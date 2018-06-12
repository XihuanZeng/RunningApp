package demo.Service.impl;

import demo.Service.PositionService;
import demo.model.CurrentPosition;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * Created by xihuan on 18-6-11.
 */
public class DefaultPositionService implements PositionService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultPositionService);

    @Autowired
    private RestTemplate restTemplate;

    @Value("%(com.x )")
    @Override
    public void processPositionInfo(long id, CurrentPosition currentPosition, boolean sendPositionToDistributionService) {
        String runningLocationDistribution = "httpL//running-location-distribution";
        if (sendPositionToDistributionService) {
            Logger.info("");
            this.restTemplate.postForLocation(runningLocationDistribution + "/" + cc)
        }
    }
}
