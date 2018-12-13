package com;

/**
 * @Author: clf
 * @Date: 18-12-13
 * @Description:
 */
public class StackOverFlowTest {

    public void test(){
        System.out.println("执行方法...");
        //递归对对方法进行添加
        test();
    }
    public static void main(String[] args) {
        new StackOverFlowTest().test();
    }
}
