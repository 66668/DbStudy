package com.mydb.demo.type2;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 直接和SqlDB对接数据的类，获取数据库的原始值，再通过SqlUtil处理成 UI能使用的数据
 */
public class SqlHelper {

    public static List<List<String>> getAllUser() {
        List<List<String>> list = new ArrayList<>();
        SqlDB.beginTransaction();
        try {
            list = (List<List<String>>) SqlDB.getInstance().execSQL(SQLString.USER_SELECT);
            Log.d("SJY", list.size() + "--");
            SqlDB.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("SJY", e.toString());
        } finally {
            SqlDB.endTransaction();
        }
        return list;
    }

    public static String getAllUserNumber() {
        List<List<String>> list = new ArrayList<>();
        SqlDB.beginTransaction();
        try {
            list = (List<List<String>>) SqlDB.getInstance().execSQL(SQLString.USER_NUMBER);
            SqlDB.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("SJY", e.toString());
        } finally {
            SqlDB.endTransaction();
        }
        return list.size() > 0 ? list.get(0).get(0) : "0";
    }

}
