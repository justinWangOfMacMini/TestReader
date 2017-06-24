package com.uplinetek.testviewer.db;

import android.graphics.Bitmap;

/**
 * Created by wangshengjun on 2017/6/24.
 */

public class CameraInfo {

    private long cid;
    private String cameraName;
    private String cameraUser;
    private String cameraPwd;
    private Bitmap cameraThumb;
    private boolean isOnline;
    private boolean pwdIsRight;
    private String os;

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getCid() {
        return cid;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraUser(String cameraUser) {
        this.cameraUser = cameraUser;
    }

    public String getCameraUser() {
        return cameraUser;
    }

    public void setCameraPwd(String cameraPwd) {
        this.cameraPwd = cameraPwd;
    }

    public String getCameraPwd() {
        return cameraPwd;
    }

    public void setCameraThumb(Bitmap cameraThumb) {
        this.cameraThumb = cameraThumb;
    }

    public Bitmap getCameraThumb() {
        return cameraThumb;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setPwdIsRight(boolean pwdIsRight) {
        this.pwdIsRight = pwdIsRight;
    }

    public boolean isPwdIsRight() {
        return pwdIsRight;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

}
