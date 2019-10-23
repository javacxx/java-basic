package com.chen.concurrent.util;

import java.util.concurrent.CountDownLatch;

/**
 * @author CXX
 * @date 2019/10/18 14:11
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        countDownLatch.countDown();
        countDownLatch.countDown();
        countDownLatch.countDown();
        countDownLatch.await();
        System.out.println("afsd");
    }
}
