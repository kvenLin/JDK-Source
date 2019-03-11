package JavaSE;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: clf
 * @Date: 19-3-10
 * @Description:
 */
public class MapTest {
    public static void main(String[] args) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(1,"hello");
        treeMap.put(5, "world");
        treeMap.put(3, "Map");
//        treeMap.put(null, "value");//不允许key为null
        treeMap.put(2, null);//允许value为null
        treeMap.entrySet().forEach(o -> {
            System.out.println("TreeMap: " + o);
        });
        Object o = treeMap.get(2);
        System.out.println(o);
        /**
         * 输出结果：说明TreeMap默认按照key的升序排列，只允许value为null
         * 1=hello
         * 2=null
         * 3=Map
         * 5=world
         * null
         */

        HashMap hashMap = new HashMap();
        hashMap.put(126, "hello");
        hashMap.put(34, "world");
        hashMap.put(288, "value");
        hashMap.entrySet().forEach(o1 -> {
            System.out.println("hashMap: " + o1);
        });
        hashMap.put(null, "null key");
        System.out.println(hashMap.get(null));//null key
        hashMap.put(null, null);
        System.out.println(hashMap.get(null));//null
        /**
         * 运行结果： HashMap存放无序；允许key为null，也允许value为null
         * hashMap: 288=value
         * hashMap: 34=world
         * hashMap: 126=hello
         * null key
         * null
         */

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
//        concurrentHashMap.put(126, null);//不允许value为null
//        concurrentHashMap.put(null, "hello");//不允许key为null
        System.out.println("concurrentHashMap : " + concurrentHashMap.get(126));
    }
}
