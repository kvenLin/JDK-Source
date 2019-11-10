package lambda;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: clf
 * @Date: 19-11-10
 * @Description:
 */
public class Test3 {
    public static void main(String[] args) {

        new Thread(() -> System.out.println("hello world!")).start();

        List<String> list = Arrays.asList("hello", "world", "hello world");

        //将集合中每一个单词变成大写并输出
        list.forEach(item -> System.out.println(item.toUpperCase()));

        //使用 stream + lambda 将转换出来的大写单词存放到集合再输出
        list.stream().map(item -> item.toUpperCase()).forEach(item -> System.out.println(item));

        //使用 stream + 方法引用
        list.stream().map(String::toUpperCase).forEach(item -> System.out.println(item));
    }
}
