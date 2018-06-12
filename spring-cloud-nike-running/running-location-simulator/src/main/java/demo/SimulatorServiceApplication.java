package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by xihuan on 18-6-8.
 */

// Let Spring Boots
@EnableScheduling
@SpringBootApplication
public class SimulatorServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(SimulatorServiceApplication.class, args);
    }


    public AsyncTaskExecutor taskExecutor() {
        // note this scheduler also implements task executor, so can be returned as executor
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }
}
