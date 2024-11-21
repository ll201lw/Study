package com.bgy.design_pattern.singleton;

public class SingleInstance {


    private static final SingleInstance instance = new SingleInstance();

    private SingleInstance() {
    }
    //饿汉模式
    public static SingleInstance getInstance() {
        return instance;
    }

}
