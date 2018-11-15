package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater inflater;
    private String[] data;
    private SortClickListener sortClickListener;

    public SortAdapter(String[] data , Context context,SortClickListener sortClickListener) {
        this.data = data;
        this.sortClickListener = sortClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.sort_item ,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data[position]);
        holder.textView.setOnClickListener(this);
        holder.textView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    public interface SortClickListener{
        void onSortClick(int postion);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (sortClickListener != null) sortClickListener.onSortClick(tag);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_sort);
        }
    }
}
