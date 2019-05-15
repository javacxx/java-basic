package com.chen.cpu;

public class CpuCache {
    static long[][] arr;

    public static void main(String[] args) {
        arr = new long[1024 * 1024][8];
        int sum = 0;
        // 横向遍历
        long marked = System.currentTimeMillis();
        //访问了 1024*1024次主内存，一个cacheline取8位long数据
        for (int i = 0; i < 1024 * 1024; i += 1) {
            for (int j = 0; j < 8; j++) {
                sum += arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
        sum = 0;
        marked = System.currentTimeMillis();
        // 纵向遍历
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 1024 * 1024; j++) {
                //访问了8*1024*1024次主内存
                sum += arr[j][i];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}