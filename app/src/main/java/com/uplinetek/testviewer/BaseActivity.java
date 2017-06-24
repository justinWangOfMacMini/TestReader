package com.uplinetek.testviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

import com.uplinetek.testviewer.utils.AppUtils;

/**
 * Created by wangshengjun on 2017/6/24.
 */

public class BaseActivity extends Activity {

    protected ProgressDialog dialog = null;
    private boolean isActivityVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppUtils.setStatusBarTransparent(this, getResources().getColor(R.color.title_red));
    }

    public void showProgressDialog(int stringId){
        if(isActivityVisible){
            if(null == dialog){
                dialog = new ProgressDialog(this);
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.setMessage(getString(stringId));
            dialog.show();
        }
    }

    public void showProgressDialogs(){
        if(isActivityVisible){
            if(null == dialog){
                dialog = new ProgressDialog(this);
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.show();
        }
    }

    public void dismissProgressDialog(){
        dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityVisible = false;
    }

}
