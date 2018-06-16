package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Source;

/**
 * Created by xihuan on 18-6-15.
 */

@Slf4j
@EnableBinding(Source.class)
@RestController
public class RunnerPositionSource {

    // spring boot will automatically create this MessageChannel for you, just autowired
    @Autowired
    private MessageChannel output;

    @RequestMapping(path="api/location", method = RequestMethod.POST)
    public void locations(@RequestBody String positionInfo) {
        log.info("Receiving currentPositionInfo from simulator " + positionInfo);
        // encapsulate the positionInfo to pass the message to MQ
        // need to convert to a format readable by MQ
        this.output.send(MessageBuilder.withPayload(positionInfo).build());
    }

}
