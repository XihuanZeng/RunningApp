package demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by xihuan on 18-6-8.
 */
@Data
// auto generate an all arg constructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CurrentPosition {

    private String runningId;
    private Point location; // not the geoLocation.Point
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private double speed;
    private double heading;
    private MedicalInfo medicalInfo;

    public CurrentPosition() {}
}
