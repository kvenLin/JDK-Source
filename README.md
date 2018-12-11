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
## HashMap
* 默认初始table使用的是Map.Entry<K,V>实现**链表结构**进行存储
* 转换临界值:TREEIFY_THRESHOLD = 8;**链表长度 >= 8时进行转换**
* table默认初始容量为16;DEFAULT_INITIAL_CAPACITY = 1 << 4
* table最大容量为2的30次方;MAXIMUM_CAPACITY = 1 << 30
* table默认的负载因子DEFAULT_LOAD_FACTOR = 0.75,只有元素总量达到总容量的75%才会进行扩容
* 扩容每次扩容为原有容量的2倍,**保证了容量n为2的x次方**
### HashMap引出的求余%和与运算&转换问题
* 当n = 2的x次幂时,满足转换条件,**(n - 1) & hash 等价于 hash % n**
* [参考博客](https://www.cnblogs.com/ysocean/p/9054804.html)