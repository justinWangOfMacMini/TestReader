package com.uplinetek.testviewer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.uplinetek.testviewer.Login.UserInfo;
import com.uplinetek.testviewer.utils.PrefUtils;

public class MainActivity extends Activity {

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final MyViewerHelper myViewerHelper = MyViewerHelper.getInstance(getApplicationContext());
        myViewerHelper.setLoginListener(new MyViewerHelper.LoginListener() {
            @Override
            public void onLoginResult(boolean success) {
                if(success){
                    boolean haveShowGuide = PrefUtils.getBoolean(MainActivity.this, PrefUtils.HAVE_SHOW_GUIDE);
                    Intent intent = new Intent();
                    if(haveShowGuide){
                        if(mUserInfo.isLogin){
//                            intent.setClass(getApplicationContext(), CameraListActivity.class);
                        }else{
//                            intent.setClass(getApplicationContext(), LoginActivity.class);
                        }
                    }else{
//                        intent.setClass(getApplicationContext(), GuideActivity.class);
//                        intent.putExtra(GuideActivity.START_AVS_ACTIVITY, true);
                    }
                    myViewerHelper.setLoginListener(null);
                    startActivity(intent);
//                    LoadingActivity.this.finish();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        //do not let user exit;
        //super.onBackPressed();
    }

}
