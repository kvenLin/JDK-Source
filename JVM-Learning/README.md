# JVM
 JVM 学习
## 初识JVM
* 针对OOM的情况调试JVM找到异常原因
* [参考博客](https://blog.csdn.net/Box_clf/article/details/84989261)

## JVM的内存管理
### 运行时数据区
![结构图示](https://images2015.cnblogs.com/blog/1182497/201706/1182497-20170616192739978-1176032049.png)
#### 程序计数器
* 是一块较小的内存空间,可以看作是当前线程所执行的字节码的行号指示器
* 线程独占
* 如果执行的是java方法,计数器记录的是正在执行的字节码指令的地址
* 如果执行native方法,计数器的值为undefined
* 此区域是唯一一个没有OOM(OutOfMemoryError)情况的区域

#### Java虚拟机栈
* 虚拟机栈描述的是Java方法执行的动态内存模型
* 栈帧
    * 每个方法都会创建一个栈帧,伴随着方法从创建到执行完成.
    * 用于存放: **局部变量表,操作数栈,动态链接,方法出口**等
* 局部变量表
    * 存放编译期可知的各种基本的数据类型,引用类型,returnAddress类型
    * 局部变量表的内存空间在编译期完成分配;
    当进入一个方法时,
    这个方法需要的栈帧分配内存是固定的,
    在方法运行期间是不会改变局部变量表的大小的
* 栈大小
    * 当栈满了,即对应的栈帧无法从虚拟机栈中申请到需要的栈内存,这时会抛出**StackOverflowError**
    * 如果不限制栈的大小,当申请的内存时虚拟机无法再提供足够的内存会抛出**OutOfMemoryError**
#### 本地方法栈
* 执行native方法
#### Java堆(heap)
* 线程共享
* 存放对象实例
* 垃圾收集器管理的主要区域
* 新生代,老年代,Eden空间
* 也可能发生OOM
* 配置参数:
    * -Xmx: 设置JVM最大可用内存
    * -Xms: 设置JVM初始内存,此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存
#### 方法区
* 线程共享
* 存储内容:
    * **运行时常量池**
    * 已被虚拟机加载的类信息: 类的版本,字段,方法,接口
    * 常量
    * 静态变量
    * 以及即时编译器编译后的代码等数据
* 方法区和永久代
    * 底层gc分代扩展到了方法区中,所以很多人习惯将方法区称为永久代,即使用永久代来实现方法区
    * 这样设计的好处,垃圾手机器可以像管理Java堆一样管理这部分内存,省去了为方法区重新编写代码的工作
    * 但实际上是不等价的,只是hotspot虚拟机中是这样设计,其他虚拟机不同
* 垃圾回收的行为
    * 一般**很少**对方法区进行垃圾回收
    * 原因: 效率低,成本高
    
### 运行时常量池
* 创建对象时存放
* StringTable(可以想象成HashSet): 避免创建对象的重复
* [结合代码示例理解heap和运行时常量池](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/ConstantPoolTest.java)

## 对象相关
### 对象的结构
* Header(对象头)
    * 自身运行时数据
        * hashCode
        * GC分代年龄
        * 锁状态标志
        * 偏向持有锁
        * 偏向线程ID
        * 偏向时间戳
    * 类型指针: 指向类的当前类的元数据的指针
* InstanceData
* Padding(对齐填充)

### 对象的创建
![对象的创建流程](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_027.png)

* 给对象分配内存
* 线程安全性问题
* 初始化对象
* 执行构造方法

#### 给对象分配内存
* 分配的方式:
    * 指针碰撞
    * 空闲列表
* 具体使用哪种方式:
    * 取决于堆是否规整
    * 是否规整由垃圾回收策略决定
#### 线程安全性问题
可能存在的情况: 
当线程A从空闲列表中查询出有地址为x0121的地址可以使用,
所以将该内存分配给线程A;
这时空闲列表还没有来的及更新,线程B来了查询也发现地址为x0121可以使用,
这时分配内存发现已经被A占用

* 解决方法:
    * 线程同步: 效率降低
    * 本地线程分配缓冲TLAB(Thread Local Allocation Buffer)
    
![本地线程分配缓冲](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_028.png)

### 对象的访问定位
* 使用句柄
* 直接指针(hotspot)

![对象访问定位方式](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_030.png)

----------------

## 垃圾回收
### 调试参数:
* -verbose:gc ; 打印垃圾回收的日志信息,是一个简单的日志信息
* -XX:+PrintGCDetails ; 查看详细的垃圾回收日志信息
### 如何判定对象为垃圾对象
* 引用计数法
    * 在对象中添加一个引用计数器,当有地方引用这个对象的时候引用计数器的值就+1,在引用失效的时候,计数器的值就-1
    * ![概念图](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_031.png)
* 可达性分析法
    * ![概念图](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_032.png)
* [测试对内存对象内部相互引用的回收](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/GCTest/GCMain.java)
* 作为GCRoot的对象
    * 虚拟机栈的局部变量表
    * 方法区的类属性所**引用的对象**
    * 方法区中常量所**引用的对象**
    * 本地方法栈中**引用的对象**
### 如何回收
* 回收策略
    * 标记-清除算法
        * 标记和清除过程都相对较慢,效率不是特别高
        * 空间问题:清除后得到的不连续空间影响后续的分配,当分配空间找不到时又会触发一次垃圾回收
    * 复制算法
        * ![复制清除算法实现垃圾回收](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_033.png)
    * 标记-整理算法(一般针对老年代进行回收)
    * 分代收集算法(标记-整理和复制算法的结合)
        * 针对不同的内存区域(新生代,老年代)选择不同的回收算法
### 垃圾收集器
* Serial
    * 最基本,发展最悠久的
    * 单线程的
* ParNew
    * 多线程的
    * 和Serial算法基本一致
* CMS(Concurrent Mark Sweep)
    * sun公司
    * 采用的是标记清除算法
    * 工作过程:
        * 初始标记
        * 并发标记
        * 重新标记
        * 并发清理
    * 优点:
        * 并发收集: 边生成垃圾的同时可以边进行垃圾回收
        * 低停顿
    * 缺点:
        * 占用大量的CPU资源
        * 无法处理浮动的垃圾
        * 出现 Concurrent Mode Failure
        * 空间碎片
* Parallel Scavenge
    * 复制算法(新生代收集器,针对新生代内存)
    * 多线程
    * 可控制的吞吐量 = 运行用户代码的时间 / (运行用户代码的时间 + 垃圾回收所占用时间)
    * -XX:MaxGCPauseMills 垃圾收集器最大停顿时间(单位ms)
    * -XX:GCTimeRatio 吞吐量大小(0 - 100)
        * 默认值为99
* G1
    * 采用标记-整理
    * 步骤:
        * 初始标记
        * 并发标记
        * 最终标记
        * 筛选回收
## 内存分配
### 内存分配策略
* 优先分配到Eden区
* 大对象直接分配到老年代
* 长期存活的对象分配到老年代
* 空间分配担保(即分配空间不足,会向老年代借空间)
* 动态对象的年龄判断
### Eden区域
* 不进行配置时默认使用的回收器是Parallel
* 相关参数学习:
    * -Xms20M -Xmx20M 限定堆内存大小20M
    * -Xml10M 指定新生代内存10M
    * -XX:SurvivorRatio=8 指定Eden区内存为8M
    * -XX:PretenureSizeThreshold=6M 大对象判定阀值为6M
* 内存分配测试:
    * [创建对象内存分配和分配器使用分析](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/MemoryTest/MemoryAllocationTest1.java)
    * [关于Eden区内存分配的分析](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/MemoryTest/MemoryAllocationTest2.java)
    * [关于大对象判定分析](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/MemoryTest/MemoryAllocationTest3.java)

### 空间分配担保
* 参数:
    * -XX:-HandlePromotionFailure 禁用空间分配担保,"+"号为开启,默认为开启
### 逃逸分析和栈上分配
* 逃逸分析:
    * 分析对象的作用域