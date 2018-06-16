package eurekademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by xihuan on 18-6-15.
 */

@SpringBootApplication
// change pom file parent
@EnableEurekaServer
public class EurekaApplication {
    public static void main (String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
