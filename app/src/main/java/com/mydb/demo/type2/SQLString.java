package com.mydb.demo.type2;

/**
 * 所有sqlite语句，该处字段使用可视化工具编写的，可以排查错误
 * 可视化工具推荐：mac：(DB browser for sqlite)
 */
public class SQLString {

    //-----------------------------------------------`User` Table-----------------------------------------------------

    /**
     * 创建表
     */
    public final static String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS `User`(" +
                    "`username` TEXT(50) NOT NULL" +
                    ",`password` TEXT(20) NOT NULL" +
                    ",`phone` TEXT(100) NOT NULL" +
                    ",`userId` LONG NOT NULL" +
                    ",`age` INTEGER NOT NULL" +
                    ")";


    /**
     * 插入表
     * 占位符？可以少于字段个数，表示末尾的字段没有值
     */
    public final static String USER_INSERT =
            "INSERT INTO `User` " +
                    "(`username`,`password`,`phone`,`userId`,`age`) " +
                    "VALUES (?,?,?,?,?)";

    /**
     * 更新表
     * userId更新信息
     */
    public final static String USER_UPDATE_ALL_BY_userId =
            "UPDATE `User` SET `username`=?, `password`=?, `phone`=?, `age`=? WHERE `userId`=?";


}
