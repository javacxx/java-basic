package com.chen.oop;

/**
 * @author CXX
 * @date 2019/12/23 11:23
 */
public class Cat extends Animal {
    private String testAccess() {
        return "Cat";
    }
    @Override
    public void getName() {
        System.out.println(this.testAccess());
    }
}
