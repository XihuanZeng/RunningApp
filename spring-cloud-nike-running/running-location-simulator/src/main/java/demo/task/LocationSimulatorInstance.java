package demo.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Future;

/**
 * Created by xihuan on 18-6-11.
 */

// In this class we do async

// AllArgsConstructor
@Data
@AllArgsConstructor
public class LocationSimulatorInstance {
    private long instanceId;
    private LocationSimulator locationSimulator;
    // Future can be called at some time in the future
    private Future<?> locationSimulatorTask;
}
