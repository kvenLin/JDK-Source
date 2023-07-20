package Thread.CompletableFutureTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //T2 创建任务T2的FutureTask
        FutureTask<String> t2 = new FutureTask<>(new T2Task());
        //创建任务T1的FutureTask
        FutureTask<String> t1 = new FutureTask<>(new T1Task(t2));

        //T1任务需要T2任务的结果
        //创建线程T1, 并且启动
        Thread T1 = new Thread(t1);
        T1.start();
        //创建线程T2, 并且启动
        Thread T2 = new Thread(t2);
        T2.start();
        //等待线程T1执行结果
        System.out.println(t1.get());
    }

    //执行结果:
    //T2: 洗茶壶...
    //T1: 洗水壶...
    //T2: 洗茶杯...
    //T1: 烧开水...
    //T2: 拿茶叶...
    //T1: 拿到茶叶:龙井
    //T1: 泡茶...
    //上茶:龙井
}

//T1任务: 洗水壶, 烧开水, 泡茶
class T1Task implements Callable<String> {
    FutureTask<String> ft2;

    //T1任务需要T2任务的FutureTask
    T1Task(FutureTask<String> ft2) {
        this.ft2 = ft2;
    }

    @Override
    public String call() throws Exception {
        System.out.println("T1: 洗水壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1: 烧开水...");
        TimeUnit.SECONDS.sleep(15);

        //获取T2线程的茶叶
        String tf = ft2.get();
        System.out.println("T1: 拿到茶叶:" + tf);

        System.out.println("T1: 泡茶...");
        return "上茶:" + tf;
    }
}

//T2任务: 洗茶壶, 洗茶杯, 拿茶叶
class T2Task implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("T2: 洗茶壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2: 洗茶杯...");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2: 拿茶叶...");
        TimeUnit.SECONDS.sleep(1);
        return "龙井";
    }
}
