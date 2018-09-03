package com.mydb.demo.type2;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理转换相关内容
 */
public class SqlUtils {

    /**
     * 获取 数据库所有数据
     *
     * @return
     */
    public static List<String> getAllUser() {
        List<UserBean> list = getAllUserBean();
        List<String> mList = new ArrayList<>();
        for (UserBean bean : list) {
            StringBuilder builder = new StringBuilder();
            builder.append(bean.username);
            builder.append(" ");
            builder.append(bean.password);
            builder.append(" ");
            builder.append(bean.phone);
            builder.append(" ");
            builder.append(bean.userId);
            builder.append(" ");
            builder.append(bean.age);
            mList.add(builder.toString());
        }
        return mList;
    }

    public static List<UserBean> getAllUserBean() {
        List<UserBean> list = new ArrayList<>();
        List<List<String>> orgList = SqlHelper.getAllUser();
        for (int i = 0; i < orgList.size(); i++) {
            UserBean bean = getUserBean(orgList.get(i));
            list.add(bean);
        }
        return list;
    }

    private static UserBean getUserBean(List<String> list) {
        UserBean bean = new UserBean();
        bean.username = list.get(0);
        bean.password = list.get(1);
        bean.phone = list.get(2);
        bean.userId = Long.valueOf(list.get(3));
        bean.age = Integer.valueOf(list.get(4));
        return bean;
    }

    public static int getAllUserBeanNumber() {
        String orgList = SqlHelper.getAllUserNumber();
        int count = Integer.valueOf(orgList);
        return count;
    }
}
