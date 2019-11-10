package lambda;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: clf
 * @Date: 19-11-9
 * @Description:
 */
public class Test1 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        //最传统的方式
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("================================");

        //jdk1.5之后
        for (Integer integer : list) {
            System.out.println(integer);
        }
        System.out.println("================================");

        //lambda
        list.forEach(integer -> System.out.println(integer));

    }
}
