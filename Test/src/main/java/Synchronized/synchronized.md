# 浅析synchronized底层实现原理

## 使用场景
* 修饰实例方法
* 修饰静态方法
* 修饰静态代码块,需要指定加锁对象

## 条件准备
* 简单编写一个使用synchronized修饰静态代码块的方法[Test.java](https://github.com/kvenLin/JDK-Source/blob/master/Test/Synchronized/Test.java)
* 使用javac Test.java 对当Test.java进行编译
* 然后使用javap -c Test > Test.txt 对class文件进行反编译并将输出结果保存到[Test.txt](https://github.com/kvenLin/JDK-Source/blob/master/Test/src/Synchronized/Test.txt)文件中

## 简要分析
在Test.txt文件中可以看到两个关键指令: monitorenter和monitorexit
* [java8文档传送门](http://docs.oracle.com/javase/8/docs/)
### monitor监视器准入指令
每个对象有一个监视器(monitor).当monitor被占用时就会处于锁定状态,线程尝试执行monitorenter指令尝试获取monitor的所有权限:
* 如果monitor的进入数为0,则该线程进入monitor,然后将数值设为1,该线程即为monitor的所有者
* 如果线程已经占有该monitor,只是重新进入,则进入monitor的进入数加1
* 如果其他线程已经占用了monitor,则该线程进入阻塞状态,直到monitor的进入数为0,再重新尝试获取monitor的所有权

### monitor监视器释放指令
* 执行monitorexit的线程必须是objectref所对应的monitor的所有者
* 指令执行时,monitor的进入数减1,如果减1后进入数为0,那线程退出monitor,不再是这个monitor的所有者.其他被这个monitor阻塞的线程可以尝试去获取这个 monitor 的所有权.

### 原理
* 当修饰的是静态方法获取的是Class对象的monitor对象
* 当修饰的是实例方法获取的是该对象实例对应的monitor对象
* 当修饰的是代码块需要自己指定
> 例如： synchronized(Test.class){...}，获取的是Test的Class对象的monitor