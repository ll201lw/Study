package com.bgy.design_pattern.singleton;

public class SingleLazyInstance {

    private static SingleLazyInstance instance;

    private SingleLazyInstance(){}

    //懒汉模式 线程安全
    public static synchronized  SingleLazyInstance getInstance() {
        if (null == instance){
            instance = new SingleLazyInstance();
        }
        return instance;
    }
}
