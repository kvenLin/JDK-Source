package Reflection.DynamicProxy.ForInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: clf
 * @Date: 19-3-3
 * @Description:
 * 实现接口动态代理的handler
 *
 * 此类用三种方法将接口直接绑定到实现的代理类，
 * 即构造方法将接口传入，空的构造函数，重新定义一个专门实现绑定的静态方法。
 * 实质上三者的绑定也是殊途同归，终究还是和最上面基本实现动态代理的过程相同。
 */
public class PlayProxy implements InvocationHandler {
    private Class interfaceClass;

    public PlayProxy(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public PlayProxy() {
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("test PlayProxy invoke");
        //do what you want to do
        System.out.println("this is what you can do in method invoke");
        return null;
    }

    public static Object bind(Class interfaceClass){
        PlayProxy playProxy = new PlayProxy();
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, playProxy);
    }
}
