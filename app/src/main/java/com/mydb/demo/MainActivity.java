package com.mydb.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mydb.demo.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> listData = new ArrayList<>();
    private MainAdapter adapter;
    private LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        initView();
        initAdapter();
    }
    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);

        listData.add("原生方式1：一个类全部实现");
        listData.add("原生方式2：");
        listData.add("原生方式3：");
    }

    private void initAdapter(){
        adapter = new MainAdapter(this, listData, new MainAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, Type1Activity.class);
                        startActivity(intent);

                        break;
                    case 1:
                        break;
                }
            }
        });
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }



}
