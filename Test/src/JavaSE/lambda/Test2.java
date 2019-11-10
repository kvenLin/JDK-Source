package lambda;

/**
 * @Author: clf
 * @Date: 19-11-9
 * @Description:
 */
public class Test2 {

    public void myTest(MyInterface myInterface) {
        System.out.println(1);
        myInterface.test();
        System.out.println(2);
    }
    public static void main(String[] args) {
        Test2 test2 = new Test2();
        test2.myTest(() -> System.out.println("My test"));

        System.out.println("============================");

        MyInterface myInterface = () -> System.out.println("Hello");

    }
}
