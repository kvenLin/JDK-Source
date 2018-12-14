package com.StackTest;

/**
 * @Author: clf
 * @Date: 18-12-14
 * @Description:
 * 栈对象逃逸分析
 */
public class StackAllocation {
    public StackAllocation obj;

    //方法返回StackAllocation对象,发生的逃逸
    public StackAllocation getInstance(){
        return obj == null ? new StackAllocation() : obj;
    }


    //为成员变量赋值,发生逃逸
    public void setObj(){
        this.obj = new StackAllocation();
    }


    //对象的作用域仅在当前方法中有效,没有发生逃逸
    public void useAStackAllocation(){
        StackAllocation s = new StackAllocation();
    }

    //引用成员变量的值,发生逃逸
    public void useStackAllocation2(){
        StackAllocation s = getInstance();
    }
}
