package demo.model;

import lombok.Data;

/**
 * Created by xihuan on 18-6-8.
 */

@Data
public class Leg {
    private int id;
    private Point startPosition;
    private Point endPosition;
    private double length;
    private double heading;

    public Leg() {

    }
}
