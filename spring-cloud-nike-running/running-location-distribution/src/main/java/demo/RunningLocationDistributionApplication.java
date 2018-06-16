package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xihuan on 18-6-15.
 */

// Why distribution service?
// when simulator, pack the data and send to distribution
    // simulator has no communication with RabbitMQ
    // if we let simulator talk to RabbitMQ directly. But if we want to modify RabbitMQ, then you have to
    // change the business logic in Simulator, which is bad.


// entry point for spring boot
@SpringBootApplication
public class RunningLocationDistributionApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunningLocationDistributionApplication.class, args);
    }
}
