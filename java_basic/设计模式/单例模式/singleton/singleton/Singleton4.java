package com.bgy.design_pattern.singleton.singleton;

public class Singleton4 {

    /**
     * lazy loading
     * 懒汉式
     * 虽然达到了按需初始化的目的,但却带来线程不安全的问题
     * 可以通过synchronized解决,但也带来了效率下降
     */

    private static Singleton4 instance;

    private Singleton4() {
    }

    public static synchronized Singleton4 getInstance() {
        if (null == instance) {
            instance = new Singleton4();
        }
        return instance;
    }

}
