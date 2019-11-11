package lambda;

import java.util.function.Function;

/**
 * @Author: clf
 * @Date: 19-11-11
 * @Description: 理解function方式的lambda表达式传入的是一种行为
 */
public class FunctionTest {

    public static void main(String[] args) {

        FunctionTest functionTest = new FunctionTest();
        System.out.println(functionTest.compute(1, value -> 2 * value));
        System.out.println(functionTest.compute(2, value -> 5 + value));
        System.out.println(functionTest.compute(3, value -> value * value));

        System.out.println(functionTest.convert(5, value -> String.valueOf(value + "hello world")));
    }

    public int compute(int a, Function<Integer, Integer> function) {
        int result = function.apply(a);
        return result;
    }

    public String convert(int a, Function<Integer, String> function) {
        return function.apply(a);
    }
}
