#数据库使用总结

带参数的查询

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
