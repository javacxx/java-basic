package com.chen.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CXX
 * @date 2019/9/25 10:07
 */
public class Client {

    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(5);

        Runnable runnable = new Runnable() {
            @Override
            public void run(){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        executorService.submit(runnable);

        while (!executorService.isTerminated()){
            System.out.println("shut");
            executorService.shutdownNow();
        }
        System.out.println(executorService.isTerminated());
    }
}
