package com.mydb.demo;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mydb.demo.adapter.MainAdapter;
import com.mydb.demo.type1.PersonBean;
import com.mydb.demo.type1.Type1SqliteImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 原生方式1：一个类全部实现
 */
public class Type1Activity extends AppCompatActivity implements View.OnClickListener {
    //=========================控件===============================
    RecyclerView recyclerView;
    EditText et_name, et_sex, et_old, et_place, et_search;
    Button btn_add, btn_update, btn_search, btn_delete, btn_search_all;
    //=========================变量===============================
    List<String> listData = new ArrayList<>();
    private MainAdapter adapter;
    private LinearLayoutManager manager;
    private Type1SqliteImpl sqlite;
    //=========================参数变量===============================
    private String name, sex, old, place, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_type1);
        initView();
        initAdapter();
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_sex = findViewById(R.id.et_sex);
        et_old = findViewById(R.id.et_old);
        et_place = findViewById(R.id.et_place);
        et_search = findViewById(R.id.et_search);

        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_search = findViewById(R.id.btn_search);
        btn_delete = findViewById(R.id.btn_delete);
        btn_search_all = findViewById(R.id.btn_search_all);

        recyclerView = findViewById(R.id.recyclerView);

        btn_add.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_search_all.setOnClickListener(this);

        //初始化数据库
        sqlite = new Type1SqliteImpl(this);
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
            case R.id.btn_add://添加
                getParams();
                sql_add();

                listData.clear();
                listData = sql_searchAll();

                adapter.setData(listData);
                break;
            case R.id.btn_update://更新
                getParams();
                sql_update();

                listData.clear();
                listData = sql_searchAll();

                adapter.setData(listData);
                break;
            case R.id.btn_search://搜索
                PersonBean bean = sql_search(et_search.getText().toString().trim());
                //拼接数据
                StringBuilder builder = new StringBuilder();
                builder.append(bean.getName() + " ");
                builder.append(bean.getSex() + " ");
                builder.append(bean.getOld() + " ");
                builder.append(bean.getPlace());

                listData.clear();
                listData.add(builder.toString());
                adapter.setData(listData);
                break;
            case R.id.btn_search_all://搜索所有
                listData.clear();
                listData = sql_searchAll();

                adapter.setData(listData);
                break;

            case R.id.btn_delete://删除
                sql_delete(et_search.getText().toString().trim());

                listData.clear();
                listData = sql_searchAll();

                adapter.setData(listData);
                break;

        }

    }

    private void getParams() {
        name = et_name.getText().toString().trim();
        sex = et_sex.getText().toString().trim();
        old = et_old.getText().toString().trim();
        place = et_place.getText().toString().trim();
    }

    //=========================数据库操作===============================
    private void sql_add() {
        sqlite.addOne(name, sex, old, place);
    }

    private void sql_update() {
        sqlite.updateOne(name, sex, old, place);
    }

    private PersonBean sql_search(String name) {
        return sqlite.searchOne(name);
    }

    private void sql_delete(String name) {
        sqlite.deleteOne(name);

    }

    private List<String> sql_searchAll() {
        return sqlite.searchAll2();
    }

}
