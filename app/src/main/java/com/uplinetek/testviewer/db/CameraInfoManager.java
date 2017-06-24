package com.uplinetek.testviewer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wangshengjun on 2017/6/24.
 */

public class CameraInfoManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "testviewer.db";

    private static final String TABLE_NAME = "testviewer";
    private static final String _ID = "id";
    private static final String _CID = "cid";
    private static final String _NAME = "name";
    private static final String _USER = "user";
    private static final String _PASSWORD = "password";
    private static final String _THUMB = "thumb";
    private static final String _OS = "os";

    private String[] mColumns = new String[]{_ID, _CID, _NAME, _USER, _PASSWORD, _THUMB, _OS};

    public CameraInfoManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("+
                _ID + " INTEGER PRIMARY KEY," +
                _CID + " LONG,"+
                _NAME + " TEXT," +
                _USER + " TEXT," +
                _PASSWORD + " TEXT," +
                _THUMB + " BLOB," +
                _OS + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public byte[] Bitmap2Bytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public long addCameraInfo(CameraInfo info){
        SQLiteDatabase db = this.getWritableDatabase();
        long rowID = 0;
        CameraInfo temp = getCameraInfo(db, info.getCid(), rowID);
        if(null == temp){
            ContentValues values = new ContentValues();
            values.put(_CID, info.getCid());
            values.put(_NAME, info.getCameraName());
            values.put(_USER, info.getCameraUser());
            values.put(_PASSWORD, info.getCameraPwd());
            values.put(_THUMB, Bitmap2Bytes(info.getCameraThumb()));
            values.put(_OS, info.getOs());
            long ret = db.insert(TABLE_NAME, null, values);
            db.close();
            return ret;
        }else{
            update(info);
            return rowID;
        }
    }

    private CameraInfo getCameraInfo(SQLiteDatabase db, long cid, long rowID){
        Cursor cur = db.query(TABLE_NAME, mColumns, _CID + "=?",
                new String[]{String.valueOf(cid)}, null, null, null);
        if(null != cur && cur.getCount() > 0) {
            cur.moveToFirst();
            rowID = cur.getPosition();
        }else {
            if(null != cur) cur.close();
            return null;
        }
        CameraInfo info = new CameraInfo();
        info.setCid(cur.getLong(cur.getColumnIndex(_CID)));
        info.setCameraName(cur.getString(cur.getColumnIndex(_NAME)));
        info.setCameraUser(cur.getString(cur.getColumnIndex(_USER)));
        info.setCameraPwd(cur.getString(cur.getColumnIndex(_PASSWORD)));
        byte[] bmp = cur.getBlob(cur.getColumnIndex(_THUMB));
        info.setCameraThumb(BitmapFactory.decodeByteArray(bmp, 0, bmp.length));
        info.setOs(cur.getString(cur.getColumnIndex(_OS)));
        info.setOnline(false);
        info.setPwdIsRight(true);
        cur.close();
        return info;
    }

    public CameraInfo getCameraInfo(long cid){
        SQLiteDatabase db = this.getWritableDatabase();
        long rowID = 0;
        return getCameraInfo(db, cid, rowID);
    }

    public ArrayList<CameraInfo> getAllCameraInfos(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<CameraInfo> infos = new ArrayList<CameraInfo>();
        Cursor cur = db.query(TABLE_NAME, mColumns, null, null, null, null, null);
        if(null != cur && cur.getCount() > 0) {
            cur.moveToFirst();
            do{
                CameraInfo info = new CameraInfo();
                info.setCid(cur.getLong(cur.getColumnIndex(_CID)));
                info.setCameraName(cur.getString(cur.getColumnIndex(_NAME)));
                info.setCameraUser(cur.getString(cur.getColumnIndex(_USER)));
                info.setCameraPwd(cur.getString(cur.getColumnIndex(_PASSWORD)));
                byte[] bmp = cur.getBlob(cur.getColumnIndex(_THUMB));
                info.setCameraThumb(BitmapFactory.decodeByteArray(bmp, 0, bmp.length));
                info.setOs(cur.getString(cur.getColumnIndex(_OS)));
                info.setOnline(false);
                info.setPwdIsRight(true);
                infos.add(info);
            }while(cur.moveToNext());
            cur.close();
            return infos;
        }else {
            if(null != cur) cur.close();
            return null;
        }
    }

    public int update(CameraInfo info){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_CID, info.getCid());
        values.put(_NAME, info.getCameraName());
        values.put(_USER, info.getCameraUser());
        values.put(_PASSWORD, info.getCameraPwd());
        values.put(_THUMB, Bitmap2Bytes(info.getCameraThumb()));
        values.put(_OS, info.getOs());
        int ret = db.update(TABLE_NAME, values,  _CID + "=?", new String[]{String.valueOf(info.getCid())});
        db.close();
        return ret;
    }

    public int delete(CameraInfo info){
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.delete(TABLE_NAME, _CID + "=?", new String[]{String.valueOf(info.getCid())});
        db.close();
        return ret;
    }

    public int deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.delete(TABLE_NAME, null, null);
        db.close();
        return ret;
    }

}
