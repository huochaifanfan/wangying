package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/16.
 * 选择和管理收货地址
 */

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.MyViewHolder> implements View.OnClickListener{
    private List<AddressBean> data;
    private LayoutInflater inflater;
    private OnViewClickListener onViewClickListener;

    public SelectAddressAdapter(List<AddressBean> data, Context context,OnViewClickListener onViewClickListener) {
        this.data = data;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<AddressBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.select_address_item , parent , false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddressBean bean = data.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvPhone.setText(makePhone(bean.getTelephone()));
        holder.tvAddress.setText(bean.getArea()+" "+bean.getDetail());
        holder.imageEdit.setOnClickListener(this);
        holder.imageEdit.setTag(R.id.address_id,position);
        holder.relItem.setOnClickListener(this);
        holder.relItem.setTag(R.id.address_id ,position);
    }
    public interface OnViewClickListener{
        void onViewClick(int position);
        void onItemClick(int position);
    }
    private String makePhone(String phone){
        String str = phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());
        return str;
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.address_id);
        switch (v.getId()){
            case R.id.image_edit:
                if (onViewClickListener != null) onViewClickListener.onViewClick(tag);
                break;
            case R.id.rel_item:
                if (onViewClickListener != null) onViewClickListener.onItemClick(tag);
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_phone) TextView tvPhone;
        @BindView(R.id.tv_address) TextView tvAddress;
        @BindView(R.id.image_edit) ImageView imageEdit;
        @BindView(R.id.rel_item) RelativeLayout  relItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
