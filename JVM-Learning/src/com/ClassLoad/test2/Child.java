package com.ClassLoad.test2;

/**
 * @Author: clf
 * @Date: 18-12-15
 * @Description:
 */
public class Child extends Parent {
    static {
        System.out.println("child 初始化....");
    }

    public static final int a = 20;
}
