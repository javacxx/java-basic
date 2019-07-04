package com.chen.thread;

/**
 * @author CXX
 * @date 2019/7/4 10:36
 */
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(
                () -> {
                    try {
                        Thread.sleep(60000L);
                    } catch (InterruptedException e) {
                        System.out.println("t1 got InterruptedException");
                    }
                }
        );
        t1.start();
        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(t1.getState());
        System.out.println(t1.isInterrupted());
        System.out.println(t1.getState());
        Thread.sleep(1000L);
        System.out.println(t1.getState());
        System.out.println(t1.isInterrupted());

    }

    private static void test() {
        System.out.println("start");
        Thread.currentThread().interrupt();
        System.out.println("end");
        System.out.println(Thread.currentThread().isInterrupted());
    }
}
