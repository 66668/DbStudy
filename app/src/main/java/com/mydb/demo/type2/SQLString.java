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
     * 删除一条
     * (1)userId满足，就删除该条信息
     * <p>
     * "DELETE FROM `User` WHERE `userId` = ?";
     * <p>
     * (2)多条件删除
     * <p>
     * "DELETE FROM `User` WHERE `username` = ? AND `password` = ?";
     * "DELETE FROM `User` WHERE `username` = ? OR `password` = ?";
     */
    public final static String USER_DELETE =
            "DELETE FROM `User` WHERE `userId` = ?";


    /**
     * 更新表
     * 以userId字段更新，可以全部字段都更新，也可以更新某一个字段
     * <p>
     * "UPDATE `User` SET `username`=?, `password`=?, `phone`=?, `age`=? WHERE `userId`=?"
     */
    public final static String USER_UPDATE =
            "UPDATE `User` SET `username`=?, `password`=?, `phone`=?, `age`=? WHERE `userId`=?";


    /**
     * 查询
     * <p>
     * (1)查询所有+排序
     * "SELECT * FROM `User` ORDER BY `userId` ASC ";
     * <p>
     * （2）以id查询+排序
     * "SELECT * FROM `User` WHERE `userId` = ? ORDER BY `userId` ASC ";
     * <p>
     * （3）多条件查询+排序
     * "SELECT * FROM `User` WHERE `username` = ? AND `age` = ? ORDER BY `userId` ASC ";
     * <p>
     * （4）模糊查询
     * "SELECT count(*) FROM `Note` WHERE `trash` = 0 AND `syncState` != 5 AND (`tagStr` LIKE ?) AND `userId` = ? ";
     */
    public final static String USER_SELECT = "SELECT * FROM `User` ORDER BY `userId` DESC ";


    /**
     * 查询--统计个数
     */
    public final static String USER_NUMBER =
            "SELECT count(*) FROM `User` ORDER BY `userId` DESC ";

    //-----------------------------------------------`NOTE` Table-----------------------------------------------------

    /**
     * 创建表
     */
    public final static String CREATE_TABLE_NOTE =
            "CREATE TABLE `NOTE`(" +
                    "`_id` LONG PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ",`noteName` TEXT(100) NOT NULL" +
                    ",`userId` LONG NOT NULL" +
                    ",`content` TEXT(100) NOT NULL" +
                    ")";


}
