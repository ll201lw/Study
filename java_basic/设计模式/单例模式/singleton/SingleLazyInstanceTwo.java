package com.bgy.design_pattern.singleton;

public class SingleLazyInstanceTwo {


    private SingleLazyInstanceTwo() {
    }

    public static final SingleLazyInstanceTwo getInstance() {
        return SingleTonHolder.instance;
    }

    //内部静态类
    private static class SingleTonHolder {
        public static final SingleLazyInstanceTwo instance = new SingleLazyInstanceTwo();
    }
}
