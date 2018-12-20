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
        String s1 = "abc";//存放常量池中
        String s2 = "abc";

        System.out.println(s1 == s2);// "==" 进行地址的比较,true

        /**
         * 进行new时,会检测"abc"在常量池中是否存在,
         * 如果存在则会将"abc"复制给堆中创建的新的空间,
         * 如果不存在则先在常量池创建"abc",
         * 在将其复制给堆内存中创建的对象,
         * 最后返回的是堆内存中的对象的引用.
         */
        String s3 = new String("abc");
        System.out.println(s1 == s3);//常量池中的"abc"和堆中的"abc"显然不是一块内存区域,false

        System.out.println(s1 == s3.intern());//s3.intern():返回常量池中的"abc"对象,这时s1和s3指向的是同一块内存,true
    }
}
