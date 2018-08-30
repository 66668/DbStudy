package com.mydb.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydb.demo.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyHolder> {
    private Context mContext;
    private List<String> mList;

    public MainAdapter(Context context, List<String> mList, OnItemClick onItemClick) {
        this.mContext = context;
        this.mList = mList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_name, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.tv.setText(mList.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    //

    public void setData(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    //
    private OnItemClick onItemClick;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }
}
