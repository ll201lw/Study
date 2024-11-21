package com.bgy.design_pattern.singleton.singleton;

public class Singleton1 {

    /**
     * 饿汉式
     * 类加载到内存后,就实例化一个单例,JVM保证线程安全
     * 简单实用,推荐实用
     * 唯一缺点:不管用到与否,类装载时就完成实例化
     */

    private static final Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static final Singleton1 getInstance() {
        return instance;
    }

}
