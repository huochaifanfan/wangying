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
import com.wytianxiatuan.wytianxia.bean.MyGoodsCollectBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/18.
 * 商品收藏
 */

public class MyGoodsCollectAdapter extends RecyclerView.Adapter<MyGoodsCollectAdapter.MyViewHolder> implements View.OnClickListener{
    private List<MyGoodsCollectBean> data;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public MyGoodsCollectAdapter(List<MyGoodsCollectBean> data, Context context,OnItemClickListener onItemClickListener) {
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<MyGoodsCollectBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.my_goods_collect_item, parent ,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyGoodsCollectBean bean = data.get(position);
        Glide.with(context).load(bean.getGoodImage()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvName.setText(bean.getGoodName());
        holder.tvPrice.setText("￥"+bean.getPrice());
        holder.relativeLayout.setOnClickListener(this);
        holder.relativeLayout.setTag(R.id.good_id,bean.getAutoId());
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnItemClickListener{
        void onItemClick(int autoId);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.good_id );
        if (onItemClickListener != null) onItemClickListener.onItemClick(tag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rel_content) RelativeLayout relativeLayout;
        @BindView(R.id.image_icon) ImageView imageView;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_price) TextView tvPrice;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
