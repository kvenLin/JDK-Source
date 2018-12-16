package com.MethodInvokeTest.test3;

/**
 * @Author: clf
 * @Date: 18-12-16
 * @Description:
 * 动态分派调用测试
 */
public class Demo {
    static class Parent{
        public void sayHello(){
            System.out.println("parent");
        }
    }

    static class Child1 extends Parent{
        @Override
        public void sayHello() {
            System.out.println("child1");
        }
    }

    static class Child11 extends Child1{
        @Override
        public void sayHello() {
            System.out.println("child11");
        }
    }

    static class Child2 extends Parent{
        @Override
        public void sayHello() {
            System.out.println("child2");
        }
    }

    public static void main(String[] args) {
        Parent p1 = new Child1();
        Parent p11 = new Child11();
        Parent p2 = new Child2();

        p1.sayHello();
        p11.sayHello();
        p2.sayHello();
    }
}
