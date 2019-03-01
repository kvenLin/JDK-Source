package lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: clf
 * @Date: 19-3-1
 * @Description:
 */
public class ReadWriteLockTest {
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        //读取线程
        new Thread(() -> {
            while (true){
                read();
            }
        }).start();

        //写入线程
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1000);
                    write();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void read(){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "is reading...");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void write(){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " is writing...");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {

            readWriteLock.writeLock().unlock();
        }

    }
    /**
     * 运行结果:
     * Thread-0is reading...
     * Thread-0is reading...
     * Thread-0is reading...
     * Thread-1 is writing...
     * Thread-1 finished.
     * Thread-0is reading...
     * Thread-0is reading...
     * Thread-1 is writing...
     * Thread-1 finished.
     * Thread-0is reading...
     * Thread-0is reading...
     * Thread-1 is writing...
     * Thread-1 finished.
     * Thread-0is reading...
     * Thread-0is reading...
     */
}
