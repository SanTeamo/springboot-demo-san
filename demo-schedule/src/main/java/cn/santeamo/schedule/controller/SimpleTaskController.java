package cn.santeamo.schedule.controller;

import cn.santeamo.schedule.run.MyRunnable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@RestController
public class SimpleTaskController {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    @PostConstruct
    public void postConstruct() {
        threadPoolTaskScheduler.initialize();
    }

    /**
     * 在ScheduledFuture中有一个cancel可以停止定时任务。
     */
    private ScheduledFuture<?> future;

    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        System.out.println("now " + LocalDateTime.now());
    }

    /**
     * 启动任务
     **/
    @RequestMapping("/startTask")
    public String startCron() {
        future = threadPoolTaskScheduler.schedule(new MyRunnable(), new CronTrigger("0/5 * * * * *"));
        System.out.println("DynamicTaskController.startCron()");
        return "startTask";
    }

    /**
     * 启此任务
     **/
    @RequestMapping("/stopTask")
    public String stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("DynamicTaskController.stopCron()");
        return "stopTask";
    }

    /**
     * 变更任务间隔，再次启动
     **/
    @RequestMapping("/changeCron")
    public String changeCron() {
        // 先停止，再开启.
        stopCron();
        future = threadPoolTaskScheduler.schedule(new MyRunnable(), new CronTrigger("*/10 * * * * *"));
        System.out.println("DynamicTaskController.changeCron()");
        return "changeCron";
    }

}
