package com.bgy.design_pattern.singleton.singleton;

public class Singleton6 {

    /**
     * lazy loading
     * 懒汉式
     * 双重校验
     * 避免线程安全问题
     * volatile:volatile是Java中的关键字，用来修饰会被不同线程访问和修改的变量。
     * java内存模型的3个特点:可见性,原子性,有序性
     * 而volatile是Java虚拟机提供的轻量级同步机制,
     * volatile:保证可见性,不保证原子性,禁止指令重排（保证有序性）
     * 特点验证链接:https://www.cnblogs.com/zhongqifeng/p/14684028.html
     * <p>
     * <p>
     * volatile 可以保证线程可见性且提供了一定的有序性，但是无法保证原子性。在 JVM 底层是基于内存屏障实现的。
     * <p>
     * 1.当对非 volatile 变量进行读写的时候，每个线程先从内存拷贝变量到 CPU 缓存中。如果计算机有多个CPU，每个线程可能在不同的 CPU 上被处理，这意味着每个线程可以拷贝到不同的 CPU cache 中。
     * 2.而声明变量是 volatile 的，JVM 保证了每次读变量都从内存中读，跳过 CPU cache 这一步，所以就不会有可见性问题。
     * ①对 volatile 变量进行写操作时，会在写操作后加一条 store 屏障指令，将工作内存中的共享变量刷新回主内存；
     * ②对 volatile 变量进行读操作时，会在写操作后加一条 load 屏障指令，从主内存中读取共享变量；
     */

    private static volatile Singleton6 instance;

    private Singleton6() {
    }

    public static Singleton6 getInstance() {
        if (null == instance) {
            synchronized (Singleton6.class) {
                if (null == instance) {
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }

}
