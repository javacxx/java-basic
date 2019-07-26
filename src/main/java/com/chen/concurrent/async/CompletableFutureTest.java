package com.chen.concurrent.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author CXX
 * @date 2019/7/24 10:44
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
   //     steepTea("碧螺春");
//        thenApplyTest();
        exceptionTest();
    }


    static void exceptionTest() {
        CompletableFuture<String> f0 =
                CompletableFuture.supplyAsync(() -> 7/1)
                .thenApply(r -> r*100+"")
                .exceptionally(e -> {
                    // handle Exception
                    System.out.println("Runtime Exception");
                    return e.getMessage();
                })
                .handle((r , e)-> "close resourse");
        System.out.println(f0.join());
    }

    static void thenApplyTest() {
        CompletableFuture f0 =
                CompletableFuture.supplyAsync(() -> "hello world ")
                .thenApply(s -> s + "from idea")
                .thenApply(String::toUpperCase);
        System.out.println(f0.join());
    }


    static void steepTea(String teaType) {
        // task 1
        CompletableFuture<Void> f1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("T1 : 洗水壶");
                    sleep(2, TimeUnit.SECONDS);

                    System.out.println("T1 : 烧开水");
                    sleep(15, TimeUnit.SECONDS);
                });

        //task 2
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2 : 洗茶杯");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2 : 洗茶壶");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2 : 拿茶叶 " + teaType);
            sleep(2, TimeUnit.SECONDS);
            return teaType;
        });

        // task 3 , after 1 and 2
        CompletableFuture<String> f3 = f1.thenCombine(f2, (__,tf) -> {
            System.out.println("T3: 拿到茶叶: " + tf);
            System.out.println("T3: 泡茶");
            return "上茶：" + tf;
        });

        System.out.println(f3.join());
    }

    static void sleep(int t, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
