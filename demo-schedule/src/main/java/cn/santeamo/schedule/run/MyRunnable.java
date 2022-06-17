package cn.santeamo.schedule.run;

import java.time.LocalDateTime;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable.run()，" + LocalDateTime.now());
    }
}
