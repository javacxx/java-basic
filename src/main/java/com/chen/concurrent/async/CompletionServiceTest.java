package com.chen.concurrent.async;

import com.chen.concurrent.SleepUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author CXX
 * @date 2019/7/26 15:44
 */
public class CompletionServiceTest {
    public static void main(String[] args) {
        System.out.println("receive msg :"+
            determineEnemyLocation()
        );
    }

    public static String determineEnemyLocation() {
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        CompletionService<String> completionService =
                new ExecutorCompletionService(executor);
        List<Future> futures = new ArrayList<>();
        futures.add(completionService.submit(() -> {
            System.out.println("soldier A : action");
            SleepUtils.sleepSeconds(15);
            System.out.println("A DONE");
            return ("soldier A find enemy");
        }));
        futures.add(completionService.submit(() -> {
            System.out.println("soldier B : action");
            SleepUtils.sleepSeconds(10);
            System.out.println("B DONE");
            return ("soldier B find enemy");
        }));
        futures.add(completionService.submit(() -> {
            System.out.println("soldier C : action");
            SleepUtils.sleepSeconds(5);
            System.out.println("C DONE");
            return ("soldier C find enemy");
        }));
        String str = null;
        try {
            for (int i = 0; i < 3; i++) {
                str = completionService.take().get();
                System.out.println(str + " task done");
                if (str != null) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            for (Future future : futures) {
                System.out.println(future.cancel(true));
            }
        }
        executor.shutdown();
        return str;
    }
}
