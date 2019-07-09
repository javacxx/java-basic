package com.chen.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CXX
 * @date 2019/6/25 11:28
 */
public class ConditionUseCase {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    int count;
    public void conditionWait() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
            // do something
            count--;
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            // do something
            count++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

}
