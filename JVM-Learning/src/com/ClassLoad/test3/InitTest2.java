package com.ClassLoad.test3;

/**
 * @Author: clf
 * @Date: 18-12-16
 * @Description:
 * 初始化测试
 */
public class InitTest2 {
    public static int A = 1;
    static {
        A = 2;
    }


    static class Sub extends InitTest2{
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);
    }
}
