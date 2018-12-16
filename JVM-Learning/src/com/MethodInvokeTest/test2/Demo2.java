package com.MethodInvokeTest.test2;

/**
 * @Author: clf
 * @Date: 18-12-16
 * @Description:
 * 静态分派调用选用最为匹配的方法进行调用
 */
public class Demo2 {

    public void sayHello(int a){
        System.out.println("int is call");
    }
    public void sayHello(char a){
        System.out.println("char is call");
    }
    public void sayHello(char... a){
        System.out.println("char[] is call");
    }
    public void sayHello(Character a){
        System.out.println("Character is call");
    }
    public void sayHello(short a){
        System.out.println("short is call");
    }
    public void sayHello(long a){
        System.out.println("long is call");
    }
    public void sayHello(Object a){
        System.out.println("Object is call");
    }

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2.sayHello('a');
        /**
         * 运行结果:
         * char is call
         * 优先选择最为匹配的类型的方法进行调用,
         * 依次将对应的方法注释掉之后可以看到其次调用的方法是哪一个
         */
    }
}
