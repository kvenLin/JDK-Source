package Thread.ThreadPoolAndCountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: clf
 * @Date: 19-2-27
 * @Description:
 * 应用场景:
 * 1.请求下载Excel表格数据时,采用ThreadPool和CountDownLatch
 * 定义多个线程同时对一份Excel进行数据的插入,
 * 但是只有在每个线程都执行完后才能写入response中,
 * 所以使用CountDownLatch对任务进行同步.
 *
 */
public class Test {
    //定义任务数量为500
    private static final int taskNum = 500;

    public static void main(String[] args) throws InterruptedException {
        //定义一个固定线程数量为200的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        //定义一个计数器
        final CountDownLatch countDownLatch = new CountDownLatch(taskNum);
        for (int i = 0; i < 500; i++) {
            final int finalI = i;
            executorService.execute(() -> {
                System.out.println("Thread " + Thread.currentThread().getName() + "running task :" + finalI);
                //每次执行一个任务就使用计数器进行减1操作
                countDownLatch.countDown();
            });
        }
        //等待所有线程都完成任务
        countDownLatch.await();
        executorService.shutdown();
        //执行后续的代码
        System.out.println("Running else task ...");
        System.out.println("All task finished ......");

    }
}
