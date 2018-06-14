package demo.task;

import demo.Service.PositionService;
import demo.model.*;
import demo.support.NavUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by xihuan on 18-6-8.
 */


// This class is to create a thread class that implements Runnable interface to do multithreading
// one request one thread
/** the steps are
     1. init start time of the executor
     2. if the thread is canceled by client(via request), reset the currentPosition to null
     3. if arrived at destination, set speed to 0, otherwise will continue to move(based on speed and direction we
        predict next position.
     4. determine medical info based on runner status
     5. prepare current position info(based on speed and direction we predict next position.)
     6. send current position to distribution service
 */
public class LocationSimulator implements Runnable {
    private long id;

    // for canceling the simulation thread, this variable is kept atomic as no other thread can access it while one thread
    // is updating this
    private AtomicBoolean cancel = new AtomicBoolean();

    private double speedInMps;

    // this determines whether the runner should move or not
    private boolean shouldmove;
    private boolean exportPositionsToMessaging = true;
    private Integer reportInterval = 500;

    // we did not use lombok Data since we only want several of our fields to be visible to public
    @Getter
    @Setter
    private PositionInfo currentPosition = null;

    @Setter
    private List<Leg> legs;
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private String runningId;

    @Setter
    private Point startPoint;
    private Date executionStartTime;

    private MedicalInfo medicalInfo;

    private PositionService positionService;

    // this thread class is being created by a request class
    public LocationSimulator(GpsSimulatorRequest gpsSimulatorRequest) {
        this.shouldmove = gpsSimulatorRequest.isMove();
        this.exportPositionsToMessaging = gpsSimulatorRequest.isExportPositionToMessaging();
        this.setSpeed(gpsSimulatorRequest.getSpeed());
        this.reportInterval = gpsSimulatorRequest.getReportInterval();

        this.runningId = gpsSimulatorRequest.getRunningID();
        this.runnerStatus = gpsSimulatorRequest.getRunnerStatus();
        this.medicalInfo = gpsSimulatorRequest.getMedicalInfo();
    }



    @Override
    public void run() {
        // we need a way to handle if the thread is being interrupted
        // if it is being interrupted, then we need to destroy it
        try {
            executionStartTime = new Date();
            // cancel is AtomicBoolean, need to use get to get the value
            if (cancel.get()){
                destroy();
                return;
            }
            // ways to break this loop
            // 1. we reach destination, which will set currentPosition to null
            // 2. runnerStatus is set to STOP_NOW, this will break
            while (currentPosition != null) {
                long startTime = new Date().getTime();

                // calculate the next position
                if(currentPosition != null){
                    if (shouldmove){
                        // this function update currentPosition
                        moveRunningLocation();
                        currentPosition.setSpeed(speedInMps);
                    } else {
                        currentPosition.setSpeed(0.0);
                    }

                    currentPosition.setRunnerStatus(this.runnerStatus);

                    final MedicalInfo medicalInfoToUse;

                    switch (this.runnerStatus) {
                        case SUPPLY_NOW:
                        case SUPPLY_SOON:
                        case STOP_NOW:
                            medicalInfoToUse = this.medicalInfo;
                            break;
                        default:
                            medicalInfoToUse = null;
                            break;
                    }

                    //
                    final CurrentPosition currentPosition = new CurrentPosition(this.currentPosition.getRunningId(),
                            new Point(this.currentPosition.getPosition().getLatitude(), this.currentPosition.getPosition().getLongitude()),
                            this.currentPosition.getRunnerStatus(),
                            this.currentPosition.getSpeed(),
                            this.currentPosition.getLeg().getHeading(),
                            medicalInfoToUse
                    );

                    // send the current position to distribution service RestApi
                    // TODO implement positionInfoService
                    positionService.processPositionInfo(id, currentPosition, this.exportPositionsToMessaging);

                }
                // wait a while for the new request to come
                // this sleep not the thread sleep
                sleep(startTime);
            }
        }
        catch (InterruptedException ie){
            destroy();
            return;
        }

        destroy();

    }

    private void destroy() {
        currentPosition = null;
    }

    private void sleep(long startTime) throws InterruptedException{
        long endTime = new Date().getTime();
        long elapsedTime = endTime - startTime;
        long sleepTime = reportInterval - elapsedTime > 0 ? reportInterval - elapsedTime: 0;
        sleep(sleepTime);
    }

    private void moveRunningLocation(){
        // need to convert ms to s
        double distance = speedInMps * reportInterval / 1000.0;
        double distanceFromStart = currentPosition.getDistanceFromStart() + distance;
        double excess = 0.0;

        for (int i = currentPosition.getLeg().getId(); i < legs.size(); i++) {
            Leg currentLeg = legs.get(i);
            excess = distanceFromStart > currentLeg.getLength() ? distanceFromStart - currentLeg.getLength() : 0;
            if (Double.doubleToLongBits(excess) == 0) {
                // this means new position falls within current Leg
                currentPosition.setDistanceFromStart(distanceFromStart);
                currentPosition.setLeg(currentLeg);
                // algorithm: calculcate next position with running direction and current position
                // the new position calculation method in NavUtils
                Point newPosition = NavUtils.getPosition(currentLeg.getStartPosition(), distanceFromStart, currentLeg.getHeading());
                currentPosition.setPosition(newPosition);
                return;
            }
            distanceFromStart = excess;
        }

        setStartPosition();
    }

    public void setStartPosition() {
        currentPosition = new PositionInfo();
        currentPosition.setRunningId(this.runningId);
        Leg leg = legs.get(0);
        currentPosition.setLeg(leg);
        currentPosition.setPosition(leg.getStartPosition());
        currentPosition.setDistanceFromStart(0.0);
    }

    public double getSpeed() {return this.speedInMps;}

    public void setSpeed(double speed) {this.speedInMps = speed;}

    // synchronized means this thread when we cancel it, other request cannot do anything on it
    public synchronized void cancel() {this.cancel.set(true);}

}
