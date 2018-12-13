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
    
![本地线程分配缓冲](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_028.png))

### 对象的访问定位
* 使用句柄
* 直接指针(hotspot)

![对象访问定位方式](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_029.png)

## 垃圾回收