package demo.Service.impl;

import demo.Service.GpsSimulatorFactory;
import demo.Service.PositionService;
import demo.model.GpsSimulatorRequest;
import demo.model.Leg;
import demo.model.Point;
import demo.support.NavUtils;
import demo.task.LocationSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xihuan on 18-6-11.
 */
public class DefaultGpsSimulatorFactory implements GpsSimulatorFactory{
    @Autowired
    private PositionService positionService;

    private final AtomicLong instanceCounter = new AtomicLong();

    // Deal with request
    @Override
    public LocationSimulator prepareGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest) {
        // why not inject locationSimulator, but instantiate a new object
        // because we need to create by using some args
        final LocationSimulator locationSimulator = new LocationSimulator(gpsSimulatorRequest);
        locationSimulator.setPositionService(positionService);
        locationSimulator.setId(this.instanceCounter.incrementAndGet());

        final List<Point> points = NavUtils.decodePolyline(gpsSimulatorRequest.getPolyline());
        locationSimulator.setStartPoint(points.iterator().next());
        return prepareGpsSimulator(locationSimulator, points);
    }


    // Deal with Leg
    @Override
    public LocationSimulator prepareGpsSimulator(LocationSimulator locationSimulator, List<Point> points) {
        locationSimulator.setCurrentPosition(null);
        final List<Leg> legs = createLegList(points);
        locationSimulator.setLegs(legs);
        locationSimulator.setStartPosition();
        return locationSimulator;
    }

    private List<Leg> createLegList(List<Point> points) {
        final List<Leg> legs = new ArrayList<Leg>();
        for (int i = 0; i < (points.size() - 1); i++) {
            Leg leg = new Leg();
            leg.setId(i);
            leg.setStartPosition(points.get(i));
            leg.setEndPosition(points.get(i+1));
            Double length = NavUtils.getDistance(points.get(i), points.get(i+1));
            leg.setLength(length);
            // start point and end point hemisphere angle
            Double heading = NavUtils.getBearing();
            leg.setHeading(heading);
            legs.add(leg);
            return legs;
        }
    }
}
