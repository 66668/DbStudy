package com.mydb.demo.type2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mydb.demo.MyApplication;

/**
 *
 */
public class SqlDB extends SQLiteOpenHelper {

    //**********************************基本设置**********************************
    private static final int DB_VERSION = 1;//数据库版本 默认都是1,涉及到更新需要修改
    private static final String DB_NAME = "type2.db";//数据库文件名
    private static final String DB_TABLE_NAME = "type2_table1";//表名

    //**********************************变量**********************************
    private static SqlDB singleTon = null;
    private SQLiteDatabase db = null;

    public SqlDB() {
        super(MyApplication.getInstance(), DB_NAME, null, DB_VERSION);
        db = getReadableDatabase();
    }

    public static SqlDB getInstance() {
        if (singleTon == null) {
            synchronized (SqlDB.class) {
                if (singleTon == null) {
                    singleTon = new SqlDB();
                }
            }
        }

        return singleTon;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
