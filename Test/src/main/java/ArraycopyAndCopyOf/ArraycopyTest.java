package ArraycopyAndCopyOf;

/**
 * @Author: clf
 * @Date: 18-12-10
 * @Description:
 */
public class ArraycopyTest {
    public static void main(String[] args) {
        int[] a = new int[10];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        /**
         * arraycopy()方法实现数组自己复制自己
         *
         * 源数组 a;
         * 源数组中的起始位置; 2
         * 目标数组 a；
         * 目标数组中的起始位置 3；
         * 要复制的数组元素的数量 3；
         */
        //将从索引2开始的后面的3个元素复制到原有数组的索引3开始的位置
        System.arraycopy(a, 2, a, 3, 3);
        a[2]=99;
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
