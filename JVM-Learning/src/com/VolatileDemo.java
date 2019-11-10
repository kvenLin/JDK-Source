package com;

/**
 * @Author: clf
 * @Date: 19-11-8
 * @Description:
 */
public class VolatileDemo {

    private int a;
    private volatile boolean flag;

    public void write() {
        a = 1;// 1
        flag = true;//2  当写一个volatile变量时,Java内存模型会把该线程对应的本地内存中的共享变量刷新到主内存中
    }

    public void reader() {
        if (flag) {//3 当读一个volatile变量时,Java内存模型会把当前线程对应的本地内存中的共享变量置为无效,然后从主内存中读取变量
            int b = a + 1;//4
            System.out.println(b);//5
        }
    }
}
