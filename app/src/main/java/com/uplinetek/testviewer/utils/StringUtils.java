package com.uplinetek.testviewer.utils;

/**
 * Created by Justin on 2017/6/23.
 */

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (null == str) {
            return true;
        } else if ("".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean notempty(String str) {
        return (str != null && !str.equals("") && !str.equals("null") && !str.equals(" ") && !str.equals("  "));
    }

}
