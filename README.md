#数据库使用总结
   
##一个类实现数据库的所有功能（Type1SqliteImpl）
优缺点：适合**小功能**，**调用不频繁** 的应用的使用，因为参数少，数据调用不多，
##使用完全sqlite语句（type2包下的所有代码）
优缺点：完全适用 高强度数据频繁操作

SQLite可以解析大部分标准SQL语句：
具体实现步骤：

（1）创建表：
  模版：CREATE TABLE 表名 (字段 逗号分割)
  
 方式1：
    
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
   方式2：
   
    db.execSQL("CREATE TABLE IF NOT EXISTS `User`(" +
                                   "`username` TEXT(50) NOT NULL" +
                                   ",`password` TEXT(20) NOT NULL" +
                                   ",`phone` TEXT(100) NOT NULL" +
                                   ",`userId` LONG NOT NULL" +
                                   ",`age` INTEGER NOT NULL" +
                                   ")");
     
                
（2）添加：
   模版：insert into 表名(字段列表) values(值列表)
   
   
  方式1：

     db.insert(DB_TABLE_NAME, null, values);
     
   
  方式2：
   
    db.execSQL("insert into table_name ( name, sex,place,oldyear)  values ( 'val1' ,'val2','val3','val4')");
     
 （3）更新：
 
 模版：update 表名 set 字段名=值 where 条件子句
 
  方式1：
    
    db.update(DB_TABLE_NAME,
                 values,//要修改的信息
                 EXP_NUMBER + "=?",//通过条码更新
                 new String[]{ExpNum}
               
  方式2：
                 
    db.execSQL( "UPDATE `User` SET `username`=?, `password`=?, `phone`=?, `age`=? WHERE `userId`=?");   
         
（4）带参数条件的查询：

  模版：select * from 表名 where 条件子句 group by 分组字句 having ... order by 排序子句 (顺序一定不能错)
    
  方式1：
    
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
                
  方式2：该种结果是(List<List<String>>),需要将这种结果处理成需要bean类型，详见type2的代码，分别用SqlHelper获取到List<List<String>>数据，用SqlUtils获取到最终bean
  
    (List<List<String>>)db.execSQL("SELECT * FROM `User` WHERE `username` = ? AND `age` = ? ORDER BY `userId` ASC "）;
    常见语句：
    select name from person group by name having count(*)>1
    获取5条记录，跳过前面3条记录：
    select * from Account limit 5 offset 3/select * from Account limit 3,5 
      

（5）删除：

   模版：delete from 表名 where 条件子句

 方式1：
 
    db.delete(DB_TABLE_NAME,
                EXP_NUMBER + "=?",
                new String[]{ExpNum});
   
 方式2：   
            
    db.execSQL(" delete from table_demo1 where name =  '小美'");
                
 全删除：
             
    db.execSQL("DELETE FROM " + DB_TABLE_NAME);
    
如上就是数据库的增删改查，可以发现每一种都有两种方式实现，现在我们就通过api了解使用规则：

##SQLiteOpenHelper详解：
1
public abstract class SQLiteOpenHelper extends Object 

2构造：

SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)

SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) 

3 方法：

 void **close()**：关闭任何打开 database项目

 String **getDatabaseName()** ：返回打开的SQLite数据库的名称，如构造函数所示

 SQLiteDatabase **getReadableDatabase()** 创建或打开一个database

 SQLiteDatabase **getWritableDatabase()** 创建或打开一个database用于读和写
 
 void 	**onConfigure(SQLiteDatabase db)** 在配置数据库连接时调用，以启用写前日志或外键支持等特性。
 
 abstract void 	**onCreate(SQLiteDatabase db)** 数据库第一次被创建时触发
 
 void **onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)** 当数据库需要降级时调用。
 
 void **onOpen(SQLiteDatabase db)** 当数据库被打开时调用
 
 abstract void **onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)** 当数据库需要更新时调用
 
 void 	**setWriteAheadLoggingEnabled(boolean enabled)**  启用或禁用数据库的预写日志记录。
 
 ##SQLiteDatabase详解：
 
 常量：
 
CONFLICT_ABORT 禁止回滚，只执行最新指令

CONFLICT_FAIL 当出现约束冲突时，命令会以返回代码SQLITE_CONSTRAINT中止。

CONFLICT_IGNORE 当出现约束违反时，包含约束违反的一行不会被插入或更改。

CONFLICT_NONE 如果没有指定冲突操作，请使用以下命令。

CONFLICT_REPLACE 当发生唯一约束违反时，在插入或更新当前行之前，会删除导致约束违反的已有行。

CONFLICT_ROLLBACK 当出现约束冲突时，会立即回滚，从而结束当前事务，命令会以SQLITE_CONSTRAINT的返回代码中止。

CREATE_IF_NECESSARY 方法openDatabase(String, SQLiteDatabase.CursorFactory, int)的标志，用于在数据库文件不存在时创建该文件。

ENABLE_WRITE_AHEAD_LOGGING 方法openDatabase(String, SQLiteDatabase.CursorFactory, int)的标志,打开数据库文件，默认情况下启用预写日志。

MAX_SQL_CACHE_SIZE


 

 
 





