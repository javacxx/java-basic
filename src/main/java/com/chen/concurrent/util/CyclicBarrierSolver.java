package com.chen.concurrent.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author CXX
 * @date 2019/7/12 10:29
 */
public class CyclicBarrierSolver {
    final int N;
    final CyclicBarrier barrier;
    public CyclicBarrierSolver(int taskThreads) {
        N = taskThreads;
        Runnable barrierAction = new Runnable() {
            @Override
            public void run() {
                System.out.println("************  callback task start!  **********");
            }
        };
        barrier = new CyclicBarrier(N, barrierAction);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Thread thread = new Thread(new Worker(i));
            threads.add(thread);
            thread.start();
        }
    }


    class Worker implements Runnable{
        int myRow;
        Worker (int row) {
           myRow = row;
        }

        @Override
        public void run() {
            System.out.println("my task done :" + myRow);
            try {
                barrier.await(); // means my task has done,wait for others task done
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
                return;
            }
            System.out.println(" terminated :" + myRow);
        }
    }

    public static void main(String[] args) {
        CyclicBarrierSolver solver = new CyclicBarrierSolver(10);
    }
}
