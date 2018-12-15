package com.ClassLoad.test2;

/**
 * @Author: clf
 * @Date: 18-12-15
 * @Description:
 */
public class Parent {
    static {
        System.out.println("parent 初始化...");
    }

    public static int num = 10;
}
