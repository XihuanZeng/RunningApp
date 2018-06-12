package demo.Service;

import demo.model.GpsSimulatorRequest;
import demo.model.Point;
import demo.task.LocationSimulator;

import java.util.List;

/**
 * Created by xihuan on 18-6-11.
 */

// this is how we receive


public interface GpsSimulatorFactory {

    LocationSimulator prepareGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest);

    LocationSimulator prepareGpsSimulator(LocationSimulator locationSimulator, List<Point> points);
}
