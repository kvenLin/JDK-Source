package lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Author: clf
 * @Date: 19-11-15
 * @Description:
 */
public class FunctionTest2 {
    public static void main(String[] args) {

        FunctionTest2 test = new FunctionTest2();
        System.out.println(test.compute(2, value -> value * 3, value -> value * value));
        System.out.println(test.compute2(2, value -> value * 3, value -> value * value));

        System.out.println(test.compute3(1, 2, (a, b) -> a + b));
        System.out.println(test.compute3(1, 2, (a, b) -> a - b));
        System.out.println(test.compute3(1, 2, (a, b) -> a * b));
        System.out.println(test.compute3(1, 2, (a, b) -> a / b));

        System.out.println(test.compute4(2, 3, (a, b) -> a + b, value -> value * value));
    }

    public int compute(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //先执行function2的apply方法,再执行function1的apply方法
        return function1.compose(function2).apply(a);
    }

    public int compute2(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //先执行function1的apply方法,再执行function2的apply方法
        return function1.andThen(function2).apply(a);
    }


    //需求: 使用BiFunction实现加减乘除
    public int compute3(Integer a, Integer b, BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(a, b);
    }

    public int compute4(Integer a, Integer b,
                        BiFunction<Integer, Integer, Integer> biFunction,
                        Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(a, b);
    }
}
