package demo.rest;

import demo.Service.GpsSimulatorFactory;
import demo.model.GpsSimulatorRequest;
import demo.model.SimulatorInitLocation;
import demo.task.LocationSimulator;
import demo.task.LocationSimulatorInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by xihuan on 18-6-11.
 */

// We will assign each thread with data
    // Also we will cancel the thread from external, which is another API call

@RestController
@RequestMapping("/api")
public class LocationSimulatorRestApi {

    @Autowired
    private GpsSimulatorFactory gpsSimulatorFactory;

    // No bean created, but this is from springframework.core, this is by default add to bean
    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @Autowired
    private PathService pathService;

    // map between thread id and hash map
    private Map<Long, LocationSimulatorInstance> taskFuture = new HashMap<Long, LocationSimulator>();

    // step1: load
    // step2: transform domain model request to class, request -> LocationSimulator
    // step3: taskExecutor.submit(simulator)
    // step4: simulator starts
    @RequestMapping("/simulation")
    public List<LocationSimulatorInstance> simulation(){
        final SimulatorInitLocation fixture = this.pathService.loadSimulatorInitLocations();
        final List<LocationSimulatorInstance> instances = new ArrayList<LocationSimulatorInstance>();
        // for each simulator request, we convert them into a single thread
        for (GpsSimulatorRequest gpsSimulatorRequest: fixture.getGpsSimulatorRequest()) {
            // one locationSimulator, one thread
            // this is before passing, we do some prepare
            final LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);
            final Future<?> future = taskExecutor.submit(locationSimulator);
            final LocationSimulatorInstance instance = new LocationSimulatorInstance(locationSimulator.getId(),);
            taskFuture.put(locationSimulator.getId(), instance);
            instances.add(instance);
        }

        return instances;
    }

    @RequestMapping("/cancel")
    public int cancel() {
        int numberOdCancelTasks = 0;
        for (Map.Entry<Long, LocationSimulatorInstance> entry: taskFuture.entrySet()) {
            LocationSimulatorInstance instance = entry.getValue();
            instance.getLocationSimulator().cancel();
            boolean wasCancelled = instance.getLocationSimulatorTask().cancel();
            if (wasCancelled) {
                numberOdCancelTasks
            }
        }

    }
}
