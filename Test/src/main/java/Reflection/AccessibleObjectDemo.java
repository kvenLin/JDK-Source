package Reflection;

import java.lang.reflect.Field;

/**
 * @Author: clf
 * @Date: 19-3-2
 * @Description:
 * 不管成员变量是否是私有，isAccessible()默认返回的都是false，
 * 只有显式的使用setAccessible(true)才会该改变AccessibleObject内部override的布尔值
 */
public class AccessibleObjectDemo {
    public static void main(String[] args) throws NoSuchMethodException,
        SecurityException, NoSuchFieldException {
        SampleClass obj = new SampleClass("testAccess");
        Field sampleField = SampleClass.class.getDeclaredField("sampleField");
        System.out.println("sampleField.isAccessible: " + sampleField.isAccessible());//false
        try {
            String value = (String) sampleField.get(obj);//获取private异常
            System.out.println("value = " + value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sampleField.setAccessible(true);
        System.out.println("sampleField.isAccessible: " + sampleField.isAccessible());//true
        try {
            String value2 = (String) sampleField.get(obj);//正常获取private成员变量
            System.out.println("set true and value2 = " + value2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ======================================================================================
     * 运行结果：
     * 情况1： private String sampleField;//设为内部私有
     *
     * sampleField.isAccessible: false
     * sampleField.isAccessible: true
     *
     * //获取私有属性若没有setAccessible(true)将会发生异常
     * java.lang.IllegalAccessException: Class Reflection.AccessibleObjectDemo can not access a member of class Reflection.AccessibleObjectDemo$SampleClass with modifiers "private"
     * 	at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:102)
     * 	at java.lang.reflect.AccessibleObject.slowCheckMemberAccess(AccessibleObject.java:296)
     * 	at java.lang.reflect.AccessibleObject.checkAccess(AccessibleObject.java:288)
     * 	at java.lang.reflect.Field.get(Field.java:390)
     * 	at Reflection.AccessibleObjectDemo.main(AccessibleObjectDemo.java:19)
     *
     * set true and value2 = testAccess
     *
     * 情况2： public String sampleField;//设为公有
     *
     * sampleField.isAccessible: false //可以看出与是否私有属性无关，只有手动set更改才会改变
     * value = testAccess //但是公有属性获取不会发生异常
     * sampleField.isAccessible: true
     * set true and value2 = testAccess
     *
     */

    static class SampleClass {
        //设置为内部私有
        private String sampleField;
        //设为公有
//        public String sampleField;

        public SampleClass(String sampleField) {
            this.sampleField = sampleField;
        }

        public String sampleMethod(){
            return "sample";
        }

        public String getSampleField() {
            return sampleField;
        }

        public void setSampleField(String sampleField) {
            this.sampleField = sampleField;
        }
    }

}


