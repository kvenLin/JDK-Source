package IntegerTest;

/**
 * @Author: clf
 * @Date: 18-12-20
 * @Description:
 */
public class IntegerDemo {
    public static void main(String[] args) {

        //==================== situation1 ===========================
        //参考IntegerDemo.txt(当前代码的反编译文件),可以看到这里,没有进行init而是调用了,Integer.valueOf(10)
        Integer integer = 10;
        Integer integer1 = 10;
        System.out.println(integer == integer1);//true


        /**
         * 分析:这里是integer和integer1的内存地址是相等的,因为在
         * 代码运行时并没有进行初始化对象,只是调用了Integer.valueOf(10)方法,
         * 这里因为Integer的缓存机制,返回的实际是Integer内部的已经创建好的数组的值,
         * 所以这里指向的是同一块地址
         *
         * 同下面代码
         * Integer integer = Integer.valueOf(10);
         * Integer integer1 = Integer.valueOf(10);
         * System.out.println(integer1 == integer1);//true
         */


        //==================== situation2 ===========================
        //创建了两个不同对象,所以在堆内存中的地址是不同的.
        Integer integer2 = new Integer(11);
        Integer integer3 = new Integer(11);
        System.out.println(integer2 == integer3);//false


        //==================== situation3 ===========================
        Integer integer4 = 1000;
        Integer integer5 = 1000;
        System.out.println(integer4 == integer5);//false
        /**
         * 参考Integer的源代码,因为缓存大小值限于-127 -- +128,
         * 这里使用1000已经超出返回所以会进行创建对象.
         */


    }
}
