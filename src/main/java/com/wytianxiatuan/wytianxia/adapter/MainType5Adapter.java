package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/20.
 */

public class MainType5Adapter extends RecyclerView.Adapter<MainType5Adapter.MyViewHolder> implements View.OnClickListener{
    private List<MainBean.MainShops> data;
    private Context context;
    private LayoutInflater inflater;
    private OnShopClickListener onShopClickListener;

    public MainType5Adapter(List<MainBean.MainShops> data, Context context,OnShopClickListener onShopClickListener) {
        this.data = data;
        this.context = context;
        this.onShopClickListener = onShopClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.main_type5_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainBean.MainShops entity = data.get(position);
        Glide.with(context).load(entity.getLogo()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvTitle.setText(entity.getName());
        holder.linearLayout.setOnClickListener(this);
        holder.linearLayout.setTag(R.id.main_page_id , position);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnShopClickListener{
        void onShopClick(int autoId,String name,String shopUrl);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.main_page_id);
        if (onShopClickListener != null) {
            onShopClickListener.onShopClick(data.get(tag).getUserId(),data.get(tag).getName(),data.get(tag).getLogo());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) ImageView imageView;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.linear_content) LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
