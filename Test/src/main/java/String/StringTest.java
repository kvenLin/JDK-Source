package String;

/**
 * @Author: clf
 * @Date: 18-12-19
 * @Description:
 */
public class StringTest {
    public static void main(String[] args) {
        String s = new String("hello") + new String("world");
//        String s = "hello" + "world";
        System.out.println(s.intern() == s);//true

        String s1 = new String("hello") + new String("world");
        System.out.println(s1.intern() == s1);//false

        String s2 = new String("String3");
        System.out.println(s2 == s2.intern());//false

        String s3 = "clf" + "best";//这时堆中没有clfbest,存在于常量池中,因为在编译期间就将其拼接成了clfbest,结合class文件和txt文件查看
        String s4 = "clfbest";
        System.out.println(s3 == s4);//true
        System.out.println(s3.intern() == s3);//true

    }

}
