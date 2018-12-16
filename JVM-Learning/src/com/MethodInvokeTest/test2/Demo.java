package com.MethodInvokeTest.test2;

/**
 * @Author: clf
 * @Date: 18-12-16
 * @Description:
 * 静态分派调用测试
 */
public class Demo {
    static class Parent{

    }
    static class Child1 extends Parent{

    }
    static class Child2 extends Parent{

    }

    public void sayHello(Child1 child1){
        System.out.println("child1 is call");
    }

    public void sayHello(Child2 child2){
        System.out.println("child2 is call");
    }

    public void sayHello(Parent parent){
        System.out.println("parent is call");
    }

    public static void main(String[] args) {
        Parent parent1 = new Child1();//parent1 为静态类型,而实例称为为实际类型或真实类型
        Parent parent2 = new Child2();

        Demo demo = new Demo();

        demo.sayHello(parent1);
        demo.sayHello(parent2);

        /**
         * 运行结果:
         * parent is call
         * parent is call
         *
         * 分析:
         * 方法的选择是根据静态类型进行选择的,则这种调用方式称为静态分派调用
         */
    }
}
