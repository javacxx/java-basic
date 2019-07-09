package com.chen.concurrent.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用读写锁实现cache 保证dataMap的安全读写
 * @author CXX
 * @date 2019/7/9 14:02
 */
public class Cache<K, V> {
    private final Map<K, V> dataMap = new HashMap();
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();

    V get(K k) {
        readLock.lock();
        try {
            return dataMap.get(k);
        } finally {
            readLock.unlock();
        }
    }

    V put (K k,V v) {
        writeLock.lock();
        try {
           return dataMap.put(k, v);
        } finally {
            writeLock.unlock();
        }
    }
}
