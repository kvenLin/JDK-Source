package ThreadLocal;

/**
 * @Author: clf
 * @Date: 18-12-12
 * @Description:
 */
public class ThreadLocalDemo {
    private static final ThreadLocal<Integer> local = new ThreadLocal<Integer>(){
        protected Integer initialValue(){//重写initialValue()方法
            return 0;
        }
    };

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                /**
                 * ThreadLocal调用get()方法根据当前线程获取ThreadLocalMap对象,
                 * 再通过ThreadLocalMap通过this(当前的ThreadLocal)获取一个Entry节点
                 * 若Entry不为null时,返回Entry.value,源码为一个Object对象
                 * Entry为null时返回对应的初始Value,这里是Integer类型的对象0
                 */
                local.set(new Integer(5));
                int num = local.get().intValue();//0
                num ++;
                System.out.println(Thread.currentThread().getName() + ":" + num);
            }, "Thread-" + i);
        }
        local.remove();
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
