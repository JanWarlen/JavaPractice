package com.janwarlen.learn.thread.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Demo {

    public static int count = 0;

    /**
     * 验证scheduleAtFixedRate是否会补足启动时间早于现在的周期任务次数
     */
    public static void funcScheduleAtFixedRate() {
        Timer timer = new Timer();
        long time = new Date().getTime() - 20_000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("this is " + count++ + "time." + System.currentTimeMillis());
            }
        };
        timer.scheduleAtFixedRate(timerTask, new Date(time), 5_000);
    }

    /**
     * 之所以不补足缺少的次数是因为 schedule 的 period 在内部会经历一次 '-'调整
     * 而在 TimerThread 内部检查时， period 为负数的任务下一次的计算时间时通过 currentTime - period
     */
    public static void funcSchedule() {
        Timer timer = new Timer();
        long time = new Date().getTime() - 20_000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("this is " + count++ + "time." + System.currentTimeMillis());
            }
        };
        timer.schedule(timerTask, new Date(time), 5_000);
    }

    public static void main(String[] args) {
//        funcScheduleAtFixedRate();
        funcSchedule();
    }
}
