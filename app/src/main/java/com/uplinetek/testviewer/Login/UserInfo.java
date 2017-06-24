package com.uplinetek.testviewer.Login;

import android.content.Context;

import com.uplinetek.testviewer.utils.PrefUtils;

/**
 * Created by Justin on 2017/6/23.
 */

public class UserInfo {

    public static final String CLIENT_CID = "CLIENT_CID";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_SESSION_ID = "USER_SESSION_ID";
    public static final String USER_RECOMMAND_URL = "USER_RECOMMAND_URL";
    public static final String TS = "TS";

    private static UserInfo mUserInfo;
    private Context mContext;
    public boolean isLogin;
    public long clientCid;
    public String name;
    public String sessionId;
    public String recommandURL;
    public String ts = "";

    private UserInfo(Context mContext) {
        this.mContext = mContext;
        String str = PrefUtils.getString(mContext, CLIENT_CID);
//        if (!String)
    }
}