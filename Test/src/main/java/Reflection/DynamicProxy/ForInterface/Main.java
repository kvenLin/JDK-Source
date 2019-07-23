package Reflection.DynamicProxy.ForInterface;

import java.lang.reflect.Proxy;

/**
 * @Author: clf
 * @Date: 19-3-3
 * @Description:
 * 实现接口的动态代理3种方式
 */
public class Main {
    public static void main(String[] args) {

        //1.利用invokeHandler中定义的绑定方法实现动态代理
        IPlay iPlay = (IPlay) PlayProxy.bind(IPlay.class);
        iPlay.play();
        //2.创建代理时添加绑定关系
        PlayProxy playProxy1 = new PlayProxy();
        IPlay iPlay1 = (IPlay) Proxy.newProxyInstance(IPlay.class.getClassLoader(), new Class[]{IPlay.class}, playProxy1);
        iPlay1.play();
        //3.利用构造函数实现绑定关系
        PlayProxy playProxy2 = new PlayProxy(IPlay.class);
        IPlay iPlay2 = (IPlay) Proxy.newProxyInstance(IPlay.class.getClassLoader(), new Class[]{IPlay.class}, playProxy2);
        iPlay2.play();
    }
    /**
     * 运行结果：
     *
     * test PlayProxy invoke
     * this is what you can do in method invoke
     * test PlayProxy invoke
     * this is what you can do in method invoke
     * test PlayProxy invoke
     * this is what you can do in method invoke
     */
}
