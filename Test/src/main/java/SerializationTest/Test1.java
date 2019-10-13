package SerializationTest;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: clf
 * @Date: 18-12-21
 * @Description:
 */
public class Test1 {
    public static void main(String[] args) throws IOException {
//        FileOutputStream fos = new FileOutputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/SerializationTest/test1.txt");
//        String text = new String("clf, test, demo");
//        byte[] bytes = text.getBytes("utf-8");//得到数字
//        fos.write(bytes);
//        fos.close();
        FileInputStream fis = new FileInputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/SerializationTest/test1.txt");
        byte[] bytes = new byte[1024];
        int len = fis.read(bytes);
        String s = new String(bytes, 0, len, "utf-8");
        System.out.println(s);
    }
}
