package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
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
 * Created by Administrator on 2018/1/30 0030.
 */

public class TopSelectorAdapter extends RecyclerView.Adapter<TopSelectorAdapter.MyViewHolder> implements View.OnClickListener{
    private List<GoodsListBean.Category> data;
    private LayoutInflater inflater;
    private List<Boolean> isLeftClicks;
    private OnTopClickListener onTopClickListener;
    private Context context;

    public TopSelectorAdapter(List<GoodsListBean.Category> data, Context context,OnTopClickListener onTopClickListener) {
        this.data = data;
        this.onTopClickListener = onTopClickListener;
        this.context = context;
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
        return new MyViewHolder(inflater.inflate(R.layout.page_selector_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
        holder.textView.setOnClickListener(this);
        holder.textView.setTag(R.id.top_id ,position);
        if (isLeftClicks.get(position)){
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.main_red));
        }else {
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.black_color));
        }
    }
    public void update(int position){
        for (int i =0;i<isLeftClicks.size();i++){
            isLeftClicks.set(i,false);
        }
        isLeftClicks.set(position,true);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnTopClickListener{
        void onTopClick(String autoId);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.top_id);
        for (int i =0;i<isLeftClicks.size();i++){
            isLeftClicks.set(i,false);
        }
        isLeftClicks.set(tag,true);
        notifyDataSetChanged();
        if (onTopClickListener != null && data != null) onTopClickListener.onTopClick(data.get(tag).getAutoId());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
