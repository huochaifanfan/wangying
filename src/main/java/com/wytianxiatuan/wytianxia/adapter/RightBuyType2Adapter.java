package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.RightBuyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/19.
 */

public class RightBuyType2Adapter extends RecyclerView.Adapter<RightBuyType2Adapter.MyViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private List<RightBuyBean.RightBuyList> data;

    public RightBuyType2Adapter(Context context, List<RightBuyBean.RightBuyList> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.right_buy_type2_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RightBuyBean.RightBuyList bean = data.get(position);
        if (position >0){
            if (bean.getShopId().equals(data.get(position-1).getShopId())){
                holder.tvTitle.setVisibility(View.GONE);
            }else {
                holder.tvTitle.setVisibility(View.VISIBLE);
            }
        }
        holder.tvTitle.setText(bean.getShopName());
        Glide.with(context).load(bean.getGoodImage()).centerCrop().into(holder.imageIcon);
        holder.tvGoodsName.setText(bean.getGoodTitle());
        holder.tvGuiGe.setText(bean.getGoodGuiGe());
        holder.tvPrice.setText("￥"+bean.getGoodPrice());
        holder.tvCount.setText("X"+bean.getGoodAmount());
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) ImageView imageIcon;
        @BindView(R.id.tv_goodsName) TextView tvGoodsName;
        @BindView(R.id.tv_guige) TextView tvGuiGe;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_count) TextView tvCount;
        @BindView(R.id.tv_title) TextView tvTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
