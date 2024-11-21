package com.bgy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;


public class StringUtil {


    public static byte[] int2Bytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((value >> 24) & 0xFF);
        bytes[1] = (byte) ((value >> 16) & 0xFF);
        bytes[2] = (byte) ((value >> 8) & 0xFF);
        bytes[3] = (byte) (value & 0xFF);
        return bytes;
    }


    public static String bytes2BitsString(byte[] bytes) {
        StringBuilder all = new StringBuilder();
        for (byte b : bytes) {
            all.append(byte2BitsString(b));
        }
        return all.toString();
    }

    public static String byte2BitsString(byte b) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            stringBuilder.append(toUnsignedInt(b) >> i & 1);
        }
        return stringBuilder.toString();
    }

    private static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }

    public static String getLocaleStringResource(Context context, int resourceId) {
        if (null == context) {
            return "";
        }
        String result;
        Resources resources = context.getResources();
        result = resources.getString(resourceId);
        return result;
    }

    public static String getString(Context context,int resourceId) {
        if (null == context) {
            return "";
        }
        String result;
        Resources resources = context.getResources();
        if (null == resources){
            return "";
        }
        result = resources.getString(resourceId);
        return result;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return packageInfo;
    }

}
