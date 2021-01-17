package Reflection.DynamicProxy.EasySample;

import Reflection.DynamicProxy.EasySample.handler.WorkHandler;

import java.lang.reflect.Proxy;

/**
 * @Author: clf
 * @Date: 19-3-3
 * @Description:
 * 动态代理简单示例
 */
public class Main {
    public static void main(String[] args) {
        Work work = new WorkImpl();
        //创建代理需要的handler
        WorkHandler workHandler = new WorkHandler(work);
        //获取代理的对象
//        Work proxy = (Work) Proxy.newProxyInstance(work.getClass().getClassLoader(), work.getClass().getInterfaces(), workHandler);
        Work proxy = (Work) workHandler.bind();
        proxy.say();
    }
    /**
     * 运行结果： 相对于原有的say()方法输出 （hello world！）而言是经过包装的，这就是代理的效果
     * =================== before ==================
     * hello world！
     * =================== after ==================
     */
}
