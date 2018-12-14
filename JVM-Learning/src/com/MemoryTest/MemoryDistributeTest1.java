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
 */
public class MemoryDistributeTest1 {

    public static void main(String[] args) {
//        byte[] b1 = new byte[4 * 1024 * 1024];//4M
        byte[] b1 = new byte[40 * 1024 * 1024];//40M

    }

    /**
     * byte[] b1 = new byte[4 * 1024 * 1024];//4M
     * =======================================================================================
     * 不配置垃圾收集器 默认使用的是Parallel
     * 运行结果:
     * Heap
     *  PSYoungGen      total 36864K, used 6636K [0x00000000d6f00000, 0x00000000d9800000, 0x0000000100000000)
     *   eden space 31744K, 20% used [0x00000000d6f00000,0x00000000d757b1c0,0x00000000d8e00000)
     *   from space 5120K, 0% used [0x00000000d9300000,0x00000000d9300000,0x00000000d9800000)
     *   to   space 5120K, 0% used [0x00000000d8e00000,0x00000000d8e00000,0x00000000d9300000)
     *  ParOldGen       total 84992K, used 0K [0x0000000084c00000, 0x0000000089f00000, 0x00000000d6f00000)
     *   object space 84992K, 0% used [0x0000000084c00000,0x0000000084c00000,0x0000000089f00000)
     *  Metaspace       used 2961K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     *  -------------------------------------------------------------------------------------------
     *
     * 添加配置使用Serial收集器 配置参数 -XX:+UseSerialGC
     * 运行结果:
     * Heap
     *  def new generation   total 38080K, used 6818K [0x0000000084c00000, 0x0000000087550000, 0x00000000add50000)
     *   eden space 33856K,  20% used [0x0000000084c00000, 0x00000000852a8a38, 0x0000000086d10000)
     *   from space 4224K,   0% used [0x0000000086d10000, 0x0000000086d10000, 0x0000000087130000)
     *   to   space 4224K,   0% used [0x0000000087130000, 0x0000000087130000, 0x0000000087550000)
     *  tenured generation   total 84672K, used 0K [0x00000000add50000, 0x00000000b3000000, 0x0000000100000000)
     *    the space 84672K,   0% used [0x00000000add50000, 0x00000000add50000, 0x00000000add50200, 0x00000000b3000000)
     *  Metaspace       used 2960K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     * 可以看到eden space占用20%,所以小对象的内存分配会直接进入Eden区
     * ==========================================================================================
     *
     * byte[] b1 = new byte[40 * 1024 * 1024];//40M
     * 大对象的创建,内存分配情况
     * 运行结果:
     * Heap
     *  def new generation   total 38080K, used 2722K [0x0000000084c00000, 0x0000000087550000, 0x00000000add50000)
     *   eden space 33856K,   8% used [0x0000000084c00000, 0x0000000084ea8a28, 0x0000000086d10000)
     *   from space 4224K,   0% used [0x0000000086d10000, 0x0000000086d10000, 0x0000000087130000)
     *   to   space 4224K,   0% used [0x0000000087130000, 0x0000000087130000, 0x0000000087550000)
     *  tenured generation   total 84672K, used 40960K [0x00000000add50000, 0x00000000b3000000, 0x0000000100000000)
     *    the space 84672K,  48% used [0x00000000add50000, 0x00000000b0550010, 0x00000000b0550200, 0x00000000b3000000)
     *  Metaspace       used 2961K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     * 可以看到tenured generation占用48%,大对象的内存分配会直接进入老年代
     */
}
