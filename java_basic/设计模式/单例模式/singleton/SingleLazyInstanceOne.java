package com.bgy.design_pattern.singleton;

public class SingleLazyInstanceOne {

    //懒汉模式 线程安全 双重校验
    private static volatile SingleLazyInstanceOne instance;

    private SingleLazyInstanceOne() {
    }


    public static SingleLazyInstanceOne getInstance() {
        if (null == instance) {
            synchronized (SingleLazyInstanceOne.class) {
                if (null == instance) {
                    instance = new SingleLazyInstanceOne();
                }
            }
        }
        return instance;
    }
}
