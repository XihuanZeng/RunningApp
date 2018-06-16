package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
/**
 * Created by xihuan on 18-6-15.
 */

// This service is a consumer
@Slf4j
public class RunningLocationUpdaterSink {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    // need to assign input channel

    public void updateLocation(String input) {
        log.info("Location input in updater" + input);
//        CuurentPosition payload = this.objectMapper.readValue(input, CurrentPosition.class);
//        this.template.convertAndSend("/topic/locations", payload);

    }
}
