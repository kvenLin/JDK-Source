# JDK-Source
jdk源码学习
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
    
## ArrayList
* 构造函数
    * 无参: 底层默认创建一个空数组,在进行第一次添加操作时进行扩容为默认的**容量10**
    * 传入int n: 创建一个容h量为n的数组
    * 传入集合: 使用Arrays.copyOf()进行遍历赋值到当前ArrayList中
* 底层数组设定的最大长度为**MAX_ARRAY_SIZE** = Integer.MAX_VALUE - 8
* 只有在minCapacity > MAX_ARRAY_SIZE时会扩容到Integer.MAX_VALUE
* 核心扩容方法 grow(int minCapacity):
    * 传入minCapacity,最小所需容量的空间
    * 实现: `newCapacity = oldCapacity + (oldCapacity >> 1);`
    * 使用的移位操作,扩容为之前的1.5倍容量
* copyOf()和arraycopy()
    * toString方法底层使用Arrays.copyOf()
    * add()或remove()使用的是System.arraycopy()
    * 实质:copyOf()底层还是由arraycopy()实现
    * [copyOf()使用示例](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/ArraycopyAndCopyOf/ArraycopyTest.java)
    * [arraycopy()使用示例](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/ArraycopyAndCopyOf/ArrayscopyOfTest.java)
## HashMap

![结构视图](https://camo.githubusercontent.com/eec1c575aa5ff57906dd9c9130ec7a82e212c96a/68747470733a2f2f757365722d676f6c642d63646e2e786974752e696f2f323031382f332f32302f313632343064626363333033643837323f773d33343826683d34323726663d706e6726733d3130393931)

* 默认初始table使用的是Map.Entry<K,V>[]实现**链表结构**进行存储
* 转换临界值:TREEIFY_THRESHOLD = 8;**链表长度 >= 8时进行转换**,转换成红黑树结构
* table默认初始容量为16;DEFAULT_INITIAL_CAPACITY = 1 << 4
* table最大容量为2的30次方;MAXIMUM_CAPACITY = 1 << 30
* table默认的负载因子DEFAULT_LOAD_FACTOR = 0.75,只有元素总量达到总容量的75%才会进行扩容
* 扩容每次扩容为原有容量的2倍,**保证了容量n为2的x次方**
### put和get方法
* put(key,value)方法:
    * 如果定位到的数组位置没有元素 就直接插入.
    * 如果定位到的数组位置有元素就和要插入的key比较
    * 如果key相同就直接覆盖
    * 如果key不相同,就判断p是否是一个树节点
    * 如果是就调用e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value)将元素添加进入
    * 如果不是就遍历链表插入。
* get(key)方法:
    * 根据key的hashCode计算对应的hash(int类型)
    * 调用getNode(hash,key):
        * 先根据hash的模运算得到索引再比较key
        * 满足(k = e.key) == key || (key != null && key.equals(k)),即当前节点key内存地址相等且key对象内容相等
        * 返回对应节点
    * 返回节点的value
### HashMap引出的求余%和与运算&转换问题
* 当n = 2的x次幂时,满足转换条件,**(n - 1) & hash 等价于 hash % n**
* [参考博客](https://www.cnblogs.com/ysocean/p/9054804.html)

## LinkedHashMap
* 继承HashMap
* 底层是双向链表
## ThreadLocal
* 一般叫做线程本地本地变量
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
如果Entry不使用弱引用将只有等到整个线程运行完后才能进行GC回收,而这里的Entry所存储的obj我们已经不能从ThreadLocalMap中取出使用,
所以这里的Entry已经可以进行回收,只有使用弱引用才能被垃圾回收器回收.

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
    * **了解uhf-8是如何将unicode字符集进行转换的**