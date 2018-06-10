package demo.model;

import lombok.Data;

/**
 * Created by xihuan on 18-6-8.
 */
@Data
public class GpsSimulatorRequest {

    private String runningID;
    private double speed;
    private boolean move = true;
    private boolean exportPositionToMessaging = true;
    private Integer reportInterval = 500;
    private RunnerStatus runnerStatus = RunnerStatus.None;
    private String polyline;
    private MedicalInfo medicalInfo;

}
