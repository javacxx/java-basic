package com.chen.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author CXX
 * @date 2019/7/26 15:56
 */
public class SleepUtils {

    public static void sleepSeconds(int t) {
        sleep(t, TimeUnit.SECONDS);
    }

    public static void sleep(int t, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(t);
        } catch (InterruptedException e) {
         //   e.printStackTrace();
        }
    }
}
