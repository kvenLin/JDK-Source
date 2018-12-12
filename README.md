# JDK-Source
jdk源码学习
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