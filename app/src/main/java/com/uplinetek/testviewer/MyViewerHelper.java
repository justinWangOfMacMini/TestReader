package com.uplinetek.testviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;

import com.ichano.rvs.viewer.Viewer;
import com.ichano.rvs.viewer.bean.StreamerInfo;
import com.ichano.rvs.viewer.callback.RecvJpegListener;
import com.ichano.rvs.viewer.constant.AuthState;
import com.ichano.rvs.viewer.constant.LoginError;
import com.ichano.rvs.viewer.constant.LoginState;
import com.ichano.rvs.viewer.constant.RemoteStreamerState;
import com.ichano.rvs.viewer.constant.RvsError;
import com.ichano.rvs.viewer.constant.RvsJpegType;
import com.ichano.rvs.viewer.constant.RvsSessionState;
import com.ichano.rvs.viewer.constant.StreamerConfigState;
import com.ichano.rvs.viewer.constant.StreamerInfoType;
import com.ichano.rvs.viewer.constant.StreamerPresenceState;
import com.ichano.rvs.viewer.ui.ViewerInitHelper;
import com.uplinetek.testviewer.Login.UserInfo;
import com.uplinetek.testviewer.db.CameraInfo;
import com.uplinetek.testviewer.db.CameraInfoManager;
import com.uplinetek.testviewer.utils.ImageDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Justin on 2017/6/22.
 */

public class MyViewerHelper extends ViewerInitHelper {

    private static final String TAG = "MyViewerHelper";
    private static MyViewerHelper mViewer;
    private UserInfo mUserInfo;
    private LoginListener mLoginListener;
    private List<CameraStateListener> mCameraStateListeners = new ArrayList<CameraStateListener>();

    private static List<CameraInfo> mCameraInfos;
    private static CameraInfoManager mCameraInfoManager;

    private Handler mHandler = new Handler();
    private final static long GET_THUMB_PERIOD = 600000;

    private HashMap<Long, Long> mThumbsGetTime = new HashMap<Long, Long>();
    private HashMap<Long, Long> mThumbRequestMap = new HashMap<Long, Long>();
    private static Context mContext;
    public static MyViewerHelper getInstance(Context applicationContext) {

        if (null == mViewer) {
            mViewer = new MyViewerHelper(applicationContext);

            mCameraInfoManager = new CameraInfoManager(applicationContext);
            mCameraInfos = mCameraInfoManager.getAllCameraInfos();
            if(null == mCameraInfos) mCameraInfos = new ArrayList<CameraInfo>();
            mContext = applicationContext;

        }

        mViewer.login();
        return mViewer;

    }

    public List<CameraInfo> getAllCameraInfos(){
        return mCameraInfos;
    }

    public void removeAllCameraInfos(){
        mCameraInfos.clear();
    }

    public CameraInfo getCameraInfo(long cid){
        for (CameraInfo info : mCameraInfos) {
            if(cid == info.getCid()){
                return info;
            }
        }
        return null;
    }

    public void addCameraInfo(CameraInfo info){
        mCameraInfos.add(info);
    }

    public void removeCameraInfo(CameraInfo info){
        mCameraInfos.remove(info);
    }

    private MyViewerHelper(Context applicationContext) {
        super(applicationContext);
        mUserInfo = UserInfo.getUserInfo(mContext);
    }

    @Override
    public String getAppID() {
        return "31112017061314223801496984858216";
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
    public String getLicense() {
        return "UP00011";
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
                mUserInfo.saveClientCid(cid);
                if(null != mLoginListener) mLoginListener.onLoginResult(true);
            }
            break;
            case FAIL: {
                //连接服务器鉴权失败，失败原因查看error
                if(null != mLoginListener) mLoginListener.onLoginResult(false);
                // When you are going to publish an app, you need register on our website (dev.ichano.com) to obtain license code.
//                if(rvsError == Rvs.ERR_WRONG_PACKAGE){
                    Toast.makeText(context, R.string.wrong_package_name, Toast.LENGTH_LONG).show();
//                }
            }
            break;
        }
    }

    @Override
    public void onRemoteStreamerStateChange(final long streamerCID, final RemoteStreamerState remoteStreamerState, RvsError rvsError) {

        long lastTime = (null == mThumbsGetTime.get(streamerCID)) ? 0 : mThumbsGetTime.get(streamerCID);
        long cur = System.currentTimeMillis();

        //do not get thumb so busy.
        if(cur - lastTime > GET_THUMB_PERIOD){
            mThumbsGetTime.put(streamerCID, cur);
            long requestId =  Viewer.getViewer().getMedia().requestJpeg(streamerCID, 0, 0, RvsJpegType.ICON, new RecvJpegListener() {

                @Override
                public void onRecvJpeg(long requestId,byte[] data) {
                    if(null == mThumbRequestMap.get(requestId)) return;
                    long cid = mThumbRequestMap.get(requestId);
                    Bitmap bmp = ImageDownloader.getInstance().getDefaultBmp(mContext) ;
                    if(data!=null){
                        Bitmap bmpMem = ImageDownloader.getInstance().putBitmapData(String.valueOf(streamerCID), data);
                        if(bmpMem!=null) bmp = bmpMem;
                    }
                    CameraInfo info = getCameraInfo(cid);
                    if(null != info && null != bmp){
                        info.setCameraThumb(bmp);
                        mCameraInfoManager.update(info);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                if(mCameraStateListeners.size() > 0){
                                    for (CameraStateListener l : mCameraStateListeners) {
                                        l.onCameraConnectionChange(streamerCID, RemoteStreamerState.CONNECTED == remoteStreamerState);
                                    }
                                }
                            }
                        });
                    }
                }
            });
            mThumbRequestMap.put(requestId, streamerCID);
        }


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
    public void onStreamerConfigState(long l, StreamerConfigState streamerConfigState) {

    }

    @Override
    public void onStreamerPresenceState(long streamerCid, StreamerPresenceState state) {
        CameraInfo info = getCameraInfo(streamerCid);
        if(null != info){
            StreamerInfo sinfo = Viewer.getViewer().getStreamerInfoMgr().getStreamerInfo(streamerCid);
            String name = sinfo.getDeviceName();
            if(null != name && (!info.getCameraName().equals(name))){
                info.setCameraName(name);
                mCameraInfoManager.update(info);
            }
            if(StreamerPresenceState.USRNAME_PWD_ERR == state && info.isPwdIsRight()){
                info.setOnline(false);
                info.setPwdIsRight(false);
                if(mCameraStateListeners.size() > 0){
                    for (CameraStateListener l : mCameraStateListeners) {
                        l.onCameraStateChange(streamerCid, state);
                    }
                }
            }else {
                boolean online = false;
                if(StreamerPresenceState.ONLINE == state){
                    online = true;
                    info.setPwdIsRight(true);
                }
                if(info.isOnline() != online){
                    info.setOnline(online);
                    if(mCameraStateListeners.size() > 0){
                        for (CameraStateListener l : mCameraStateListeners) {
                            l.onCameraStateChange(streamerCid, state);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onUpdateCID(long cid) {

        mUserInfo.saveClientCid(cid);

    }



    @Override
    public void onStreamerInfoUpdate(long l, StreamerInfoType streamerInfoType) {

    }

    @Override
    public boolean useIchanoUserSystem() {
        return false;
    }



    public void setLoginListener(LoginListener mLoginListener) {
        this.mLoginListener = mLoginListener;
    }

    public void addCameraStateListener(CameraStateListener l){
        if(!mCameraStateListeners.contains(l)){
            mCameraStateListeners.add(l);
        }
    }

    public void removeCameraStateListener(CameraStateListener l){
        mCameraStateListeners.remove(l);
    }


    public interface LoginListener {
        public void onLoginResult(boolean success);
    }

    public interface CameraStateListener{
        public void onCameraStateChange(long streamerCID, StreamerPresenceState state);
        public void onCameraConnectionChange(long streamerCID, boolean connected);
    }


}