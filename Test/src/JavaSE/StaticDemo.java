package JavaSE;

/**
 * @Author: clf
 * @Date: 19-3-4
 * @Description:
 * static关键字总结:
 *
 * 执行顺序:
 * 静态代码块 ----> 非经态静态代码块 ----> 构造方法
 */
public class StaticDemo {
    public static void main(String[] args) {
        DemoClass demoClass = new DemoClass();
        DemoClass demoClass1 = new DemoClass();
    }

    static class DemoClass {
        static {
            System.out.println("静态代码块执行,并且只有第一次new操作才会执行");
        }

        {
            System.out.println("非静态代码块执行,每一次new操作都会执行");
        }

        public DemoClass() {
            System.out.println("构造器执行");
        }
    }
    /**
     * 运行结果:
     *
     * 静态代码块执行,并且只有第一次new操作才会执行
     * 非静态代码块执行,每一次new操作都会执行
     * 构造器执行
     * 非静态代码块执行,每一次new操作都会执行
     * 构造器执行
     */
}
