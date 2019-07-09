package com.chen.concurrent.condition;

import javax.xml.ws.Response;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo RPC 异步转同步
 * @author CXX
 * @date 2019/7/8 16:01
 */
public class DubboInvoker {
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private Response response;

    Object get(int timeout) throws Exception{
        lock.lock();
        long startTime = System.nanoTime();
        try {
            while (!isDone()) {
                done.await((long)timeout, TimeUnit.NANOSECONDS);
                long cur = System.nanoTime();
                if (isDone()) {
                    break;
                }
                if (cur-startTime > timeout) {
                    throw new TimeoutException();
                }
            }
        } finally {
            lock.unlock();
        }
        return response;
    }

    boolean isDone() {
        return response != null;
    }

    /**
     * RPC 结果返回调用此方法
     * @param res
     */
    private void doReceived(Response res) {
        lock.lock();
        try {
            response = res;
            if (done != null) {
                done.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
