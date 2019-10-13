package Reflection.Annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @Author: clf
 * @Date: 19-3-2
 * @Description:
 * 测试通过注解的反射
 */
public class Test {
    public static void main(String[] args) {
        Field[] fields = Product.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Info.class)){
                Info info = field.getAnnotation(Info.class);
                System.out.println("name = " + info.name() + ", address = " + info.address() + ", id = " + info.id());
            }
        }
    }

    static class Product {
        @Info(name = "淘宝", address = "浙江", id = 1)
        private String provider;
        private String name;
        private int price;
    }

}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface Info {
    int id() default 0;
    String name() default "";
    String address() default "";
}
