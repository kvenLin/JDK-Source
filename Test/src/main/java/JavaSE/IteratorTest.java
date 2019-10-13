package JavaSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: clf
 * @Date: 19-3-8
 * @Description:
 * iterator遍历使用
 */
public class IteratorTest {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("hello");
        list.add(new Obj1());
        list.add(new Obj2());
        list.forEach(o -> {
            System.out.println("Foreach get current obj: " + o.toString());
        });
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            System.out.println("Iterator get current obj: " + next.toString());
        }

    }

    static class Obj1{
        @Override
        public String toString() {
            return "Obj1";
        }
    }

    static class Obj2{
        @Override
        public String toString() {
            return "Obj2";
        }
    }
}
