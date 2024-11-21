package com.bgy.design_pattern.singleton.singleton;

public class Singleton2 {

    /**
     * 饿汉式
     * 类加载到内存后,就实例化一个单例,JVM保证线程安全
     * 简单实用,推荐实用
     * 唯一缺点:不管用到与否,类装载时就完成实例化
     */

    private static final Singleton2 instance;

    static {
        instance = new Singleton2();
    }

    private Singleton2() {
    }

    public static final Singleton2 getInstance() {
        return instance;
    }

}
