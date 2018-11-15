package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.ClassifyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/1/24.
 */

public class ClassifyLeftAdapter extends RecyclerView.Adapter<ClassifyLeftAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ClassifyBean> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private List<Boolean> isLeftClicks;
    private Context context;

    public ClassifyLeftAdapter(List<ClassifyBean> data , Context context,OnItemClickListener onItemClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
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
    public void update(List<ClassifyBean> data){
        this.data =data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.classify_left_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
        holder.textView.setOnClickListener(this);
        holder.textView.setTag(R.id.classify_id ,position);
        if (isLeftClicks.get(position)){
            holder.textView.setBackgroundResource(R.drawable.classify_left_click);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.main_red));
        }else {
            holder.textView.setBackgroundResource(R.drawable.classify_left_background);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.black_color));
        }
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClickListener{
        void onItemClick(int autoId,int position);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.classify_id);
        for (int i =0;i<isLeftClicks.size();i++){
            isLeftClicks.set(i,false);
        }
        isLeftClicks.set(tag,true);
        notifyDataSetChanged();
        if (onItemClickListener != null && data != null){
            onItemClickListener.onItemClick(data.get(tag).getAutoId(),tag);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
