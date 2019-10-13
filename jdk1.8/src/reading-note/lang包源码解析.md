# java.lang包下源码解析
## Object
[Object源码](../java/lang/Object.java)
* public final native Class<?> getClass();//获取Class对象
* public native int hashCode();//根据本地方法获取hashCode，依靠对象的地址和字段属性进行获取。一般两个对象==，则其hashcode一定相等；反之不成立
* public boolean equals(Object obj) { return (this == obj); }//Object内部equals比较的内存地址，如果对象不重写该方法，则只有是同一个对象时才会返回true
## String
[String源码](../java/lang/String.java)
### 成员变量
* private final char value[];//默认是final修饰的char数组
* private int hash;//缓存字符串的hashCode,默认值为0

================================================================

* private static final long serialVersionUID = -6849794470754667710L;
* private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
> 因为String实现了Serializable接口，所以支持序列化和反序列化支持。Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，
如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常(InvalidCastException)。

### 主要构造函数
* public String(){ this.value = new char[0]; }//默认创建一个长度为0的char数组
* public String(String original): 对字符数组和hash值进行赋值
* public String(char value[]): 使用Arrays.copyOf()方式拷贝传入的value数组到属性的value数组
    * 面试题: 这里为什么采用拷贝的方式?而不是直接赋值?
    > String内部的value想要达到的目的是想通过final修后不能再进行修改,
    如果直接赋值,则指向的是同一块地址,这里便可以修改外部的value的值从而更改了内部的value;
    为了避免上述的情况所以采用了拷贝的方式.
