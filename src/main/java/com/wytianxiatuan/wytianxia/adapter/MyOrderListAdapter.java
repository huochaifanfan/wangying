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
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder> implements View.OnClickListener{
    private List<MyOrderBean.OrderList> data;
    private Context context;
    private LayoutInflater inflater;
    private OnOrderClickListener onOrderClickListener;

    public MyOrderListAdapter(List<MyOrderBean.OrderList> data, Context context,OnOrderClickListener onOrderClickListener) {
        this.data = data;
        this.context = context;
        this.onOrderClickListener = onOrderClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.my_order_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyOrderBean.OrderList bean = data.get(position);
        Glide.with(context).load(bean.getOrderImage()).centerCrop().into(holder.imageIcon);
        holder.tvGoodName.setText(bean.getOrderTitle());
        holder.tvGuiGe.setText(bean.getOrderGuiGe());
        holder.tvPrice.setText("￥"+bean.getOrderPrice());
        holder.tvAmount.setText("X"+bean.getOrderAmount());
        holder.relItem.setOnClickListener(this);
        holder.relItem.setTag(R.id.order_manager,bean.getOrderId());
    }
    public interface OnOrderClickListener{
        void onOrderClick(String orderId);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public void onClick(View v) {
        String orderId = (String) v.getTag(R.id.order_manager);
        if (onOrderClickListener != null) onOrderClickListener.onOrderClick(orderId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) ImageView imageIcon;
        @BindView(R.id.tv_goodsName) TextView tvGoodName;
        @BindView(R.id.tv_guige) TextView tvGuiGe;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_amount) TextView tvAmount;
        @BindView(R.id.order_layout) RelativeLayout relItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
