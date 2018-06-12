package demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xihuan on 18-6-11.
 */

// json file convert to model

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulatorInitLocation {
    private List<GpsSimulatorRequest> gpsSimulatorRequest = new ArrayList<GpsSimulatorRequest>();

    public int getNumberOfGpsSimulatorRequest() { return gpsSimulatorRequest.size();}

    public void setGpsSimulatorRequest(List<GpsSimulatorRequest> gpsSimulatorRequest){
        Assert.notEmpty(gpsSimulatorRequest, "gpsSimulatorRequest cannot be empty");
    }
}
