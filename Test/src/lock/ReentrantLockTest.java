package lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: clf
 * @Date: 19-2-27
 * @Description:
 */
public class ReentrantLockTest {
    private final static int threadNum = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() -> {
                new Task().task();
            });
        }
        executorService.shutdown();
    }

    static class Task{
        private Lock lock = new ReentrantLock();

        public void task(){
            //获取锁
            lock.lock();
            //使用try{ }finally{ }保证不管方法是否发生异常都会进行释放锁的操作
            try {
                count();
            }finally {
                //释放锁
                lock.unlock();
            }
        }

        //进行计数操作
        public void count(){
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread " + Thread.currentThread().getName() + " counting num is " + i);
            }
        }
    }

    /**
     * =================================================================================
     * 运行结果：
     * Thread pool-1-thread-1 counting num is 0
     * Thread pool-1-thread-1 counting num is 1
     * Thread pool-1-thread-1 counting num is 2
     * Thread pool-1-thread-1 counting num is 3
     * Thread pool-1-thread-1 counting num is 4
     * Thread pool-1-thread-2 counting num is 0
     * Thread pool-1-thread-2 counting num is 1
     * Thread pool-1-thread-2 counting num is 2
     * Thread pool-1-thread-2 counting num is 3
     * Thread pool-1-thread-2 counting num is 4
     * Thread pool-1-thread-3 counting num is 0
     * Thread pool-1-thread-3 counting num is 1
     * Thread pool-1-thread-3 counting num is 2
     * Thread pool-1-thread-3 counting num is 3
     * Thread pool-1-thread-3 counting num is 4
     * ......
     *
     * 只有获取锁的线程的Task做完之后释放锁，其他线程才能获取锁执行Task
     */
}
