package com.chen.concurrent.semaphore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 使用semaphore实现限流器
 * @author CXX
 * @date 2019/7/9 11:43
 */
public class ObjPool<T, R> {
   private final List<T> pool;
   private final Semaphore sem;

   public ObjPool(int size, T t) {
       pool = Collections.synchronizedList(new ArrayList<>(size));
       for (int i = 0; i < size; i++) {
           pool.add(t);
       }
       sem = new Semaphore(size);
   }

    public R exec(Function<T, R> function) throws InterruptedException {
       T t = null;
       sem.acquire();
       try {
           t = pool.remove(0);
           return function.apply(t);
       } finally {
           pool.add(t);
           sem.release();
       }
    }

    public static void main(String[] args) throws InterruptedException{
        ObjPool<Long, String> pool = new ObjPool(3, 2L);

        // 多线程进入临界区 exec方法
        pool.exec(
                t -> {
                    System.out.println(t);
                    return t.toString();
                }
        );

    }

}
