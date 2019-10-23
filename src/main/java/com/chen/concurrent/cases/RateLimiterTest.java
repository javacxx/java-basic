package com.chen.concurrent.cases;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CXX
 * @date 2019/8/22 15:05
 */
public class RateLimiterTest {
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(3.3);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        long start = System.nanoTime();
        for (int i=0; i<20; i++) {
            rateLimiter.acquire();
            executorService.submit(() -> {
                long curr = System.nanoTime();
                System.out.println((curr - start)/100000000);
            });
        }


    }
}
