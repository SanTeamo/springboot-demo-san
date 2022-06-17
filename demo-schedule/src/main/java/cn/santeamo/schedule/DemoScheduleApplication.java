package cn.santeamo.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author santeamo
 */
@EnableScheduling
@SpringBootApplication
public class DemoScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoScheduleApplication.class, args);
    }
}
