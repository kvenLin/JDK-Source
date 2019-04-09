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