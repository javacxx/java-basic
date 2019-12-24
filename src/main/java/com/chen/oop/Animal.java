package com.chen.oop;

/**
 * @author CXX
 * @date 2019/12/23 11:22
 */
public class Animal {
    private String testAccess() {
        return "animal";
    }

    public void getName() {
        System.out.println(this.testAccess());
    }
}
