package com.MemoryTest;

/**
 * @Author: clf
 * @Date: 18-12-14
 * @Description:
 * 内存分配测试,
 * 针对大对象判定的分析
 *
 * 参数配置:
 * -verbose:gc -XX:+PrintGCDetails 打印GC日志
 * -Xms20M -Xmx20M 限定堆内存大小20M
 * -Xml10M 指定新生代内存10M
 * -XX:SurvivorRatio=8 指定Eden区内存为8M
 * 则两个Survivor区 默认各自为1M
 */
public class MemoryDistributeTest3 {

    private static final int M = 1024 * 1024;

    public static void main(String[] args) {
//        byte[] b1 = new byte[8 * M];//8M
        byte[] b2 = new byte[7 * M];//7M

    }

    /**
     * byte[] b1 = new byte[8 * M];//8M
     * 运行结果:
     * Heap
     *  def new generation   total 9216K, used 1570K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   eden space 8192K,  19% used [0x00000000fec00000, 0x00000000fed88a98, 0x00000000ff400000)
     *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     *  tenured generation   total 10240K, used 8192K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *    the space 10240K,  80% used [0x00000000ff600000, 0x00000000ffe00010, 0x00000000ffe00200, 0x0000000100000000)
     *  Metaspace       used 2959K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     * 分析: 可以看到tenured generation占用80%,即b1进入了老年代
     *
     * ======================================================================================
     * byte[] b2 = new byte[7 * M];//7M
     * 运行结果:
     * [GC (Allocation Failure) [DefNew: 1406K->363K(9216K), 0.0012374 secs] 1406K->363K(19456K), 0.0012586 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * Heap
     *  def new generation   total 9216K, used 7613K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   eden space 8192K,  88% used [0x00000000fec00000, 0x00000000ff314930, 0x00000000ff400000)
     *   from space 1024K,  35% used [0x00000000ff500000, 0x00000000ff55aea0, 0x00000000ff600000)
     *   to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *  tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *    the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
     *  Metaspace       used 2961K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     * 分析: 触发GC并将b2放入Eden区,所以这时JVM认为7M不为一个大对象
     *
     * ======================================================================================
     *  byte[] b2 = new byte[7 * M];//7M
     *  添加参数: -XX:PretenureSizeThreshold=6M,指定大对象的判定阀值为6M
     *  运行结果:
     *  Heap
     *  def new generation   total 9216K, used 1570K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   eden space 8192K,  19% used [0x00000000fec00000, 0x00000000fed88a98, 0x00000000ff400000)
     *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     *  tenured generation   total 10240K, used 7168K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *    the space 10240K,  70% used [0x00000000ff600000, 0x00000000ffd00010, 0x00000000ffd00200, 0x0000000100000000)
     *  Metaspace       used 2961K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     * 分析: 由于添加了判定大对象的阀值为6M,所以这时认为7M是大对象了,直接进入老年代
     *
     */
}
