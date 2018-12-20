package String;

/**
 * @Author: clf
 * @Date: 18-12-20
 * @Description:
 */
public class StringTest1 {

    public static void main(String[] args) {
        String tmp1 = "hello";//常量池
        String tmp2 = "world";
        String s1 = tmp1 + tmp2;//tmp1为变量,编译时不能知道其值,所以需要在运行时进行创建s1,所以s1是在堆中创建

        System.out.println(s1.intern() == s1);//true,常量池中并,没有helloworld,调用intern常量池中生成一个引用指向堆中的s1
    }
}
