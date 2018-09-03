#数据库使用总结

（1）创建表：

     db.execSQL("CREATE TABLE IF NOT EXISTS " +
                DB_TABLE_NAME + "(" +
                ORDERID + " integer primary key autoincrement ," +//参数0
                EXP_DID + " text," +//参数1
                EXP_SENDTYPE + " text," +//参数2
                EXP_NUMBER + " text  UNIQUE," +//参数3 不为空且唯一
                EXP_ID + " text," +//参数4
                EXP_TITLE + " text," +//参数5
                EXP_ADDRESSEE_PHONE + " varchar(20)," +//参数6
                EXP_ADDRESSEE_NAME + " text," +//参数7
                EXP_ADDRESSEE_LOCAL + " text," +//参数8
                EXP_ConID + " text," +//参数9
                EXP_ARRIVE_TYPE + " text," +//参数10
                EXP_ARRIVE_PRICE + " text," +//参数11
                EXP_ARRIVE_PRICE_TIME + " text," +//参数12
                EXP_COMPANY_ID + " text," +//参数13
                EXP_COMPANY_NAME + " text," +//参数14
                EXP_COMPANY_TYPE + " text," +//参数15
                EXP_RID + "  text," +//参数16
                EXP_STATE + "  text," +//参数17
                EXP_SIGN_PAHT + " text," +//参数18
                EXP_SIGN_TIME + " text," +//参数19
                EXP_ERROR_MSG + " text," +//参数20
                EXP_PRICE_SRUE + " text," +//参数21
                EXP_BACKSHOP_SRUE + " text" +//参数22
                ")");
                
（2）添加：
    方式1：

     db.insert(DB_TABLE_NAME, null, values);
     
   
   方式2：
   
    db.execSQL("insert into table_name ( name, sex,place,oldyear)  values ( 'val1' ,'val2','val3','val4')");
     
 （3）更新：
 
    db.update(DB_TABLE_NAME,
                 values,//要修改的信息
                 EXP_NUMBER + "=?",//通过条码更新
                 new String[]{ExpNum}
             
（4）带参数的查询：


    Cursor cursor = db.query(DB_TABLE_NAME,
                new String[]{EXP_DID,//1
                        EXP_SENDTYPE,//2
                        EXP_NUMBER,//3
                        EXP_ID,//4
                        EXP_TITLE,//5
                        EXP_ADDRESSEE_PHONE,//6
                        EXP_ADDRESSEE_NAME,//7
                        EXP_ADDRESSEE_LOCAL, //8
                        EXP_ConID, //9
                        EXP_ARRIVE_TYPE, //10
                        EXP_ARRIVE_PRICE,//11
                        EXP_ARRIVE_PRICE_TIME, //12
                        EXP_COMPANY_ID, //13
                        EXP_COMPANY_NAME, //14
                        EXP_COMPANY_TYPE, //15
                        EXP_RID,//16
                        EXP_STATE,//17
                        EXP_SIGN_PAHT,//18
                        EXP_SIGN_TIME,//19
                        EXP_ERROR_MSG,//20
                        EXP_PRICE_SRUE,//21
                        EXP_BACKSHOP_SRUE},//22
                EXP_STATE + " IN (?,?)",
                new String[]{"1", "2"},
                null,
                null,
                ORDERID + " desc",
                null);

（5）删除：
 方式1：
 
    db.delete(DB_TABLE_NAME,
                EXP_NUMBER + "=?",
                new String[]{ExpNum});
   
 方式2：   
            
    db.execSQL(" delete from table_demo1 where name =  '小美'");
                
 全删除：
             
    db.execSQL("DELETE FROM " + DB_TABLE_NAME);