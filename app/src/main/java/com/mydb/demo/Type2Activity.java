package com.mydb.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mydb.demo.adapter.MainAdapter;
import com.mydb.demo.type1.PersonBean;
import com.mydb.demo.type1.Type1SqliteImpl;
import com.mydb.demo.type2.SQLString;
import com.mydb.demo.type2.SqlDB;
import com.mydb.demo.type2.SqlUtils;

import java.util.ArrayList;
import java.util.List;


public class Type2Activity extends BaseAct implements View.OnClickListener {
    //=========================控件===============================
    RecyclerView recyclerView;
    TextView tv_total;
    EditText et_username, et_password, et_phone, et_userId, et_age;
    EditText et_noteName2, et_userId2, et_content2;
    Button btn_add1, btn_delete1, btn_update1, btn_select1;
    Button btn_add2, btn_delete2, btn_update2, btn_select2;
    //=========================变量===============================
    List<String> listData = new ArrayList<>();
    private MainAdapter adapter;
    private LinearLayoutManager manager;
    //=========================参数变量===============================
    private String username, password, phone, userId1, age;
    private String noteName2, userId2, content2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_type2);
        initView();
        initAdapter();
    }

    private void initView() {
        tv_total = findViewById(R.id.tv_total);
        recyclerView = findViewById(R.id.recyclerView);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_phone = findViewById(R.id.et_phone);
        et_userId = findViewById(R.id.et_userId);
        et_age = findViewById(R.id.et_age);

        et_noteName2 = findViewById(R.id.et_noteName2);
        et_userId2 = findViewById(R.id.et_userId2);
        et_content2 = findViewById(R.id.et_content2);

        btn_add1 = findViewById(R.id.btn_add1);
        btn_delete1 = findViewById(R.id.btn_delete1);
        btn_update1 = findViewById(R.id.btn_update1);
        btn_select1 = findViewById(R.id.btn_select1);

        btn_add2 = findViewById(R.id.btn_add2);
        btn_delete2 = findViewById(R.id.btn_delete2);
        btn_update2 = findViewById(R.id.btn_update2);
        btn_select2 = findViewById(R.id.btn_select2);


        btn_add1.setOnClickListener(this);
        btn_delete1.setOnClickListener(this);
        btn_update1.setOnClickListener(this);
        btn_select1.setOnClickListener(this);

        btn_add2.setOnClickListener(this);
        btn_delete2.setOnClickListener(this);
        btn_update2.setOnClickListener(this);
        btn_select2.setOnClickListener(this);

    }


    private void initAdapter() {
        adapter = new MainAdapter(this, listData, new MainAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {

            }
        });
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //表User操作
            case R.id.btn_add1://添加1
                getParams1();
                sql_add1();
                break;
            case R.id.btn_update1://更新1
                getParams1();
                sql_update1();
                break;

            case R.id.btn_select1://搜索1

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //查询流程
                        listData.clear();
                        listData = SqlUtils.getAllUser();
                        final int count = SqlUtils.getAllUserBeanNumber();

                        //返回UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //UI操作
                                adapter.setData(listData);
                                tv_total.setText("统计：" + count);
                            }
                        });


                    }
                });
                break;
            case R.id.btn_delete1://删除1
                userId1 = et_userId.getText().toString().trim();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //数据库操作的完整流程：删除流程
                        SqlDB.beginTransaction();
                        try {
                            SqlDB.getInstance().execSQL(SQLString.USER_DELETE, userId1);

                            SqlDB.setTransactionSuccessful();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            SqlDB.endTransaction();
                        }

                        //查询流程
                        listData.clear();
                        listData = SqlUtils.getAllUser();
                        final int count = SqlUtils.getAllUserBeanNumber();

                        //返回UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //UI操作
                                adapter.setData(listData);
                                tv_total.setText("统计：" + count);
                            }
                        });


                    }
                });
                break;

        }

    }


    private void getParams1() {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        userId1 = et_userId.getText().toString().trim();
        age = et_age.getText().toString().trim();
        Log.d("SJY", username + "--" + password + "--" + phone + "--" + userId1 + "--" + age);
    }

    private void getParams2() {
        noteName2 = et_noteName2.getText().toString().trim();
        userId2 = et_userId2.getText().toString().trim();
        content2 = et_content2.getText().toString().trim();
    }

    //=========================数据库操作：异步执行===============================

    private void sql_add1() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //数据库操作的完整流程：插入流程
                SqlDB.beginTransaction();
                long ret = 0;
                try {
                    ret = (long) SqlDB.getInstance().execSQL(SQLString.USER_INSERT, username, password, phone, userId1, age);
                    SqlDB.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    SqlDB.endTransaction();
                }
                //查询流程
                listData.clear();
                listData = SqlUtils.getAllUser();
                final int count = SqlUtils.getAllUserBeanNumber();

                //返回UI
                final long finalRet = ret;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                        adapter.setData(listData);
                        tv_total.setText("统计：" + count);
                    }
                });


            }
        });
    }


    private void sql_update1() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //数据库操作的完整流程：插入流程
                SqlDB.beginTransaction();
                try {
                    SqlDB.getInstance().execSQL(SQLString.USER_UPDATE, username, password, phone, age, userId1);
                    SqlDB.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    SqlDB.endTransaction();
                }

                //查询流程
                listData.clear();
                listData = SqlUtils.getAllUser();
                final int count = SqlUtils.getAllUserBeanNumber();
                //返回UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                        adapter.setData(listData);
                        tv_total.setText("统计：" + count);
                    }
                });
            }
        });
    }
}
