package lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: clf
 * @Date: 19-11-11
 * @Description:
 */
public class StringComparator {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return t1.compareTo(s);
            }
        });

        //expression o2.comparator(o1)
        Collections.sort(names, (s, t1) -> t1.compareTo(s));
        //statement {return o2.comparator(o1);}
        Collections.sort(names, (s, t1) -> {return t1.compareTo(s);});

        Collections.sort(names, Comparator.reverseOrder());

        System.out.println(names);
    }
}
