package JavaSE.lambda;

/**
 * @Author: clf
 * @Date: 19-3-8
 * @Description:
 * lambda应用测试
 */
public class Test {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("传统方式实现线程");
            }
        }).start();

        //lambda方式
        new Thread(() -> {
            System.out.println("使用lambda方式实现线程");
        }).start();
    }
}
