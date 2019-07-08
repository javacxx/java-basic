package com.chen.concurrent.monitor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CXX
 * @date 2019/7/8 14:37
 */
public class BlockedQueue<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    List<T> queue = new LinkedList<>();
    private final int capacity = 100;

    public void enq(T t) throws InterruptedException{
        lock.lock();
        try {
            while (queue.size() == 100) {
                notFull.await(); // wait signal for not full
            }
            queue.add(t);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T deq() throws InterruptedException{
        lock.lock();
        T t;
        try {
            while (queue.size()==0) {
                notEmpty.await(); // wait signal for not empty
            }
            t = queue.remove(0);
            notFull.signal();
        } finally {
            lock.unlock();
        }
        return t;
    }
}
