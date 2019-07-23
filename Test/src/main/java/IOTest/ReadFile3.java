package IOTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: clf
 * @Date: 18-12-20
 * @Description:
 * 采用字符流避免使用字节流发生读取汉字时无法区分是否将单个字符的字节读取完整.
 */
public class ReadFile3 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/IOTest/test3.txt");
        //把字节流包装成字符流
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String text;

        while ((text = br.readLine())!= null){
            System.out.println(text);
        }

    }
}

