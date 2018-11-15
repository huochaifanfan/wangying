package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
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
import com.wytianxiatuan.wytianxia.bean.MyShopCollectBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/18.
 * 我的店铺收藏
 */

public class MyShopCollectAdapter extends RecyclerView.Adapter<MyShopCollectAdapter.MyViewHolder> implements View.OnClickListener{
    private List<MyShopCollectBean> data;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public MyShopCollectAdapter(List<MyShopCollectBean> data, Context context,OnItemClickListener onItemClickListener) {
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<MyShopCollectBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.my_shop_collect_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyShopCollectBean bean = data.get(position);
        Glide.with(context).load(bean.getShopImage()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvDes.setText(bean.getShopDescription());
        holder.tvShopNames.setText(bean.getShopName());
        holder.relativeLayout.setOnClickListener(this);
        holder.relativeLayout.setTag(R.id.shop_id ,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.shop_id);
        if (onItemClickListener != null && data != null) {
            onItemClickListener.onItemClick(data.get(tag).getShopId(),data.get(tag).getShopName(),data.get(tag).getShopImage());
        }
    }
    public interface OnItemClickListener{
        void onItemClick(String shopId,String shopName,String shopUrl);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rel_content) RelativeLayout relativeLayout;
        @BindView(R.id.image_icon) ImageView imageView;
        @BindView(R.id.tv_shopName) TextView tvShopNames;
        @BindView(R.id.tv_des) TextView tvDes;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
