package com;

/**
 * @Author: clf
 * @Date: 18-12-13
 * @Description:
 * 常量池测试
 */
public class ConstantPoolTest {
    public static void main(String[] args) {

        /**
         * Java栈中的局部变量表:
         * 可以存放基本数据类型,
         * String是一个抽象数据类型,所以这里存放的是引用,
         * 即局部变量表存储s1,s2,
         * 创建的"abc"时会将其放入运行时常量池
         * 如果使用new进行创建"abc"会直接存放到Java堆中,这时不再考虑常量池
         */
        String s1 = "abc";
        String s2 = "abc";

        System.out.println(s1 == s2);// "==" 进行地址的比较,true

        String s3 = new String("abc");
        System.out.println(s1 == s3);//常量池中的"abc"和堆中的"abc"显然不是一块内存区域,false

        System.out.println(s1 == s3.intern());//s3.intern()将堆中的"abc"(即s3)放入常量池中,产生一个运行时常量,true
    }
}
