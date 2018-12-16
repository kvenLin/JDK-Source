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
* 栈帧,具体结构解析: [运行时栈帧结构](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/README.md#运行时栈帧结构)
    * 每个方法都会创建一个栈帧,伴随着方法从创建到执行完成.
    * 用于存放: **局部变量表,操作数栈,动态链接,方法出口**等
    * ![栈帧结构](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_037.png)
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
* 可以作为GCRoot的对象
    * 虚拟机栈的局部变量表
    * 方法区的类属性所**引用的对象**
    * 方法区中常量所**引用的对象**
    * 本地方法栈中**引用的对象**
### 如何回收
* 回收策略
    * 标记-清除算法
        * 标记和清除过程都相对较慢,效率不是特别高
        * 空间问题:清除后得到的不连续空间影响后续的分配,当分配空间找不到时又会触发一次垃圾回收
    * 复制算法(适用于新生代内存)
        * ![复制清除算法实现垃圾回收](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_033.png)
    * 标记-整理算法(适用于老年代进行回收)
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
    * 复制算法(默认的新生代收集器,针对新生代内存)
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
    * -XX:+UseSerialGC 使用Serial收集器
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
* 对于没有逃逸的对象只会在栈上分配
* 对象的大小其实在运行时也是确定的,因此即使出现了栈上内存分配,也不会导致栈帧改变大小
* 逃逸分析:
    * [分析对象的作用域](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/StackTest/StackAllocation.java)
## JVM常用工具和主要参数
* [使用教程参考博客](https://blog.csdn.net/wyy101301/article/details/80481630)
### jps
* java process status
* 参数:
    * -m 查看运行时传入主类的参数
    * -v 查看虚拟机参数
    * -l 运行的主类的全名或者是jar包名称
    * [具体参数使用文档](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jps.html)
    
### jstat
* 监视虚拟机的各种运行状态信息
* 类装载信息,内存,垃圾收集,jit编译的信息
* [具体参数使用方法文档](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jstat.html)
### jinfo
* 实时查看和调整虚拟机的各项参数
* -flag [+/-]<name\> : 启动或禁用某个参数
* -flag <name\>=<value\> : 某个参数值
### jmap
* Memory map for java : 生成堆存储快照
* 使用方法 : jmap -dump:live,format=b,file=/home/hk/heap.bin <pid\>
    * live若当前进程是活着的才会进行操作
    * format=b , 二进制格式
    * file=/home/hk/heap.bin , 指定生成的文件路径
    * <pid\> 指定对应的进程号
### jconsole
* [使用教程](https://docs.oracle.com/javase/1.5.0/docs/guide/management/jconsole.html)

##Class 文件
### Class文件结构
* 是一组以8位字节为基础单位的二进制流,各个数据项目严格按照顺序紧凑的排在Class文件中,中间没有分隔符
* 采用Class文件格式的目的是为了提高文件的读取效率
* Class文件中有两种数据类型:
    * 无符号数
    * 表
* [结构分析](https://blog.csdn.net/sinat_38259539/article/details/78248454)
### 设计理念和意义

![概念图](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_035.png)

## 类加载
加载流程:
![加载流程图](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/JVM-Learning/src/image/选区_036.png)
### 类加载机制
* 概述:
> 虚拟机把描述类的数据从Class文件加载到内存,并对数据进行校验,解析和初始化,
最终形成可以被虚拟机直接使用的Java类型,这就是虚拟机的类型加载机制
* 懒加载: 用的时候才进行加载

### 类加载的时机
* 当初始化一个类时,如果发现其父类还没有进行过初始化,则需要先触发其父类的初始化
* 当虚拟机启动时,用户需要指定一个执行的主类(包含main()方法的那个类),虚拟机会先初始化这个主类
* [运行实例](https://github.com/kvenLin/JDK-Source/tree/master/JVM-Learning/src/com/ClassLoad/test1/Child.java)

#### 不会被初始化的例子
* 通过子类引用父类的静态字段,子类不会被初始化,只会初始化父类
* **通过数组定义来引用类**
* 调用类的常量
* [运行实例](https://github.com/kvenLin/JDK-Source/tree/master/JVM-Learning/src/com/ClassLoad/test2)

### 加载
* 通过一个类的全限定名来获取定义此类的二进制流
* 将这个字节流所代表的静态存储结构转化为方法区的运行时数据
* 在内存中生成一个代表这个类的Class对象(**特例,在hotspot中,这里的Class对象不会放到堆中,而是放到方法区中**),作为这个类的各种数据的访问入口
> Class对象即对象类型数据,存放于方法区中,具体可以参考对象的访问节
#### 加载源
* 文件
    * Class文件
    * jar文件
* 网络
* 计算生成一个二进制流
*由其他文件生成
    * jsp
* 数据库

### 连接
#### 验证
* 确保Class文件的字节流信息符合虚拟机的要求
* 校验信息:
    * 文件格式验证
    * 元数据验证(语义方面的校验)
    * 字节码验证
    * 符号引用验证
#### 准备
* 正式为类变量(static修饰的变量)分配内存并设置**变量的初始值**.这些变量使用的内存都将在方法区中进行分配
* 这里的初始值并不是我们指定的值,而是其默认的值.**但是如果被final修饰,那么在这个过程中,常量值会被一同指定**
>int 0;
boolean false;
float 0.0;
char '0';
抽象数据类型 null
#### 解析
* 虚拟机将常量池中的符号引用替换为直接引用的过程
* 解析内容:
    * 类或者接口的解析
    * 字段的解析
    * 类方法的解析
    * 接口的解析
### 初始化
* 类加载的最后一步
* 初始化是执行<clinit\>()方法的过程
    * [初始化示例](https://github.com/kvenLin/JDK-Source/tree/master/JVM-Learning/src/com/ClassLoad/test3/InitTest1.java)
> <init\>()是初始化实例对象的,<clinit\>()是初始化类或者接口的
### 双亲委派模型
* 实际就是子类加载器加载时会先扔给父类进行加载,如果父类加载不了抛出异常后,再交给子类进行加载
* [对ClassLoader.java的loadClass()加载流程进行分析](https://github.com/kvenLin/JDK-Source/jdk1.8/src/java/lang/ClassLoader.java),第403行

## 虚拟机字节码执行引擎
### 运行时栈帧结构
* [参考博客](http://www.cnblogs.com/noKing/p/8167700.html)
* 局部变量表
    * 是一片逻辑连续的内存空间,最小单位是Slot,用来存放方法参数和方法内部定义的局部变量,没有具体大小,默认32位
    * byte,boolean,short,char,int,float,reference,returnAddress类型都可以用32位空间内存存放
    * Java中的long和double类型是64位,占用两个Slot
* 操作数栈
    * 对于byte,short以及char类型值在眼入到操作数栈之前,也会被转换成int
    * [操作数栈指令执行流程](https://github.com/kvenLin/JDK-Source/tree/master/JVM-Learning/src/com/JavacTest/)
* 动态链接
    * 实际就是一个解析找到调用方法的引用
* 方法的返回地址
    * 进入一个方法完成后需要返回被调用处,就是返回地址
* 附加信息
    * 虚拟机实现自已可以添加一些没有的规范到栈帧中,取决于虚拟机
   
### 方法调用 
* 解析调用
    * 在编译阶段就进行了确定调用的方法称为解析调用
    * [测试用例](https://github.com/kvenLin/JDK-Source/tree/master/JVM-Learning/src/com/MethodInvokeTest/test1/)
* 分派调用
    * 在运行期间才能确定调用的方法就是分派调用
    * 静态分派代用
        * [测试用例](https://github.com/kvenLin/JDK-Source/tree/master/JVM-Learning/src/com/MethodInvokeTest/test2/)
    * 动态分派调用
        * 方法的重写即动态分派调用