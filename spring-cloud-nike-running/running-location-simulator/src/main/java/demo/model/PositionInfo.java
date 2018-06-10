package demo.model;

import lombok.Data;

/**
 * Created by xihuan on 18-6-8.
 */
@Data
public class PositionInfo {
    private String runningId;
    private Point position;
    private RunnerStatus runnerStatus = RunnerStatus.NONE;

    private Leg leg;

    private double distanceFromStart;
    private double speed;
}
