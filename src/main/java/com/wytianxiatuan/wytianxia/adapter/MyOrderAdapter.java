package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 我的订单
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> implements
        MyOrderListAdapter.OnOrderClickListener,View.OnClickListener{
    private List<MyOrderBean.Orders> data;
    private Context context;
    private LayoutInflater inflater;
    private onItemClickListener onItemClickListener;
    private int redColor;
    private int greyColor;

    public MyOrderAdapter(List<MyOrderBean.Orders> data, Context context ,onItemClickListener onItemClickListener) {
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        redColor = ContextCompat.getColor(context,R.color.main_red);
        greyColor = ContextCompat.getColor(context,R.color.main_grey);
        inflater = LayoutInflater.from(context);
    }
    public void reflashData(List<MyOrderBean.Orders> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.my_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyOrderBean.Orders bean = data.get(position);
        holder.tvOrderNumber.setText("订单编号："+bean.getOrderNumber());
        holder.tvOrderStatus.setText(bean.getStatusText());
        holder.tvFei.setText(Html.fromHtml("邮费：￥"+bean.getExpressAmount()+"，合计：<font color='#e64340'>￥"+bean.getTotalPrice()+"</font>"));
        List<MyOrderBean.OrderList> data = bean.getOrderLists();
        MyOrderListAdapter adapter = new MyOrderListAdapter(data,context,this);
        holder.recyclerView.setAdapter(adapter);
        String status = bean.getOrderStatus();
        if ("FINISH".equals(status)){
            /**订单已经完成*/
            holder.linearKefu.setVisibility(View.GONE);
        }else if ("WAIT_PAY".equals(status)){
            /**订单待付款*/
            holder.linearKefu.setVisibility(View.VISIBLE);
            holder.tvOrderStatus.setTextColor(redColor);
            holder.tvCancelOrder.setVisibility(View.VISIBLE);
            holder.tvPayOrder.setVisibility(View.VISIBLE);
            holder.tvApplyCancel.setVisibility(View.GONE);
            holder.tvSure.setVisibility(View.GONE);
        }else if ("WAIT_DELIVER".equals(status)){
            /**订单待发货*/
            holder.linearKefu.setVisibility(View.GONE);
        }else if ("WAIT_TAKE".equals(status)){
            /**订单待收货*/
            holder.linearKefu.setVisibility(View.VISIBLE);
            holder.tvOrderStatus.setTextColor(redColor);
            holder.tvCancelOrder.setVisibility(View.GONE);
            holder.tvPayOrder.setVisibility(View.GONE);
            holder.tvApplyCancel.setVisibility(View.GONE);
            holder.tvSure.setVisibility(View.VISIBLE);
        }else if ("CLOSED".equals(status)){
            /**交易关闭*/
            holder.linearKefu.setVisibility(View.GONE);
        }else if ("REFUND_APPLY".equals(status)){
           /**退款申请*/
            holder.linearKefu.setVisibility(View.VISIBLE);
            holder.tvOrderStatus.setTextColor(redColor);
            holder.tvCancelOrder.setVisibility(View.GONE);
            holder.tvPayOrder.setVisibility(View.GONE);
            holder.tvSure.setVisibility(View.GONE);
            holder.tvApplyCancel.setVisibility(View.VISIBLE);
        }else {
            /**其他状态*/
            holder.linearKefu.setVisibility(View.GONE);
            holder.tvOrderStatus.setTextColor(redColor);
        }
        holder.tvCancelOrder.setOnClickListener(this);
        holder.tvCancelOrder.setTag(R.id.order_id,position);
        holder.tvPayOrder.setOnClickListener(this);
        holder.tvPayOrder.setTag(R.id.order_id,position);
        holder.tvApplyCancel.setOnClickListener(this);
        holder.tvApplyCancel.setTag(R.id.order_id ,position);
        holder.tvSure.setOnClickListener(this);
        holder.tvSure.setTag(R.id.order_id,position);
    }

    @Override
    public void onOrderClick(String orderId) {
        if (onItemClickListener != null) onItemClickListener.onItemClick(orderId);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.order_id);
        switch (v.getId()){
            case R.id.tv_cancel_order:
                if (onItemClickListener!= null) onItemClickListener.cancelOrder(data.get(position).getOrderId());
                break;

            case R.id.tv_cancel_apply:
                if (onItemClickListener != null) onItemClickListener.cancelApply(data.get(position).getOrderId());
                break;

            case R.id.tv_sure:
                if (onItemClickListener != null) onItemClickListener.sureTakeGood(data.get(position).getOrderId());
                break;

            case R.id.tv_pay_order:
                /**支付订单*/
                if (onItemClickListener != null) onItemClickListener.paySure(data.get(position).getOrderNumber());
                break;
        }
    }

    public interface onItemClickListener{
        void onItemClick(String orderId);
        void cancelOrder(String orderId);
        void cancelApply(String orderId);
        void sureTakeGood(String goodId);
        void paySure(String orderNumber);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_orderNum) TextView tvOrderNumber;
        @BindView(R.id.order_status) TextView tvOrderStatus;
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        @BindView(R.id.tv_fei) TextView tvFei;
        @BindView(R.id.tv_cancel_order) TextView tvCancelOrder;
        @BindView(R.id.tv_pay_order) TextView tvPayOrder;
        @BindView(R.id.linear_kefu) LinearLayout linearKefu;
        @BindView(R.id.tv_cancel_apply) TextView tvApplyCancel;
        @BindView(R.id.tv_sure) TextView tvSure;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
