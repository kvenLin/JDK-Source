package com.ClassLoad.test2;

/**
 * @Author: clf
 * @Date: 18-12-15
 * @Description:
 */
public class Main3 {
    public static void main(String[] args) {
        /**
         * 运行结果:
         * 20
         *
         * 即: 调用类的常量,该类不会进行初始化
         */
        System.out.println(Child.a);

    }

}

