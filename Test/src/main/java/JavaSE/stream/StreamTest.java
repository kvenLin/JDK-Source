package JavaSE.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author louye
 * @title: StreanTest
 * @description: TODO
 * @date 2019-06-13,11:22
 */
public class StreamTest {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
        List<String> values = nums.stream().
                map(integer -> print("str" + integer, integer))
                .collect(Collectors.toList());
        System.out.println(values);
    }

    public static String print(String str, Integer value) {
        return str + ": " + value;
    }
}
