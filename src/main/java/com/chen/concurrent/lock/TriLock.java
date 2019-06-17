package com.chen.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 允许3个线程同时获取锁
 *
 * @author CXX
 * @date 2019/6/17 14:12
 */
public class TriLock implements Lock {

    private static final Sync sync = new Sync(3);

    private static final class Sync extends AbstractQueuedSynchronizer {
        private static int MAX_COUNT;

        Sync(int count) {
            if(count <= 0) {
                throw new IllegalArgumentException("count count must lager than zero!");
            }
            setState(count);
            MAX_COUNT = count;
        }

        @Override
        protected int tryAcquireShared(int acquireNum) {
            if (acquireNum <= 0) {
                throw new IllegalArgumentException("acquireNum must be lager than zero!");
            }
            for(;;) {
                int c = getState();
                int newCount = c - acquireNum;
                if (newCount >= 0 && compareAndSetState(c, newCount)) {
                    return acquireNum;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int releaseCount) {
            if (releaseCount <= 0) {
                throw new IllegalArgumentException("acquireNum must be lager than zero!");
            }
            for(;;) {
                int c = getState();
                int newCount = c + releaseCount;
                if (newCount <= MAX_COUNT && compareAndSetState(c,newCount)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
       sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
