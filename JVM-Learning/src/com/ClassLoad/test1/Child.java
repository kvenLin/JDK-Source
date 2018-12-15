package com.ClassLoad.test1;

/**
 * @Author: clf
 * @Date: 18-12-15
 * @Description:
 */
public class Child extends Parent{
    static {
        System.out.println("child 初始化....");
    }

    public static void main(String[] args) {
        /**
         * 运行结果:
         * parent 初始化...
         * child 初始化....
         * 即:
         * 当初始化一个类时,如果发现其父类还没有进行过初始化,则需要先触发其父类的初始化
         * 当虚拟机启动时,用户需要指定一个执行的主类(包含main()方法的那个类),虚拟机会先初始化这个主类
         */
    }
}
