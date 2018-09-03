package com.mydb.demo.type2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mydb.demo.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 一个库创建三个表，操作语句全部使用sqlite 语句，即db.execSQL()代替db.insert() db.query() db.update()这三个方法
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
        db = getWritableDatabase();
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

    //===============================================数据库操作，统一处理方法====================================================

    /**
     * @param sql
     * @param args
     * @return 说明:数据存储，最后是什么类型都没有区别
     */
    public Object execSQL(String sql, Object... args) {

        logSql(sql, args);
        if (sql.startsWith("SELECT")) {//查询
            return select(sql, args);
        } else if (sql.startsWith("INSERT")) {//插入数据
            return insertSQL(sql, args);
        } else {//delete update
            db.execSQL(sql, args);
        }
        return null;
    }

    //================================================================================

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLString.CREATE_TABLE_USER);
//        db.execSQL(SQLString.CREATE_TABLE_NOTE);

    }

    /**
     * 如有版本更新，会执行此方法
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * 插入数据
     *
     * @param sql
     * @param args
     * @return
     */
    private long insertSQL(String sql, Object[] args) {
        long id = -1;
        try {
            db = getWritableDatabase();
            int start = 0, end = 0;
            String tableName = "";
            ContentValues values = new ContentValues();

            //取tablename
            start = sql.indexOf("`");
            end = sql.indexOf("`", start + 1);
            tableName = sql.substring(start, end + 1);

            //取内容
            for (int i = 0; i < args.length; i++) {
                start = sql.indexOf("`", end + 1);
                end = sql.indexOf("`", start + 1);
                //sql中
                values.put(sql.substring(start, end + 1), args[i] != null ? args[i].toString() : "");

                //打印
                int s = start;
                int e = end;
            }

            id = db.insertOrThrow(tableName, null, values);
//            MLog.d("saveNote", "TNDb--insertSQL-->noteLocalId=" + id);
        } catch (Exception e) {
            Log.e("TNDb--insertSQL", "插入数据库失败:" + e.toString());
        }
        return id;
    }


    /**
     * 查询
     *
     * @param sql
     * @param args
     * @return
     */
    private List<List<String>> select(String sql, Object[] args) {
        String[] strArgs = new String[args.length];
        for(int i=0;i<args.length;i++){
            strArgs[i] = (String) args[i];
        }

        Cursor cursor = db.rawQuery(sql, strArgs);

        List<List<String>> allData = new ArrayList<List<String>>();
        while (cursor.moveToNext()) {
            List<String> rowData = new ArrayList<String>();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String value = cursor.getString(i);
                if (value != null)
                    rowData.add(value);
                else
                    rowData.add("0");
            }
            allData.add(rowData);
        }
        cursor.close();
//        MLog.d("TNDB--select打印：" + allData.size() + "个笔记。", allData.toString());

        return allData;
    }

    /**
     * 调用步骤四部曲：
     * （1）开始：beginTransaction（）
     * （2）具体操作 execSQL（）
     * （3）操作成功 setTransactionSuccessful()
     * (4)操作结束 endTransaction()
     */
    //(1)
    public static void beginTransaction() {
        getInstance().db.beginTransaction();
    }

    //(2)
    public static void setTransactionSuccessful() {
        getInstance().db.setTransactionSuccessful();
    }

    //（3）finaly
    public static void endTransaction() {
        getInstance().db.endTransaction();
    }


    private void logSql(String sql, Object[] args) {
        String values = "";
        for (Object arg : args) {
            String str = "`" + (String) arg + "` ";
            values = values + str;
        }
        Log.w("SJY", sql + "\r\n" + values);
    }


}
