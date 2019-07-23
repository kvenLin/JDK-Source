package Reflection.DynamicProxy.EasySample;

/**
 * @Author: clf
 * @Date: 19-3-3
 * @Description:
 */
public class WorkImpl implements Work{
    @Override
    public void say() {
        System.out.println("hello world！");
    }
}
