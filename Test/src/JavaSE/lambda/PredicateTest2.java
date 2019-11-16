package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: clf
 * @Date: 19-11-16
 * @Description:
 */
public class PredicateTest2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        PredicateTest2 predicateTest2 = new PredicateTest2();
        predicateTest2.conditionFilter(list, integer -> integer % 2 == 0);
        System.out.println("================================");

        predicateTest2.conditionFilter(list, integer -> integer % 2 != 0);
        System.out.println("================================");

        predicateTest2.conditionFilter(list, integer -> integer > 5);
        System.out.println("================================");

        predicateTest2.conditionFilter(list, integer -> integer < 3);
        System.out.println("================================");

        //找出大于5并且是偶数的数字
        predicateTest2.conditionFilter(list, integer -> integer > 5, integer -> integer % 2 == 0);
        System.out.println("================================");

        System.out.println(predicateTest2.isEqual("test").test("test"));
    }


    public void conditionFilter(List<Integer> list, Predicate<Integer> predicate){
        list.forEach(integer -> {
            if (predicate.test(integer)) {
                System.out.println(integer);
            }
        });
    }

    public void conditionFilter(List<Integer> list,
                                Predicate<Integer> predicate,
                                Predicate<Integer> predicate2) {
        for (Integer integer : list) {
            if (predicate.and(predicate2).negate().test(integer)) {
                System.out.println(integer);
            }
        }
    }

    public Predicate<String> isEqual(Object object) {
        return Predicate.isEqual(object);
    }

}
