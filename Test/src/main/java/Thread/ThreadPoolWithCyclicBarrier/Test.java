package Thread.ThreadPoolWithCyclicBarrier;

import java.util.concurrent.*;

/**
 * @Author: clf
 * @Date: 19-2-27
 * @Description:
 * 应用场景：
 * 我们打扑克斗地主，必须要满了三个人才能开始游戏，
 * 这时候就可以用CyclicBarrier，设定屏障值为3，
 * 线程数量不到三必须等待，满足了3才开始游戏。
 */
public class Test {
    private static final int threadNum = 20;

    public static void main(String[] args) throws InterruptedException {
        //设置cyclicBarrier同步线程数为3
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 19; i++) {
            final int finalI = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                System.out.println("Thread " + finalI + "is waiting for beginning, current waiting num is " + cyclicBarrier.getNumberWaiting());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                //达到同步线程数，执行后续的操作
                System.out.println("Thread " + finalI + "is playing game...");
            });
        }
        threadPool.shutdown();
    }
}

/**
 * ======================================================================
 * 运行结果：
 * Thread 0is waiting for beginning, current waiting num is 0
 * Thread 1is waiting for beginning, current waiting num is 1
 * Thread 2is waiting for beginning, current waiting num is 2
 * Thread 2is playing game...
 * Thread 0is playing game...
 * Thread 1is playing game...
 * Thread 3is waiting for beginning, current waiting num is 0
 * Thread 4is waiting for beginning, current waiting num is 1
 * Thread 5is waiting for beginning, current waiting num is 2
 * Thread 5is playing game...
 * Thread 3is playing game...
 * Thread 4is playing game...
 */
