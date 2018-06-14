package demo.Service.impl;

import demo.Service.PositionService;
import demo.model.CurrentPosition;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * Created by xihuan on 18-6-11.
 */

@Service // make it bean
@Slf4j
public class DefaultPositionService implements PositionService {
    // private static final Logger LOGGER = LoggerFactory.getLogger((DefaultPositionService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("$(com.xihuan.running.location.distribution)")
    // this value should be put into resource dir so that it is not hard coding
    // see resources/application.yml
    private String runningLocationDistribution;
    @Override
    public void processPositionInfo(long id, CurrentPosition currentPosition, boolean sendPositionToDistributionService) {
        if (sendPositionToDistributionService) {
            // this log is created by annotation @Slf4j
            log.info(String.format("Thread %d  Simulator is calling distribution REST API", Thread.currentThread().getId());
            // restTemplate is like a REST client,
            // since we already start the bean, we only need to do dependency injection
            this.restTemplate.postForLocation(runningLocationDistribution + "/", currentPosition);
        }
    }
}
