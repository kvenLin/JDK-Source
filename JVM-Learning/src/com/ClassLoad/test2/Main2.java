package com.ClassLoad.test2;

/**
 * @Author: clf
 * @Date: 18-12-15
 * @Description:
 */
public class Main2 {
    public static void main(String[] args) {
        /**
         * 运行结果:
         * 不会输出任何东西
         *
         * 即:通过数组定义来引用类,不会被初始化
         */
        Child[] children = new Child[10];

    }

}

