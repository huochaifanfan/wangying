package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class DetailDialogContentAdapter extends RecyclerView.Adapter<DetailDialogContentAdapter.MyViewHolder>
        implements View.OnClickListener{
    private LayoutInflater inflater;
    private List<GoodDetailBean.SpecValue> data;
    private List<Boolean> isLeftClicks;
    private Context context;
    private String[] clickIds;
    private OnDialogClickListener onDialogClickListener;
    private int size;
    private int clickPosition;

    public DetailDialogContentAdapter(List<GoodDetailBean.SpecValue> data , Context context,OnDialogClickListener onDialogClickListener,int size,int clickPosition) {
        this.data = data;
        this.context = context;
        this.onDialogClickListener = onDialogClickListener;
        this.size = size;
        this.clickPosition = clickPosition;
        clickIds = new String[size];
        inflater = LayoutInflater.from(context);
        isLeftClicks = new ArrayList<>();
        for (int i=0;i<data.size();i++){
            isLeftClicks.add(false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.detail_dialog_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
        holder.textView.setOnClickListener(this);
        holder.textView.setTag(R.id.detail_dialog,position);
        if (isLeftClicks.get(position)){
            holder.textView.setBackgroundResource(R.drawable.detail_dialog_red);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.white_color));
//            clickIds[clickPosition] = data.get(position).getId();
        }else {
            holder.textView.setBackgroundResource(R.drawable.detail_dialog_background);
            holder.textView.setTextColor(Color.parseColor("#a7a7a7"));
        }
    }
    public interface OnDialogClickListener{
        void onDialogClick(String clickIds,int size,int position);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        for (int i =0;i<isLeftClicks.size();i++){
            isLeftClicks.set(i,false);
        }
        int position = (Integer) v.getTag(R.id.detail_dialog);
        isLeftClicks.set(position,true);
        notifyDataSetChanged();
        if (onDialogClickListener != null){
            if (onDialogClickListener != null) onDialogClickListener.onDialogClick(data.get(position).getId(),size,clickPosition);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
