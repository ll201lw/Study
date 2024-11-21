package com.bgy.utils;

import android.content.Context;
import com.bgy.anotation.BindView;
import com.bgy.anotation.LayoutResource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ResourceManager {


    public static void init(Context context) {
        setLayoutResource(context);
        bindView(context);
        onClick(context);
    }

    /**
     * 执行点击事件
     * @param context
     */
    private static void onClick(Context context) {
        Class clazz = context.getClass();

    }

    /** 加载布局文件
     * @param context
     */
    private static void setLayoutResource(Context context) {
        Class clazz = context.getClass();
        Annotation annotation = clazz.getAnnotation(LayoutResource.class);
        if (null == annotation) {
            return;
        }
        int value = ((LayoutResource) annotation).value();
        try {
            Method method = clazz.getMethod("setContentView", int.class);
            method.setAccessible(true);
            method.invoke(context, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    /**根据注解查找View并实例化
     * @param context
     */
    private static void bindView(Context context) {
        Class clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (null == fields) {
            return;
        }
        try {
            Method findViewById = clazz.getMethod("findViewById", int.class);
            if (null == findViewById) {
                return;
            }
            for (Field field : fields) {
                BindView annotation = field.getAnnotation(BindView.class);
                if (null == annotation) {
                    continue;
                }
                int id = annotation.value();
                Object invoke = findViewById.invoke(context, id);
                field.setAccessible(true);
                field.set(context, invoke);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
