package lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: clf
 * @Date: 19-3-1
 * @Description:
 * Semaphore 是一种基于计数的信号量。它可以设定一个阈值,基于此,多个线程竞争获取许可信
 * 号,做完自己的申请后归还,超过阈值后,线程申请许可信号将会被阻塞。Semaphore 可以用来
 * 构建一些对象池,资源池之类的,比如数据库连接池
 */
public class SemaphoreTest {
    private final static int threadNum = 10;
    //创建一个计数阈值为 5 的信号量对象
    private static Semaphore semaphore = new Semaphore(5);
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            final int finalI = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    try {
                        System.out.println("Thread " + finalI + " 获取到一个资源,继续后续工作...");
                        //业务逻辑
                        Thread.sleep(3000);
                        System.out.println("Thread " + finalI + " 工作完成,释放当前获取的资源...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        //使用finally,避免业务出现异常无法释放锁
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
    /**
     * ===========================================================
     * 运行结果:
     * Thread 1 获取到一个资源,继续后续工作...
     * Thread 2 获取到一个资源,继续后续工作...
     * Thread 3 获取到一个资源,继续后续工作...
     * Thread 0 获取到一个资源,继续后续工作...
     * Thread 4 获取到一个资源,继续后续工作...
     *
     * Thread 1 工作完成,释放当前获取的资源...
     * Thread 2 工作完成,释放当前获取的资源...
     * Thread 5 获取到一个资源,继续后续工作...
     * Thread 6 获取到一个资源,继续后续工作...
     * Thread 3 工作完成,释放当前获取的资源...
     * Thread 0 工作完成,释放当前获取的资源...
     * Thread 7 获取到一个资源,继续后续工作...
     * Thread 8 获取到一个资源,继续后续工作...
     * Thread 4 工作完成,释放当前获取的资源...
     * Thread 9 获取到一个资源,继续后续工作...
     *
     * Thread 5 工作完成,释放当前获取的资源...
     * Thread 6 工作完成,释放当前获取的资源...
     * Thread 7 工作完成,释放当前获取的资源...
     * Thread 8 工作完成,释放当前获取的资源...
     * Thread 9 工作完成,释放当前获取的资源...
     *
     * 只允许做最多5个线程同时持有资源,后续线程只有等待前面的线程释放资源后再继续抢占资源进行工作
     */
}
