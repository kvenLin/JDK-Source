package IOTest;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: clf
 * @Date: 18-12-20
 * @Description:
 */
public class ReadFile2 {
    public static void main(String[] args) throws IOException {
//        char c = '习';//中文字符是两个字节长度,char类型也是两个字节长度
//        System.out.println((int)c);//java中的汉字使用的是Unicode代码表示的
        FileInputStream fis = new FileInputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/IOTest/test2.txt");
        byte i = (byte) fis.read();
        byte i1 = (byte) fis.read();
        byte i2 = (byte) fis.read();
        byte i3 = (byte) fis.read();

        String s = new String(new byte[]{i, i1 , i2, i3},"utf-8");//证明汉字的unicode字符集使用uhf-8进行编码后使用的是3个字节进行保存
        System.out.println(s);//输出结果: a习

    }
}
