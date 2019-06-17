package com.chen.concurrent.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @author CXX
 * @date 2019/6/17 14:58
 */
public class TriLockTest {
    @Test
    public void test() {
        final Lock lock = new TriLock();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        sleepSeconds(1);
                        System.out.println(Thread.currentThread().getName());
                        sleepSeconds(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        // 启动10个线程
        for (int i = 0; i < 20; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 每隔1秒换行
        for (int i = 0; i < 20; i++) {
            sleepSeconds(1);
            System.out.println();
        }
    }

    private void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
