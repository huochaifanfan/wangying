package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CpuUsageInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/1/28.
 */

public class TopTextAdapter extends RecyclerView.Adapter<TopTextAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater inflater;
    private List<GoodsListBean.OrderList> data;
    private OnViewClickListener onViewClickListener;
    private Context context;
    private List<Boolean> isLeftClicks;
    public TopTextAdapter(List<GoodsListBean.OrderList> data , Context context ,OnViewClickListener onViewClickListener) {
        this.data = data;
        this.context = context;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
        isLeftClicks = new ArrayList<>();
        for (int i=0;i<data.size();i++){
            if (i == 0){
                isLeftClicks.add(true);
            }else {
                isLeftClicks.add(false);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.top_text_background,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
        holder.textView.setOnClickListener(this);
        holder.textView.setTag(position);
        if (isLeftClicks.get(position)){
            holder.textView.setBackgroundResource(R.drawable.top_bar_text_backgrouond);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.main_red));
        }else {
            holder.textView.setBackgroundResource(0);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.black_color));
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        for (int i =0;i<isLeftClicks.size();i++){
            isLeftClicks.set(i,false);
        }
        isLeftClicks.set(position,true);
        notifyDataSetChanged();
        if (data != null && onViewClickListener != null) onViewClickListener.onItemViewClick(data.get(position).getOrderId());
    }

    public interface OnViewClickListener{
        void onItemViewClick(String orderId);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.toptext);
        }
    }
}
