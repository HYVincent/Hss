package com.vincent.hss.bean.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/10 15:45
 *
 * @version 1.0
 */

public class DaoUtils {

    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public static void init(Context context,String dbName){
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(context,dbName,null);
        db = mHelper.getWritableDatabase();
        //注意：该数据库连接属于DaoMaster,所以多个Session指的是相同的数据库连接
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
