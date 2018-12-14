package com.GCTest;

/**
 * @Author: clf
 * @Date: 18-12-14
 * @Description:
 * 测试当对象进行内部引用,
 * 但无外部引用时,
 * 虚拟机是否会进行垃圾回收,
 * 即判断当前虚拟机的判定垃圾的方式是什么
 */
public class GCMain {

    private Object instance;

    public GCMain() {
        //每创建一次该对象开辟一块20M内存,使得回收时有内够更明显的观察
        byte[] m = new byte[20 * 1024 * 1024];
    }

    public static void main(String[] args) {
        GCMain m1 = new GCMain();
        GCMain m2 = new GCMain();



        //对内存中内部对象进行引用
        m1.instance = m2;
        m2.instance = m1;

        //断开栈内存中的引用
        m1 = null;
        m2 = null;

        //垃圾回收
        System.gc();
    }
    /**
     * 运行结果:
     * [GC (System.gc()) [PSYoungGen: 22385K->416K(36864K)] 42865K->20904K(121856K), 0.0018773 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     * [Full GC (System.gc()) [PSYoungGen: 416K->0K(36864K)] [ParOldGen: 20488K->362K(84992K)] 20904K->362K(121856K), [Metaspace: 2954K->2954K(1056768K)], 0.0103675 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     * Heap
     *  PSYoungGen      total 36864K, used 317K [0x00000000d6f00000, 0x00000000d9800000, 0x0000000100000000)
     *   eden space 31744K, 1% used [0x00000000d6f00000,0x00000000d6f4f738,0x00000000d8e00000)
     *   from space 5120K, 0% used [0x00000000d8e00000,0x00000000d8e00000,0x00000000d9300000)
     *   to   space 5120K, 0% used [0x00000000d9300000,0x00000000d9300000,0x00000000d9800000)
     *  ParOldGen       total 84992K, used 362K [0x0000000084c00000, 0x0000000089f00000, 0x00000000d6f00000)
     *   object space 84992K, 0% used [0x0000000084c00000,0x0000000084c5aa18,0x0000000089f00000)
     *  Metaspace       used 2961K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 316K, capacity 388K, committed 512K, reserved 1048576K
     *
     *   分析:
     *   22385K->416K(36864K)说明回收了部分内存,说明jdk8不是使用的引用计数的方式进行垃圾回收
     */
}
