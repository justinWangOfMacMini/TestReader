package com.uplinetek.testviewer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by wangshengjun on 2017/6/24.
 */

public class AppUtils {

    public static String getAppVersionName(Context context){
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    public static void setStatusBarTransparent(Activity context, int tintColor){
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setStatusBarColor(tintColor);
        }else{
            tintManager.setTintColor(tintColor);
        }
    }

}
