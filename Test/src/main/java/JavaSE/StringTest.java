package JavaSE;

/**
 * @author louye
 * @title: StringTest
 * @description: TODO
 * @date 2019-06-06,12:10
 */
public class StringTest {
    public static void main(String[] args) {
        String str1 = "String test";
        String obj = new String(str1);
        System.out.println(obj.equals(str1));
    }

    /**
     * 输出结果: true
     * 默认的equals方法先使用"=="进行内存地址的比较,如果相等直接返回true,
     * 如果不等再进行值得比较,如果值相等也返回true,否则返回false
     */
}
