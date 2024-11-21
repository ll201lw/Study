package com.bgy.design_pattern.singleton.singleton;


/**
 * 枚举类
 * 不仅可以解决线程同步,还可以防止反序列化
 * 防止反序列化原因:枚举类没有构造方法
 */
public enum Singleton8 {
    instance;

}
