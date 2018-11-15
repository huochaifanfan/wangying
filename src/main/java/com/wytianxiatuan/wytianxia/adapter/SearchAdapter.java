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
 * Created by Administrator on 2018/1/22 0022.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>implements View.OnClickListener{
    private List<String> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public SearchAdapter(List<String> data, Context context,OnItemClickListener onItemClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.search_item,parent,false));
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (onItemClickListener != null) onItemClickListener.onItemClick(tag);
    }

    public interface OnItemClickListener{
        void onItemClick(String content);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvContent.setText(data.get(position));
        holder.tvContent.setOnClickListener(this);
        holder.tvContent.setTag(data.get(position));
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
