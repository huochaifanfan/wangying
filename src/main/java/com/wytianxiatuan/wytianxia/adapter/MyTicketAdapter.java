package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MyTicketBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<MyTicketBean> data;
    private int redColor;
    private int greyColor;

    public MyTicketAdapter(Context context, List<MyTicketBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        redColor = Color.parseColor("#ff0000");
        greyColor = Color.parseColor("#5c000000");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.my_ticket_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyTicketBean bean = data.get(position);
        if ("AVAILABLE".equals(bean.getTicketStatus())){
            /**未过期*/
            holder.relContent.setBackgroundResource(R.drawable.ticket_selected);
            holder.tvSign.setTextColor(redColor);
            holder.tvAmount.setTextColor(redColor);
            holder.tvType.setTextColor(Color.parseColor("#ff0500"));
            holder.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.black_color));
            holder.tvCondition.setTextColor(ContextCompat.getColor(context,R.color.main_grey));
            holder.tvTime.setTextColor(ContextCompat.getColor(context,R.color.main_grey));
            holder.tvStatus.setVisibility(View.GONE);
            holder.tvTime.setText(bean.getEndTime()+"过期");
        }else {
            holder.relContent.setBackgroundResource(R.drawable.ticket_dealline_time);
            holder.tvSign.setTextColor(greyColor);
            holder.tvAmount.setTextColor(greyColor);
            holder.tvType.setTextColor(greyColor);
            holder.tvTitle.setTextColor(Color.parseColor("#5c000000"));
            holder.tvCondition.setTextColor(Color.parseColor("#5c999999"));
            holder.tvTime.setTextColor(Color.parseColor("#5c999999"));
            holder.tvSign.setVisibility(View.VISIBLE);
            holder.tvTime.setText("有效期："+bean.getStartTime()+"至"+bean.getEndTime());
        }
        holder.tvAmount.setText(bean.getAmount());
        holder.tvType.setText("优惠券");
        holder.tvTitle.setText(bean.getTitle());
        holder.tvCondition.setText("全场满"+bean.getCondition()+"元可用");
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rel_content) RelativeLayout relContent;
        @BindView(R.id.tv_amount)TextView tvAmount;
        @BindView(R.id.tv_type) TextView tvType;
        @BindView(R.id.text_title) TextView tvTitle;
        @BindView(R.id.text_condition) TextView tvCondition;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_status) TextView tvStatus;
        @BindView(R.id.tv_sign) TextView tvSign;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
