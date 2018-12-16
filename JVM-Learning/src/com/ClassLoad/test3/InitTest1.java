package com.ClassLoad.test3;

/**
 * @Author: clf
 * @Date: 18-12-16
 * @Description:
 * 初始化测试
 */
public class InitTest1 {
    static {
        i = 0;//可以赋值,但是不能进行访问
//        System.out.println(i);//这里出现了编译错误
    }
    static int i = 1;
}
/**
 * 分析:
 * <clinit>()方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块中的语句块合并产生的,
 * 编译器收集的顺序是由语句在源文件中出现的顺序决定的,静态块中只能访问在静态语句块之前的变量,
 * 定义在它之后的变量,在前面的语句块中可以赋值,但是不能访问
 *
 */
