package com.chen.concurrent.util;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 斐波那契数列，下标从0开始，求第n个下标的斐波那契值
 * @author CXX
 * @date 2019/7/29 10:34
 */
public class ForkJoinTest {
    static int counter;
    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool(4);

        Fibonacci fib = new Fibonacci(5);
        Integer result = fjp.invoke(fib);

        System.out.println(result);

    }

    static class Fibonacci extends RecursiveTask<Integer> {
        int n;
        public Fibonacci(int n) {
            this.n = n ;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            System.out.println("compute times :" + ++counter);
            return f2.compute() + f1.join();
        }
    }
}
