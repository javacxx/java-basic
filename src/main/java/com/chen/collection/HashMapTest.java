package com.chen.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author CXX
 * @date 2019/10/8 16:24
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        Random random = new Random();
        for (Map.Entry entry : map.entrySet()) {
            entry.setValue(6);
        }

        System.out.println(map.toString());
    }
}
