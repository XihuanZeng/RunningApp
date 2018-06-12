package demo.Service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.Service.PathService;
import demo.SimulatorServiceApplication;
import demo.model.SimulatorInitLocation;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xihuan on 18-6-11.
 */
public class DefaultPathService implements PathService {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SimulatorServiceApplication loadSimulatorInitLocation() {
        final InputStream is = this.getClass().getResourceAsStream("/init-location");
        // reverse serialization, convert json to a model
        try {
            return objectMapper.readValue(is, SimulatorInitLocation.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
