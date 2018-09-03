package com.mydb.demo.type1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个数据库 一张表
 * 方式1 使用
 * db.execSQL() -- 只用于创建表使用
 * db.insert()
 * db.delete()
 * db.update()
 * db.query()
 *
 */
public class Type1SqliteImpl extends SQLiteOpenHelper {

    //**********************************基本设置**********************************
    private static final int DB_VERSION = 1;//数据库版本 默认都是1,涉及到更新需要修改
    private static final String DB_NAME = "type1.db";//数据库文件名
    private static final String DB_TABLE_NAME = "type1_table1";//表名

    /************************************表-字段属性 开始*************************************
     */

    //0 排序
    private static final String ORDERID = "_id";//表排序（非后台数据）

    //1姓名
    private static final String NAME = "name";

    //2性别
    private static final String SEX = "sex";

    //3年龄
    private static final String OLD = "old";

    //4住址
    private static final String PLACE = "place";

    /************************************表-字段属性 结束*************************************
     */


    // **********************************构造*************************************
    public Type1SqliteImpl(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Type1SqliteImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                DB_TABLE_NAME + "(" +
                ORDERID + " integer primary key autoincrement ," +//参数0
                NAME + " varchar(8)," +//参数1
                SEX + " text," +//参数2
                OLD + " text," +//参数3
                PLACE + " text" +//参数4
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

    //******************************************添加操作***********************************************
    public long addOne(String name, String sex, String old, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);//1
        values.put(SEX, sex);//2
        values.put(OLD, old);//3
        values.put(PLACE, place);//4

        long result = db.insert(DB_TABLE_NAME, null, values); // 组拼sql语句实现的.带返回值
        //        db.close();// 释放资源
        return result;
    }

    public void addList(List<PersonBean> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (PersonBean bean : list) {
            ContentValues values = new ContentValues();
            values.put(NAME, bean.getName());//1
            values.put(SEX, bean.getOld());//2
            values.put(OLD, bean.getOld());//3
            values.put(PLACE, bean.getPlace());//4
            db.insert(DB_TABLE_NAME, null, values); // 组拼sql语句实现的.带返回值
        }
    }

    //******************************************更新操作***********************************************
    public long updateOne(String name, String sex, String old, String place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);//1
        values.put(SEX, sex);//2
        values.put(OLD, old);//3
        values.put(PLACE, place);//4

        int result = db.update(DB_TABLE_NAME,
                values,//要修改的信息
                NAME + "=?",//通过条码更新
                new String[]{name}
        );

        return result;
    }

    //******************************************删除操作***********************************************

    public long deleteOne(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(DB_TABLE_NAME,
                NAME + "=?",
                new String[]{name});
        //        db.close();// 释放资源
        return result;
    }


    //******************************************查询***********************************************

    /**
     * 这种写法不严谨，数据库没有限制同名，所以查询结果可能是多个
     * @param name
     * @return
     */
    public PersonBean searchOne(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        PersonBean bean = new PersonBean();
        Cursor cursor = db.query(DB_TABLE_NAME,
                new String[]{
                        NAME,//1
                        SEX,//2
                        OLD,//3
                        PLACE//4
                },//22
                NAME + " =?",
                new String[]{name},
                null,
                null,
                null,
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //取
                String mName = cursor.getString(0);//1
                String sex = cursor.getString(1);//2
                String old = cursor.getString(2);//3
                String place = cursor.getString(3);//4

                //存
                bean.setName(mName);
                bean.setOld(old);
                bean.setSex(sex);
                bean.setPlace(place);
            }
            cursor.close();
        } else {
            Log.e("SJY", "数据库cursor异常关闭");
        }

        return bean;
    }

    public List<PersonBean> searchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<PersonBean> list = new ArrayList<>();

        Cursor cursor = db.query(DB_TABLE_NAME,
                new String[]{NAME,//1
                        SEX,//2
                        OLD,//3
                        PLACE//4
                },
                null,
                null,
                null,
                null,
                ORDERID + " desc",
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                //取
                String mName = cursor.getString(0);//1
                String sex = cursor.getString(1);//2
                String old = cursor.getString(2);//3
                String place = cursor.getString(3);//4


                PersonBean bean = new PersonBean();


                //存
                bean.setName(mName);
                bean.setOld(old);
                bean.setSex(sex);
                bean.setPlace(place);
                //添加列表中
                list.add(bean);
            }
            cursor.close();
        } else {
            Log.e("SJY", "数据库cursor异常关闭");
        }
        return list;

    }

    public List<String> searchAll2() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> list = new ArrayList<>();

        Cursor cursor = db.query(DB_TABLE_NAME,
                new String[]{NAME,//1
                        SEX,//2
                        OLD,//3
                        PLACE//4
                },
                null,
                null,
                null,
                null,
                ORDERID + " desc",
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                //取
                String mName = cursor.getString(0);//1
                String sex = cursor.getString(1);//2
                String old = cursor.getString(2);//3
                String place = cursor.getString(3);//4

                //存
                String str = mName + " " + sex + " " + old + " " + place;

                //添加列表中
                list.add(str);
            }
            cursor.close();
        } else {
            Log.e("SJY", "数据库cursor异常关闭");
        }
        return list;

    }

}
