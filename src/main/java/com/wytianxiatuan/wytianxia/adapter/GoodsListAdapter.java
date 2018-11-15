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
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;
import com.wytianxiatuan.wytianxia.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/28.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder>implements View.OnClickListener{
    private Context context;
    private LayoutInflater inflater;
    private List<GoodsListBean.GoodsList> data;
    private OnItemClickListener onItemClickListener;

    public GoodsListAdapter(Context context, List<GoodsListBean.GoodsList> data,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<GoodsListBean.GoodsList> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.good_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GoodsListBean.GoodsList entit = data.get(position);
        Glide.with(context).load(entit.getImageUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvName.setText(entit.getName());
        holder.tvPrice.setText("￥"+entit.getCurrentPrice());
        holder.relContent.setOnClickListener(this);
        holder.relContent.setTag(R.id.good_list_detail,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClickListener{
        void onItemCLick(int autoId);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.good_list_detail);
        if (data != null && onItemClickListener != null){
            onItemClickListener.onItemCLick(data.get(tag).getAutoId());
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) ImageView imageView;
        @BindView(R.id.rel_content) RelativeLayout relContent;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_price) TextView tvPrice;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
