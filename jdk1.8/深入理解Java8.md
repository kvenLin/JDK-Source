# 深入理解Java8
## lambda
>lambda就是用于表示匿名函数或者是闭包这样的一种运算符

lambada表达式的基本结构: 
```
(param1, param2, param3) -> {
    //执行体
}
```
[使用示例](../Test/src/JavaSE/lambda/Test1.java)
### 关于函数式接口
1. 如果一个结口只有一个抽象方法, 那么该接口就是函数式接口
2. 如果我们在某个接口上声明了 FunctionInterface 注解, 那么编译器就会按照函数式接口的定义来要求该接口.
3. 如果某个接口只有一个抽象方法, 但我们并没有对该接口声明FunctionInterface注解, 那么编译器依旧会将该接口看作函数式接口.
