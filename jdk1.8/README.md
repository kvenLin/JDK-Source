# JDK-Source
jdk源码学习
## 目录
* [如何进行源码的阅读?](#如何进行源码的阅读?)
    * [对于源码阅读顺序](#对于源码阅读顺序)
    * [阅读源码的方法](#阅读源码的方法)
* [String](#String)
    * [常考面试题](#常考面试题)
* [整型缓存](#整型缓存)
* [重载](#重载)
* [ArrayList](#arraylist(非线程安全))
* [Vector](#vector(线程安全))
* [LinkedList](#linkedlist(非线程安全))
* [HashMap](#hashmap(非线程安全))
    * [put和get方法分析](#put和get方法)
    * [HashMap引出的求余%和与运算&转换问题](#HashMap引出的求余%和与运算&转换问题)
* [Hashtable](#hashtable(线程安全))
* [LinkedHashMap](#linkedhashmap(非线程安全))
* [HashSet](#hashset(非线程安全))
* [ConcurrentHashMap](#concurrenthashmap(线程安全))
* [ThreadLocal](#ThreadLocal)
    * [主要方法](#主要方法)
    * [关于为什么ThreadLocal中的Entry申明为弱引用?](#关于为什么ThreadLocal中的Entry申明为弱引用?)
    * [相关问题](#相关问题)
* [IO流](#IO流)
* [序列化](#序列化)

## 如何进行源码的阅读?
### 对于源码阅读顺序
* 建议先从常用类进行入手进行解读,初次尝试源码阅读可以结合别人的博客进行阅读
    * 具体可以先从**方法**和**成员变量**的作用进行理解和解读
    * 然后进行方法内部的具体实现进行解读
    * 最后结合子类和继承的父类或接口进行解读
* [后续源码阅读顺序参考博客](https://blog.csdn.net/qq_21033663/article/details/79571506)
### 阅读源码的方法
这里分享一下自己阅读的经验,我使用的是intellij
* 学会使用bookmarks,用于标记阅读源码的位置便于下一次继续进行阅读
    * bookmarks标记: ctrl + 单击鼠标左键
    ![bookmark标记](http://raw.githubusercontent.com/kvenLin/JDK-source/master/Test/src/image/选区_002.png)
    * 查看标记内容: Shift + F11
    ![查看标记内容](http://raw.githubusercontent.com/kvenLin/JDK-source/master/Test/src/image/选区_003.png)
    * [参考博客](https://blog.csdn.net/qq_36376059/article/details/80277767)
* **学会进行写注释**,可以结合博客和自己的理解然后进行分析写注释
## String
* String使用char[]保存字符串的值,而且还是静态常量
* 当相同的字符串被多次创建(使用双引号显式声明)时,**字符串常量对象会被创建在常量池中**,且只会有一个对象
* intern(): 去常量池中寻找当前的字符串常量
    * 如果有则直接返回常量池中的对象
    * 如果没有会将当前的字符串引用(jdk1.7之后)放入常量池(如果是jdk1.7之前会直接复制到常量池),然后返回
* 字符串对象的+号运算,会在堆内存中生成新的字符串对象
### 常考面试题
* question1:[结合代码理解](https://github.com/kvenLin/JDK-Source/blob/master/JVM-Learning/src/com/ConstantPoolTest.java)
    * String s1 = "abc"; 
      String s2 = "abc";
      String s3 = new String("abc");
       问有几个实例对象?
    >答: 两个对象,堆中一个"abc",常量池一个"abc"
    * [参考博客](https://blog.csdn.net/Mypromise_TFS/article/details/81504137)
* question2:[这段代码运行结果是什么](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/String/StringTest.java)
    * 分析: ![String实例创建流程](https://raw.githubusercontent.com/kvenLin/JDK-Source/master/Test/src/image/选区_044.png)
## 整型缓存
* 包括:
    * Integer(-128 --- +127)
    * Long(-128 --- +127)
    * Short(-128 --- +127)
    * Byte(-128 -- +127)
    * Character(0 -- +127): 表示标准的128个ASCII字符
* 缓存机制,结合源码查看
    * 缓存内容: 存在一个cache[]数组中
    * 类加载时通过静态代码块进行初始化
* [运行示例分析](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/IntegerTest/IntegerDemo.java)

## 重载
* 方法名相同,参数不同
* java选择调用哪个重载方法,是在编译时期决定的
    * 运行时类型虽然不同,但是编译时类型是相同的,所以选用了同一个重载
* 对重载方法的选择是**静态的**,对覆盖方法的选择是动态的
* 覆盖: 在子类中重写父类的方法
    * java对调用那个覆盖方法,是运行时决定的,依据是对象的运行时类型
    * 如果子类覆盖了覆盖的方法,调用子类实例的方法,
    那么调用的就是子类中重写的方法,因为运行时类型一定是子类的类型,
    引用子类的那个类型可以是父类类型
    
## ArrayList(非线程安全)
* 实现了 RandomAccess 接口,标识着这个类可以快速随机访问
* 构造函数
    * 无参: 底层默认创建一个空数组,在进行第一次添加操作时进行扩容为默认的**容量10**
    * 传入int n: 创建一个容h量为n的数组
    * 传入集合: 使用Arrays.copyOf()进行遍历赋值到当前ArrayList中
* 底层数组设定的最大长度为**MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8**
* 扩容条件: **插入元素时,当需要元素空间大于数组现有容量时进行扩容**
* 只有在minCapacity > MAX_ARRAY_SIZE时会扩容到Integer.MAX_VALUE
* 核心扩容方法 grow(int minCapacity):
    * 传入minCapacity,最小所需容量的空间
    * 实现: `newCapacity = oldCapacity + (oldCapacity >> 1);`
    * 使用的移位操作,**扩容为之前的1.5倍容量**
* copyOf()和arraycopy()
    * toString方法底层使用Arrays.copyOf()
    * add()或remove()使用的是System.arraycopy()
    * 实质:copyOf()底层还是由arraycopy()实现
    * [copyOf()使用示例](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/ArraycopyAndCopyOf/ArraycopyTest.java)
    * [arraycopy()使用示例](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/ArraycopyAndCopyOf/ArrayscopyOfTest.java)
## Vector(线程安全)
* 出构造发方法以外的所有方法都是synchronized同步的
* 区别于ArrayList： ArrayList不是同步的,所以在不需要保证线程安全时时建议使用ArrayList

## LinkedList(非线程安全)
* 初始大小万为0
* 实现了 List接口,Deque接口的双端链表

## HashMap(非线程安全)

![结构视图](https://camo.githubusercontent.com/eec1c575aa5ff57906dd9c9130ec7a82e212c96a/68747470733a2f2f757365722d676f6c642d63646e2e786974752e696f2f323031382f332f32302f313632343064626363333033643837323f773d33343826683d34323726663d706e6726733d3130393931)

* 默认初始table使用的是Map.Entry<K,V>[]实现**链表结构**进行存储
* 转换临界值:TREEIFY_THRESHOLD = 8;**链表长度 >= 8时进行转换**,转换成**红黑树**结构
* table**默认初始容量为16**;DEFAULT_INITIAL_CAPACITY = 1 << 4
* table**最大容量为2的30次方**;MAXIMUM_CAPACITY = 1 << 30
* table默认的负载因子DEFAULT_LOAD_FACTOR = 0.75,**只有元素总量达到总容量的75%才会进行扩容**
* 扩容**每次扩容为原有容量的2倍,保证了容量n为2的x次方**
### put和get方法
* put(key,value)方法:
    * 如果定位到的数组位置没有元素 就直接插入.
    * 如果定位到的数组位置有元素就和要插入的key比较
    * 如果key相同就直接覆盖
    * 如果key不相同,就判断p是否是一个树节点
    * 如果是就调用e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value)将元素添加进入
    * 如果不是就遍历链表插入。
* get(key)方法:
    * **根据key的hashCode计算对应的hash**(int类型)
    * **然后通过(n - 1) & hash 得到元素存放的位置**
    * 调用getNode(hash,key):
        * 先根据hash的模运算得到索引再比较key
        * 满足(k = e.key) == key || (key != null && key.equals(k)),即当前节点key内存地址相等且key对象内容相等
        * 返回对应节点
    * 返回节点的value
### HashMap引出的求余%和与运算&转换问题
* 当n = 2的x次幂时,满足转换条件,**(n - 1) & hash 等价于 hash % n**
* [参考博客](https://www.cnblogs.com/ysocean/p/9054804.html)

## Hashtable(线程安全)
* 线程安全,大多数方法采用了synchronized进行同步处理
* 设置默认的**初始容量为11,负载因子为0.75**
* ![图示](https://camo.githubusercontent.com/b8e66016373bb109e923205857aeee9689baac9e/687474703a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f31382d382d32322f35303635363638312e6a7067)

## LinkedHashMap(非线程安全)
* 继承HashMap
* 底层是双向链表

## HashSet(非线程安全)
* 底层使用的是HashMap

## ConcurrentHashMap(线程安全)
* 底层使用的 分段数组 + 链表/红黑二叉树
* 1.7时采用**分段锁(Segment)将整个数组分隔为多段数组**
* 1.7时结构: ![1.7时](https://camo.githubusercontent.com/443af05b6be6ed09e50c78a1dca39bf75acb106d/687474703a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f31382d382d32322f33333132303438382e6a7067)
* 1.8时采用**synchronized 和 CAS来操作**
    * synchronized只锁定当前链表或红黑二叉树的首节点,这样只要hash不冲突,就不会产生并发空,效率又提升N倍。
* 1.8时结构: ![1.8时](https://camo.githubusercontent.com/2d779bf515db75b5bf364c4f23c31268330a865e/687474703a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f31382d382d32322f39373733393232302e6a7067)

## ThreadLocal
* 一般叫做线程本地变量
* 实际使用ThreadLocalMap进行保存
* 初始的ThreadLocalMap中的table容量为16
* table扩容临界值为当前容量的2/3,只有满足当前条件才会进行扩容
* 每次扩容为原有容量的2倍

### 主要方法 
* public T get()
    * 获取当前线程
    * 得到对应的ThreadLocalMap
    * 然后接着下面获取到<key,value>键值对,**传入this**,不是线程t
    * 如果存在键值对,返回value
    * 不存在ThreadLocalMap 或 不存在对应的键值对 则调用setInitialValue()
        * 如果是ThreadLocalMap不为空,直接添加键值对
        * ThreadLocalMap为空,创建map并添加
        * 方法最后返回对应的初始值initialValue()中的value    
* public void set()
    * 获取当前线程t
    * 调用getMap(t)获取ThreadLocalMap
    * 不为空直接添加
    * 为空创建map并添加
* public void remove()
    * 获取当前线程
    * 获取当前线程的ThreadLocalMap
    * 存在则**传入this**,进行移除对应的键值对
* protected T initialValue()
    * 默认初始的ThreadLocal返回的value为null,一般会对该方法进行重写
* [ThreadLocalDemo示例](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/ThreadLocal/ThreadLocalDemo.java)
### 关于为什么ThreadLocal中的Entry申明为弱引用?
[参考博客](https://www.cnblogs.com/waterystone/p/6612202.html)
### 相关问题
* 为什使用弱引用?
> 当ThreadLocal = null后,还存存在Thread中的ThreadLocalMap中的Entry对添加的obj的引用,
如果Entry不使用弱引用将只有等到整个线程运行完后才能进行GC回收,
而这里的Entry所存储的obj我们已经不能从ThreadLocalMap中取出使用,
所以这里的Entry已经可以进行回收,只有使用弱引用才能被垃圾回收器回收.

## synchronized 和 ReentrantLock
### ReentrantLock
* 继承接口Lock
* 成员变量 Sync sync
* 内部抽象类Sync继承AbstractQueuedSynchronizer
* 存在两个实现类NonfairSync和FairSync继承Sync
* lock()方法:
    * 通过回调内部的Sync的lock实现方法,即NonfairSync或FairSync的lock
        * 公平锁(Fair):
        >加锁前检查是否有排队等待的线程，优先排队等待的线程，先来先得 
        * 非公平锁(Nonfair):
        > 加锁时不考虑排队等待问题，直接尝试获取锁，获取不到自动到队尾等待
    * 默认使用的是非公平锁(Nonfair):
    > 在获取时先cas改变一下 AQS 的state值, 改变成功就获取, 不然就加入到  AQS 的 Sync Queue 里面
    
### synchronized和ReentrantLock异同
* 相同:
    * 都实现了多线程同步和内存可见性语义
    * 都是可重入锁
* 不同点:
    * 实现机制不同:
        * synchronized通过java对象头锁标记和Monitor对象实现 
        * reentrantlock通过CAS、AQS（AbstractQueuedSynchronizer）和locksupport（用于阻塞和解除阻塞）实现 
        * synchronized依赖jvm内存模型保证包含共享变量的多线程内存可见性 
        * reentrantlock通过ASQ的volatile state保证包含共享变量的多线程内存可见性
    * 使用方式不同:
        * ynchronized可以修饰实例方法（锁住实例对象）、静态方法（锁住类对象）、代码块（显示指定锁对象）
        * reentrantlock显示调用trylock()/lock()方法，需要在finally块中释放锁
    * 功能丰富程度不同:
        * reentrantlock提供有限时间等候锁（设置过期时间）、可中断锁（lockInterruptibly）、condition（提供await、signal等方法）等丰富语义 
        * reentrantlock提供公平锁和非公平锁实现
        * synchronized不可设置等待时间、不可被中断（interrupted）

## IO流
* 字节流
* 字符流:用来操作字符的
* Input流:读数据
* Output流:写数据
* 输入输出是相对于本机内存的
* java代码中的汉字是使用的Unicode进行编码实现的(Unicode兼容了ASCII)
* char类型长度是两个字节,能够表示中文,因为Unicode字符集中中文对应的数字,用两个字节就足够表示了
* utf-8编码,使用的就是unicode字符集
    * utf-8是用一个运算规则,把unicode字符集的数字转换成计算机能识别的编码
    * utf-8标识的汉字,占用3个字节 
    * 汉字转换成utf-8之后格式: 1110 XXXX | 10XX XXXX | 10XX XXXX
    * **了解utf-8是如何将unicode字符集进行转换的**
## 序列化
* 序列化将对象的状态信息装换成可以存储或传输的形式的**过程**
* 计算机中保存的所有数据都是二进制数据
* 总结: 序列化就是把计算机中的各种各样的格式(文本,图片,音频,视频)变成数字的过程
* 变成数字后,用IO流读写
    * 字节流
* 反序列化: 就是把二进制数据还原成各种各样的数据格式的过程
* [测试demo](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/SerializationTest/)
