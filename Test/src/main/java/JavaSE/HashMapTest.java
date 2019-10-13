package JavaSE;

import java.util.HashMap;

/**
 * @Author: clf
 * @Date: 19-3-23
 * @Description:
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put(null, null);
        hashMap.put(null, 123);
        hashMap.put(null, 4515);

        System.out.println(hashMap.size());
    }
}
