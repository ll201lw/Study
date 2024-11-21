package com.bgy.design_pattern.singleton.singleton;

public class Singleton3 {

    /**
     * lazy loading
     * 懒汉式
     * 虽然达到了按需初始化的目的,但却带来线程不安全的问题
     */

    private static Singleton3 instance;

    private Singleton3() {
    }

    public static  Singleton3 getInstance() {
        if (null == instance){
            instance = new Singleton3();
        }
        return instance;
    }

}
