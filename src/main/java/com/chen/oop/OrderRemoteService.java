package com.chen.oop;

/**
 * @author CXX
 * @date 2019/12/24 16:03
 */
public interface OrderRemoteService {

    enum Type {
        CN,
        US,
        UK
    }

    static void defineStatic() {
        System.out.println("static method!");
    }

    default void name() {
        System.out.println(OrderRemoteService.class.getName());
    }
}
