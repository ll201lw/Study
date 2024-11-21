package com.bgy.design_pattern.singleton.singleton;

public class Singleton7 {

    /**
     * 静态内部类
     * JVM保证单例
     * 加载外部类时不会加载内部类,这样可以实现懒加载
     */


    private Singleton7() {
    }

    private static class SingletonHolder {
        private static final Singleton7 instance = new Singleton7();
    }

    public static Singleton7 getInstance() {
        //调用时,JVM保证只加载一次SingletonHolder,那么同时也保证了只加载一次instance的实例,所以达到了线程安全的效果
        return SingletonHolder.instance;
    }

}
