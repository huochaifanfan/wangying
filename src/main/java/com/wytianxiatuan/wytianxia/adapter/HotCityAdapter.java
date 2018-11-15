package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wytianxiatuan.wytianxia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class HotCityAdapter extends RecyclerView.Adapter<HotCityAdapter.MyViewHolder> {
    private List<String> data;
    private LayoutInflater inflater;

    public HotCityAdapter(List<String> data , Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.hot_city_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvContent.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
