package Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: clf
 * @Date: 19-3-2
 * @Description:
 * 反射机制学习
 */
public class Test {

    public static void main(String[] args) {
        ReflectClass obj = new ReflectClass("张三", 24, 5);
        try {
            Class<ReflectClass> clazz = ReflectClass.class;
            Field age = clazz.getField("age");//获取age成员变量
            Field name = clazz.getDeclaredField("name");
            int objAge = (Integer)age.get(obj);
            String objName = (String)name.get(obj);
            System.out.println("通过反射获取到obj1的年龄为: " + objAge + ", 姓名为: " + objName);
            Method method = clazz.getMethod("testMethod");//获取testMethod方法
            System.out.println("测试obj1的testMethod()方法执行效果: ");
            method.invoke(obj);
            System.out.println();
            testAccess(clazz, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAccess(Class clazz, ReflectClass obj) {
        System.out.println("=============================");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("成员变量: " + declaredField.getName());
            try {
                declaredField.setAccessible(true);//保证了
                Object o = declaredField.get(obj);
                System.out.println("value : " + o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * =============================================================================
     * 运行结果：
     *
     * 通过反射获取到obj1的年龄为: 24, 姓名为: 张三
     * 测试obj1的testMethod()方法执行效果:
     * testMethod is called...
     *
     * =============================
     * 成员变量: age
     * value : 24
     * 成员变量: name
     * value : 张三
     * 成员变量: grade
     * value : 5
     */
    private static class ReflectClass {
        public int age;
        public String name;
        private int grade;

        public ReflectClass(String name, int age, int grade) {
            this.age = age;
            this.name = name;
            this.grade = grade;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public void testMethod(){
            System.out.println("testMethod is called...");
        }
    }
}
