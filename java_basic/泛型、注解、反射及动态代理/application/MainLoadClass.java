package com.bgy.application;

public class MainLoadClass {


    // Used to load the 'application' library on application startup.
    static {
        System.loadLibrary("math");
    }


    //test
    public native float mul(float a, float b);

    public native float div(float a, float b);

    public native float add(float a, float b);

    public native float sub(float a, float b);

}
