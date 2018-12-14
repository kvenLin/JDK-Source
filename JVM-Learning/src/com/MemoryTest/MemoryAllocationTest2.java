package com.MemoryTest;

/**
 * @Author: clf
 * @Date: 18-12-14
 * @Description:
 * 内存分配测试,
 * 查看Eden区的内存占比可知对象有先在Eden区进行分配
 *
 * 参数配置:
 * -verbose:gc -XX:+PrintGCDetails 打印GC日志
 * -Xms20M -Xmx20M 限定堆内存大小20M
 * -Xml10M 指定新生代内存10M
 * -XX:SurvivorRatio=8 指定Eden区内存为8M
 * 则两个Survivor区 默认各自为1M
 */
public class MemoryAllocationTest2 {

    public static void main(String[] args) {
        byte[] b1 = new byte[2 * 1024 * 1024];//2M
        byte[] b2 = new byte[2 * 1024 * 1024];//2M
        byte[] b3 = new byte[2 * 1024 * 1024];//2M
        //发生GC,但是并没有回收当前创建的b1,b2,b3
        byte[] b4 = new byte[4 * 1024 * 1024];//4M

        System.gc();//手动进行gc操作
    }

    /**
     * 不配置垃圾收集器 默认使用的是Parallel
     *  限定堆内存大小20M -Xms20M -Xmx20M ; 指定新生代内存10M -Xml10M ; 指定Eden区内存为8M -XX:SurvivorRatio=8
     * 运行结果:
     *
     * [GC (Allocation Failure) [DefNew: 7550K->363K(9216K), 0.0060790 secs] 7550K->6507K(19456K), 0.0061174 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     * Heap
     *  def new generation   total 9216K, used 4541K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   eden space 8192K,  51% used [0x00000000fec00000, 0x00000000ff014930, 0x00000000ff400000)
     *   from space 1024K,  35% used [0x00000000ff500000, 0x00000000ff55ae38, 0x00000000ff600000)
     *   to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *  tenured generation   total 10240K, used 6144K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *    the space 10240K,  60% used [0x00000000ff600000, 0x00000000ffc00030, 0x00000000ffc00200, 0x0000000100000000)
     *  Metaspace       used 2961K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     * 分析:
     * b1,b2,b3添加进入Eden区后占用了6M,因为Eden区只有8M所以,这时来的b4需要8M内存Eden区放不下,这是进行一次Minor GC
     * Minor GC : 发生在新生代,时间较短;
     * 因为存放不下,所以需要进行担保,将b1,b2,b3移到tenured generation,可以看到该区域占用看了60%即6M
     * 而这时b4再放入Eden区,占用50%即4M
     *
     * =====================================================================================
     *
     * 手动进行gc操作: System.gc(); 进行Full GC,耗时长
     * 运行结果:
     *
     * [GC (Allocation Failure) [DefNew: 7550K->363K(9216K), 0.0064149 secs] 7550K->6507K(19456K), 0.0064411 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [Full GC (System.gc()) [Tenured: 6144K->6144K(10240K), 0.0018166 secs] 10603K->10588K(19456K), [Metaspace: 2954K->2954K(1056768K)], 0.0018489 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * Heap
     *  def new generation   total 9216K, used 4526K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   eden space 8192K,  55% used [0x00000000fec00000, 0x00000000ff06bbc8, 0x00000000ff400000)
     *   from space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     *   to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *  tenured generation   total 10240K, used 6144K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *    the space 10240K,  60% used [0x00000000ff600000, 0x00000000ffc00030, 0x00000000ffc00200, 0x0000000100000000)
     *  Metaspace       used 2960K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     */
}
