package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.DynaMicBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater inflater;
    private Context context;
    private List<DynaMicBean> data;
    private OnItemClickListener onItemClickListener;
    public DynamicAdapter(Context context, List<DynaMicBean> data ,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<DynaMicBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.dynamic_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DynaMicBean bean = data.get(position);
        Glide.with(context).load(bean.getImageIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageIcon);
        holder.textTitle.setText(bean.getTitle());
        holder.textContent.setText(bean.getContent());
        holder.textTime.setText(bean.getTime());
        holder.relContent.setOnClickListener(this);
        holder.relContent.setTag(R.id.dynamic_id , position);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClickListener{
        void onItemClick(int itemId);
    }
    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag(R.id.dynamic_id);
        if (data != null && onItemClickListener != null){
            onItemClickListener.onItemClick(data.get(tag).getId());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) ImageView imageIcon;
        @BindView(R.id.text_title) TextView textTitle;
        @BindView(R.id.text_content) TextView textContent;
        @BindView(R.id.text_time) TextView textTime;
        @BindView(R.id.rel_content) RelativeLayout relContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
