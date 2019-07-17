package com.chen.concurrent.cas;

import java.util.concurrent.ThreadFactory;

/**
 * @author CXX
 * @date 2019/7/17 10:49
 */
public class SimulatedCAS {
    volatile int count;
    volatile int unsafeCount;

    public synchronized boolean compareAndSwapInt(int expect, int newValue) {
        if (count == expect) {
            count = newValue;
            return true;
        }
        return false;
    }

    /**
     * unsafe
     * @return
     */
    public int getAndIncreCount() {
        int previous = unsafeCount;
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        unsafeCount = unsafeCount +1;
        return previous;
    }

    /**
     * return previous and then increment
     * @return previous count
     */
    public int getAndIncreCountByCAS() {
        int previous;
        do {
            previous = count;
        } while (!this.compareAndSwapInt(previous, previous+1));
        return previous;
    }

    public static void main(String[] args) {
        SimulatedCAS s =  new SimulatedCAS();

        Runnable runnable = () ->  System.out.println("------unsafe---------: "+s.getAndIncreCount());
        Runnable runnableByCAS = () ->  System.out.println(s.getAndIncreCountByCAS());

        ThreadFactory threadFactory = r -> new Thread(r);

        for (int i = 0; i < 1000; i++) {
            new Thread(runnable).start();
            threadFactory.newThread(runnableByCAS).start();
        }
    }
}
