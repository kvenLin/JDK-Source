package Thread.WaitSleepNotifyYieldJoin;

/**
 * @Author: clf
 * @Date: 19-3-2
 * @Description:
 * 线程中wait()、sleep（）、notify（）、yield（）、join的使用
 */
public class Test {

    public static void main(String[] args) {
        Thread thread0 = new Thread(new MyThread());
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        Thread thread3 = new Thread(new MyThread());
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static class MyThread implements Runnable{

        @Override
        public void run() {
            synchronized (Test.class){
                System.out.println(Thread.currentThread().getName() + "执行读操作");
                System.out.println(Thread.currentThread().getName() + "进入等待操作");
                if (Thread.currentThread().getName().equals("Thread-0")){
                    try {
                        /**
                         * 进入等待状态直到notify唤醒或者notifyAll唤醒
                         */
                        Test.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (Thread.currentThread().getName().equals("Thread-1")){
                    /**
                     * 线程1进入,唤醒线程0,唤醒操作不会让出锁,自己执行完才会让出锁
                     */
                    Test.class.notify();
                    System.out.println(Thread.currentThread().getName() + "不好意思, 我赶时间插个队");
                }else if (Thread.currentThread().getName().equals("Thread-2")){

                }else if (Thread.currentThread().getName().equals("Thread-3")){
                    /**
                     * yield方法让出资源,但并不会让出锁,还是会在线程执行完后才执行其他线程
                     */
                    Thread.yield();
                    System.out.println(Thread.currentThread().getName() + "我让资源不让锁, 你们还是等我释放锁才能继续执行");
                    System.out.println(Thread.currentThread().getName() + "让我休息10s钟");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "执行结束");
            }
        }
    }
    /**
     * ======================================================================
     * 运行结果:
     * Thread-0执行读操作
     * Thread-0进入等待操作
     * Thread-1执行读操作
     * Thread-1进入等待操作
     * Thread-1不好意思, 我赶时间插个队
     * Thread-1执行结束
     * Thread-0执行结束
     * Thread-2执行读操作
     * Thread-2进入等待操作
     * Thread-2执行结束
     * Thread-3执行读操作
     * Thread-3进入等待操作
     * Thread-3我让资源不让锁, 你们还是等我释放锁才能继续执行
     * Thread-3让我休息10s钟
     */
}
