package com.popomusic;

import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.popomusic.bean.DaoMaster;
import com.popomusic.bean.DaoSession;


import org.greenrobot.greendao.database.Database;

/**
 * Created by popo on 2017/4/6 0006.
 */

public class MyApplication extends Application {
    public static DaoSession mDaoSession;
    public static DaoSession searchDaoSession;
    public static DaoSession videoDaoSession;
    public static Context mContext;

    //    private static RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this.getApplicationContext();
        initGreenDao();
        initdown();
    }
    //初始化数据库
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"music_db",null);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

        DaoMaster.DevOpenHelper helper1=new DaoMaster.DevOpenHelper(this,"search_db",null);
        Database db1 = helper.getWritableDb();
        searchDaoSession=new DaoMaster(db1).newSession();

        DaoMaster.DevOpenHelper Videohelper=new DaoMaster.DevOpenHelper(this,"video_db",null);
        Database videodb = helper.getWritableDb();
        videoDaoSession=new DaoMaster(videodb).newSession();

    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }

    public static DaoSession getDaoSession1(){
        return searchDaoSession;
    }

    public static DaoSession getVideoDaoSession(){return videoDaoSession;}

    //初始化FileDownloader
    private void initdown(){FileDownloader.init(mContext);}
}