package com.chen.concurrent.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CXX
 * @date 2019/5/17 10:51
 */
public class CASCount {
    private int countNum = 0;
    private AtomicInteger atomicCount = new AtomicInteger(0);
    private CountDownLatch countDownLatch;

    private void count () {
        countNum++;
    }
    private void safeCount() {
        for (;;){
            int i = atomicCount.get();
            boolean suc = atomicCount.compareAndSet(i, ++i);
            if (suc){
                break;
            }
        }
    }

    public void conCurrentCount(int threadNum) {
        countDownLatch = new CountDownLatch(threadNum);

        Runnable runnable = () -> {
            count();
            safeCount();
            countDownLatch.countDown();
        };

        List<Thread> threadList = new ArrayList<>(threadNum);
        for (int i=0; i<threadNum; i++) {
            threadList.add(new Thread(runnable));
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(countNum);
        System.out.println(atomicCount);
    }

    public static void main(String[] args) {
        new CASCount().conCurrentCount(333);
    }
}
