package demo.model;

import lombok.Data;

/**
 * Created by xihuan on 18-6-8.
 */
@Data
public class CurrentPosition {

    private String runningId;
    private Point location;
    private RunnerStatus runnerStatus = RunnerState.NONE;
    private double speed;
    private double heading;
    private MedicalInfo medicalInfo;

    public CurrentPosition() {}
}
