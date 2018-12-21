package SerializationTest;

import java.io.*;

/**
 * @Author: clf
 * @Date: 18-12-21
 * @Description:
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
//        Person person = new Person("clf", 22, 13000.5);
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/SerializationTest/test2.txt"));
//        //Java把p对象序列化成二进制数据并写入到磁盘中
//        oos.writeObject(person);
//        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/home/hk/IdeaProjects2/JDK-Source/Test/src/SerializationTest/test2.txt"));
        //Java把从本地的文件中读取到的二进制数据还原成person对象
        Person person = (Person) ois.readObject();
        System.out.println(person);
        ois.close();
    }
}
