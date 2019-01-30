package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: clf
 * @Date: 18-12-29
 * @Description:
 * 锁条件测试
 */
public class ConditionTest {
    static Lock lock =  new ReentrantLock();//使用lock接口进行引用
    static final Condition condition = lock.newCondition();

    public static void main(String[] args) throws Exception {
        final Thread thread1 = new Thread("Thread 1 "){
            @Override
            public void run() {
                lock.lock();//线程1获取lock锁
                System.out.println(Thread.currentThread().getName() + "正在运行.......");

                try {
                    Thread.sleep(2 * 1000);
                    System.out.println(Thread.currentThread().getName() + "停止运行,等待一个signal");
                    condition.await();//调用 condition.await() 运行释放锁,将当前节点封装成一个 Node 放入 Condition Queue 里面,等待唤醒
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "获取一个 signal,继续执行");
                lock.unlock();//释放锁
            }
        };
        thread1.start();//线程1运行

        Thread.sleep(1 * 1000);//等待1秒


        Thread thread2 = new Thread("Thread 2 "){
            @Override
            public void run() {
                lock.lock();        // 线程2获取lock
                System.out.println(Thread.currentThread().getName() + "获取锁后,正在运行.....");
                thread1.interrupt(); // 对线程1 进行中断 看看中断后会怎么样? 结果 线程 1还是获取lock, 并且最后还进行 lock.unlock()操作

                try {
                    Thread.sleep(2 * 1000);
                }catch (Exception e){

                }
                condition.signal(); // 发送唤醒信号 从 AQS 的 Condition Queue 里面转移 Node 到 Sync Queue
                System.out.println(Thread.currentThread().getName() + " 发送一个 signal ");
                System.out.println(Thread.currentThread().getName() + " 发送 signal 结束");
                lock.unlock(); // 线程 2 释放锁
            }
        };
        System.out.println(thread2.getName() + "无法获取锁所以等待");
        thread2.start();
    }

    /**
     * 运行结果:
     * 1.体现了ReentrantLock的可重入获取锁,可多次获取多次释放
     * 2.支持中断(synchronized不支持)
     * 3.支持超时机制, 支持尝试获取lock, 支持不公平获取lock(主要区别在 判断 AQS 中的 Sync Queue 里面是否有其他线程等待获取 lock)
     * 4.支持调用 Condition 提供的 await(释放lock, 并等待), signal(将线程节点从 Condition Queue 转移到 Sync Queue 里面)
     * 在运行 synchronized 里面的代码若抛出异常, 则会自动释放监视器上的lock, 而 ReentrantLock 是需要显示的调用 unlock方法
     *
     * Thread 1 正在运行.......
     * Thread 2 无法获取锁所以等待
     * Thread 1 停止运行,等待一个signal
     * Thread 2 获取锁后,正在运行.....
     * Thread 2  发送一个 signal
     * Thread 2  发送 signal 结束
     * Thread 1 获取一个 signal,继续执行
     */
}
