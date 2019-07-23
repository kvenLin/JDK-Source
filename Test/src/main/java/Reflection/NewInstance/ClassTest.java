package Reflection.NewInstance;

import java.lang.reflect.Constructor;

/**
 * @Author: clf
 * @Date: 19-3-2
 * @Description:
 * 通过反射实例化对象的方法：
 * 1.直接通过newInstance，默认使用无参的构造函数
 * 2.获取指定参数的构造器，然后进行newInstance
 */
public class ClassTest {
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("Reflection.NewInstance.Person");
            Person person = (Person) clazz.newInstance();//使用无参构造函数
            System.out.println("newInstance ====> " + person.toString());
            Constructor constructor = clazz.getConstructor(String.class, int.class);
            Person person1 = (Person) constructor.newInstance("张三", 25);
            System.out.println("constructor ====> " + person1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 运行结果：
     * newInstance ====> Person{name='null', age=0}
     * constructor ====> Person{name='张三', age=25}
     */
}
