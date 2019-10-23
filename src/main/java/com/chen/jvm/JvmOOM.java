package com.chen.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CXX
 * @date 2019/10/21 17:36
 */
public class JvmOOM {
    public static void main(String[] args) {
        permGenOOM();
    }

    private static void headOOM() {
        List list = new ArrayList<>();

        while(true) {
            list.add(new Object());
        }
    }

    private static void permGenOOM() {
        String str = new StringBuilder("ja").append("va").toString();
        System.out.println(str.intern() == str);

        String str1 = new StringBuilder("c").append("xx").toString();
        System.out.println(str1.intern() == str1);
    }
}
