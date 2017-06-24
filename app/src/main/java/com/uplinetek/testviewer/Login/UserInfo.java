package com.uplinetek.testviewer.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.uplinetek.testviewer.utils.PrefUtils;
import com.uplinetek.testviewer.utils.StringUtils;

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

    private UserInfo(Context context) {
        this.mContext = context;
        String str = PrefUtils.getString(mContext, CLIENT_CID);
        if (!StringUtils.isEmpty(str)) {
            clientCid = Long.parseLong(str);
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        isLogin = sp.getBoolean(IS_LOGIN, false);
        name = sp.getString(USER_NAME, "");
        sessionId = sp.getString(USER_SESSION_ID, "");
        recommandURL = sp.getString(USER_RECOMMAND_URL, "");
        ts = sp.getString(TS, "");
    }

    public static UserInfo getUserInfo(Context context){
        if(null == mUserInfo){
            mUserInfo = new UserInfo(context);
        }
        return mUserInfo;
    }

    public void saveClientCid(long cid){
        if(cid > 0){
            if(cid != clientCid)
                clientCid = cid;
            PrefUtils.putString(mContext, CLIENT_CID, String.valueOf(cid));
        }
    }

    public void setLoginInfo(boolean isLogin, String name, String sessionId, String recommandURL){
        this.isLogin = isLogin;
        this.name = name;
        this.sessionId = sessionId;
        this.recommandURL = recommandURL;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.putString(USER_NAME, name);
        editor.putString(USER_SESSION_ID, sessionId);
        editor.putString(USER_RECOMMAND_URL, recommandURL);
        editor.commit();
    }

    public void setTS(String ts){
        if(!ts.equals(this.ts)){
            this.ts = ts;
            PrefUtils.putString(mContext, TS, ts);
        }
    }

}