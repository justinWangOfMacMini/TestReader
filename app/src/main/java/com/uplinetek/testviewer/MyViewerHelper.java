package com.uplinetek.testviewer;

import android.content.Context;

import com.ichano.rvs.viewer.Viewer;
import com.ichano.rvs.viewer.bean.UserInfo;
import com.ichano.rvs.viewer.constant.AuthState;
import com.ichano.rvs.viewer.constant.LoginError;
import com.ichano.rvs.viewer.constant.LoginState;
import com.ichano.rvs.viewer.constant.RemoteStreamerState;
import com.ichano.rvs.viewer.constant.RvsError;
import com.ichano.rvs.viewer.constant.StreamerConfigState;
import com.ichano.rvs.viewer.constant.StreamerInfoType;
import com.ichano.rvs.viewer.ui.ViewerInitHelper;

/**
 * Created by Justin on 2017/6/22.
 */

public class MyViewerHelper extends ViewerInitHelper {

    private static MyViewerHelper mViewer;
    private UserInfo mUserInfo;

    private LoginListener mLoginListener;

    public void setLoginListener(LoginListener mLoginListener) {
        this.mLoginListener = mLoginListener;
    }

    public static MyViewerHelper getInstance(Context applicationContext) {

        if (null == mViewer) {

            mViewer = new MyViewerHelper(applicationContext);

        }

        return mViewer;

    }

    public MyViewerHelper(Context applicationContext) {
        super(applicationContext);
    }

    @Override
    public void logout() {
        super.logout();
    }

    @Override
    public void login() {
        super.login();
    }

    @Override
    public String getConfigPath() {
        return super.getConfigPath();
    }

    @Override
    public String getCachePath() {
        return super.getCachePath();
    }

    @Override
    public String getPersistentPath() {
        return super.getPersistentPath();
    }

    @Override
    public String getCompanyID() {
        return "11112017061314174901496984858215";
    }

    @Override
    public String getCompanyKey() {
        return "c4ff26461c364e868d579b3c0e6dec2b";
    }

    @Override
    public String getAppID() {
        return "31112017061314223801496984858216";
    }

    @Override
    public String getLicense() {
        return "";
    }

    @Override
    public void onStreamerConfigState(long l, StreamerConfigState streamerConfigState) {

    }

    @Override
    public void onUpdateCID(long l) {

    }

    @Override
    public void onRemoteStreamerStateChange(long l, RemoteStreamerState remoteStreamerState, RvsError rvsError) {
        switch (remoteStreamerState){
            case INIT:{

            }
                break;
            case AUTHER:{

            }
                break;
            case CONNECTED:{

            }
                break;
            case CANUSE:{

            }
                break;
            case FAIL:{

            }
                break;

        }
    }

    @Override
    public void onStreamerInfoUpdate(long l, StreamerInfoType streamerInfoType) {

    }

    @Override
    public boolean useIchanoUserSystem() {
        return false;
    }

    @Override
    public void onAuthResult(AuthState authState, RvsError rvsError) {
        switch (authState) {
            case INIT: {

            }
                break;
            case AUTHER: {


            }
                break;
            case CONNECTING:{

            }
                break;
            case SUCCESS: {
                long cid = Viewer.getViewer().getCID();
//                mUserInfo.saveClientC
            }
                break;
            case FAIL: {

            }
                break;
        }
    }



    public interface LoginListener {
        public void onLoginResult(boolean success);
    }




}
