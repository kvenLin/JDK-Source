package JavaSE;

/**
 * @Author: clf
 * @Date: 19-3-10
 * @Description:
 */
public class HashCodeAndEqualsTest {
    public static void main(String[] args) {
        Obj obj = new Obj();
        System.out.println(obj.hashCode());
        System.out.println(obj.hashCode());
    }
    static class Obj{

    }
}
