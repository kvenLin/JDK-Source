package Reflection.DynamicProxy.EasySample.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: clf
 * @Date: 19-3-3
 * @Description:
 * 动态代理的handler实现invoke方法
 */
public class WorkHandler implements InvocationHandler {
    private Object object;

    public WorkHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("=================== before ==================");
        Object result = method.invoke(object, objects);
        System.out.println("=================== after ==================");
        return result;
    }

    public Object bind() {
        Class cls = object.getClass();
        return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), this);
    }
}
