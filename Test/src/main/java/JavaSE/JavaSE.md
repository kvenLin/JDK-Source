# Java基础

## 关键字总结
### static关键字
* 执行顺序: 静态代码块 ---> 非静态代码块 ---> 构造方法
* **注意:静态代码块只有第一次new操作才会执行,之后不会再执行;而非静态代码块每一次new都会执行一次**
* [Static关键字Demo示例](StaticDemo.java)
## Java8新特性
### lambda表达式
1. (params) -> expression
2. (params) -> statement
3. (params) -> {statement}

## Exception和Error
![Exception和Error结构](../image/Exception和Error结构.png)
* Throwable:
    * 是Java语言中所有错误或异常的超类
    * 两个子类分别为: Error 和 Exception
    * 包含了其线程创建时线程执行堆栈的快照,提供了printStackTrace()等接口用于获取堆栈跟踪数据等信息
* Exception: 
    * 是Throwable的一种表现形式,指出了合理的应用程序想要捕获的条件
    * RuntimeException: 即可能在Java虚拟机正常运行期间抛出的异常类,例如除数为0时,抛出ArithmeticException异常;编译器不会检查RuntimeException
* Error:
    * 也是Throwable的子类,用于指示合理的应用程序不应该试图捕获的严重问题,和RuntimeException一样,编译器不会检查Error
    * 当资源不足、约束失败、或者是其它程序无法继续运行的条件发生时,就产生错误.程序本身是无法修复这些错误的.
* OOM(OutOfMemoryError): 除了程序计数器外,虚拟机内存的其他几个运行时区域都有发生OOM异常的可能
* StackOverflowError: 
    * 如果线程请求的栈深度大于虚拟机所允许的最大深度,将抛出StackOverflowError异常
    * 注意: 如果虚拟机在扩展栈时无法申请到足够的内存空间,则抛出OOM异常