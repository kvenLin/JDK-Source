package lambda;

import java.util.function.Predicate;

/**
 * @Author: clf
 * @Date: 19-11-16
 * @Description:
 */
public class PredicateTest {
    public static void main(String[] args) {

        Predicate<String> predicate = s -> s.length() > 5;
        System.out.println(predicate.test("hello"));
    }
}
