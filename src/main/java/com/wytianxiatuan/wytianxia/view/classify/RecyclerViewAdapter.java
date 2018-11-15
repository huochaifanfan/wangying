package com.wytianxiatuan.wytianxia.view.classify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wytianxiatuan.wytianxia.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private List<String> data;

    public RecyclerViewAdapter(List<String> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.recycler_iemt,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
