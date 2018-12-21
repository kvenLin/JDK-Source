package IOTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: clf
 * @Date: 18-12-20
 * @Description:
 */
public class ReadFile {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/IOTest/test1.txt");
        //返回的int是从流中读取的一个字节
//        int i = fis.read();
//        System.out.println((char) i);

        byte[] b = new byte[4];
        //读取之后,从流中读取的数据都在b数组里,因为b数组长度就是4,所以每次读取的是4个字节
        int len = fis.read(b);
        String s = new String(b);
        System.out.println(s);
        fis.close();


        FileOutputStream fos = new FileOutputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/IOTest/test1.txt");
        String s1 = "HELLO_WORLD";
        fos.write(s1.getBytes());
        fos.close();


    }
}
