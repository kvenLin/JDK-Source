package com.ClassLoad.test2;

/**
 * @Author: clf
 * @Date: 18-12-15
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 运行结果:
         * parent 初始化...
         * 10
         *
         * 即:子类不会被加载,只有父类会被加载
         */
        System.out.println(Child.num);


    }

}

