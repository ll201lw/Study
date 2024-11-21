package com.bgy.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.bgy.application.TestActivity;
import com.bgy.application.TestHookActivity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class HookHelper {


    public static void hookAmsAidl() {
        hookIActivityTaskManager();
    }


    //修改启动模式
    public static void hookAms() {
        try {

            Field singletonField;
            Class<?> iActivityManager;
            // 1，获取Instrumentation中调用startActivity(,intent,)方法的对象
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // 10.0以上是ActivityTaskManager中的IActivityTaskManagerSingleton
                Class<?> activityTaskManagerClass = Class.forName("android.app.ActivityTaskManager");
                singletonField = activityTaskManagerClass.getDeclaredField("IActivityTaskManagerSingleton");
                iActivityManager = Class.forName("android.app.IActivityTaskManager");
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 8.0,9.0在ActivityManager类中IActivityManagerSingleton
                Class activityManagerClass = ActivityManager.class;
                singletonField = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
                iActivityManager = Class.forName("android.app.IActivityManager");
            } else {
                // 8.0以下在ActivityManagerNative类中 gDefault
                Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
                singletonField = activityManagerNative.getDeclaredField("gDefault");
                iActivityManager = Class.forName("android.app.IActivityManager");
            }
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);

            // 2，获取Singleton中的mInstance，也就是要代理的对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            Method getMethod = singletonClass.getDeclaredMethod("get");
            Object mInstance = getMethod.invoke(singleton);
            if (mInstance == null) {
                return;
            }

            //开始动态代理
            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManager},
                    new AmsHookBinderInvocationHandler(mInstance));

            //现在替换掉这个对象
            mInstanceField.set(singleton, proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //动态代理执行类
    public static class AmsHookBinderInvocationHandler implements InvocationHandler {

        private Object obj;

        public AmsHookBinderInvocationHandler(Object rawIActivityManager) {
            obj = rawIActivityManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if ("startActivity".equals(method.getName())) {

                Intent raw;
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }

                //原始意图
                raw = (Intent) args[index];

                //设置新的Intent-直接制定LoginActivity
                Intent newIntent = new Intent();
                String targetPackage = "com.bgy.application";
                ComponentName componentName = new ComponentName(targetPackage, TestHookActivity.class.getName());
                newIntent.setComponent(componentName);
                args[index] = newIntent;
                return method.invoke(obj, args);

            }

            //如果不是拦截的startActivity方法，就直接放行
            return method.invoke(obj, args);
        }

    }


    private static void hookIActivityTaskManager() {
        try {
            Field singleTonField = null;
            Class<?> iActivityManager;
//            Class<?> activityTaskManagerClass = Class.forName("android.app.ActivityTaskManager");
//            singleTonField = activityTaskManagerClass.getDeclaredField("IActivityTaskManagerSingleton");
//            iActivityManager = Class.forName("android.app.IActivityTaskManager");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // 10.0以上是ActivityTaskManager中的IActivityTaskManagerSingleton
                Class<?> activityTaskManagerClass = Class.forName("android.app.ActivityTaskManager");
                singleTonField = activityTaskManagerClass.getDeclaredField("IActivityTaskManagerSingleton");
                iActivityManager = Class.forName("android.app.IActivityTaskManager");
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 8.0,9.0在ActivityManager类中IActivityManagerSingleton
                Class activityManagerClass = ActivityManager.class;
                singleTonField = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
                iActivityManager = Class.forName("android.app.IActivityManager");
            } else {
                // 8.0以下在ActivityManagerNative类中 gDefault
                Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
                singleTonField = activityManagerNative.getDeclaredField("gDefault");
                iActivityManager = Class.forName("android.app.IActivityManager");
            }


            singleTonField.setAccessible(true);
            Object singleton = singleTonField.get(null);

            //获取ActivityManager对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            //原始的IActivityTaskManager
            final Object IActivityTaskManager = mInstanceField.get(singleton);
            //创建一个IActivityManager对象
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManager},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Intent raw = null;
                            int index = -1;
                            if ("startActivity".equals(method.getName())) {
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        raw = (Intent) args[i];
                                        index = i;
                                        Log.i("hook_manager", "raw:" + raw);
                                        if (null != raw) {
                                            if (raw.getComponent().getClassName().contains("TestActivity")) {
                                                Intent intent = new Intent();
                                                intent.setComponent(new ComponentName("com.bgy.application", TestHookActivity.class.getName()));
                                                args[index] = intent;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            return method.invoke(IActivityTaskManager, args);
                        }
                    });
            mInstanceField.set(singleton, proxy);
        } catch (Exception e) {
            Log.i("hook_manager", "Exception:" + e.getMessage());
        }
    }

    private static void hookIActivityManager() {
        try {
            Field singleTonField = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> activityManager = Class.forName("android.app.ActivityTaskManager");
                singleTonField = activityManager.getField("IActivityTaskManagerSingleTon");
            } else {
                Class<?> activityManager = Class.forName("android.app.ActivityManagerNative");
                singleTonField = activityManager.getField("gDefault");
            }
            singleTonField.setAccessible(true);
            Object singleton = singleTonField.get(null);

            //获取ActivityManager对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            //原始的IActivityTaskManager
            final Object IActivityTaskManager = mInstanceField.get(singleton);
            //创建一个IActivityManager对象
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{Class.forName("android.app.IActivityManager")},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Intent raw = null;
                            int index = -1;
                            if ("startActivity".equals(method.getName())) {
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        raw = (Intent) args[i];
                                        index = i;
                                        break;
                                    }
                                }
                                Log.i("hook_manager", "raw:" + raw);
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.bgy.application", TestActivity.class.getName()));
//                                intent.putExtra(EXTRA_TARGET_INTENT, raw);
                                args[index] = intent;
                            }
                            return method.invoke(IActivityTaskManager, args);
                        }
                    });
            mInstanceField.set(singleton, proxy);
        } catch (Exception e) {

        }
    }

}
