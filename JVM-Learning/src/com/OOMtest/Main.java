package com.OOMtest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: clf
 * @Date: 18-12-13
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        List<Demo> demoList = new ArrayList<>();
        while (true){
            //进行无限循环添加对象Demo
            demoList.add(new Demo());
        }
    }
}
