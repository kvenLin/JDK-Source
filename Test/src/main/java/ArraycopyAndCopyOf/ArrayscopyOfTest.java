package ArraycopyAndCopyOf;

import java.util.Arrays;

/**
 * @Author: clf
 * @Date: 18-12-10
 * @Description:
 */
public class ArrayscopyOfTest {
    public static void main(String[] args) {
        int[] a = new int[3];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        int[] b = Arrays.copyOf(a, 10);
        System.out.println("b.length"+b.length);
        for (int i = 0; i < b.length; i++) {
            System.out.println(i + " : " + b[i]);
        }
    }
}
