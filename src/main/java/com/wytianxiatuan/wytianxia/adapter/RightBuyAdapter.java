package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.RightBuyBean;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/19.
 */

public class RightBuyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private LayoutInflater inflater;
    private RightBuyBean bean;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private static final int ITEM_TYPE3 = 3;
    private OnViewClickListner onViewClickListner;
    private String faPiaoInfo;

    public RightBuyAdapter(Context context, RightBuyBean bean,OnViewClickListner onViewClickListner,String faPiaoInfo) {
        this.context = context;
        this.bean = bean;
        this.onViewClickListner = onViewClickListner;
        this.faPiaoInfo = faPiaoInfo;
        inflater = LayoutInflater.from(context);
    }
    public void notifyData(RightBuyBean bean){
        this.bean = bean;
        notifyDataSetChanged();
    }
    public void reflash(String faPiaoInfo){
        this.faPiaoInfo = faPiaoInfo;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.right_buy_type1,parent,false));

            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.right_buy_type2,parent,false));

            case ITEM_TYPE3:
                return new Type3ViewHolder(inflater.inflate(R.layout.right_buy_type3,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type2ViewHolder){
            bindType2((Type2ViewHolder) holder);
        }else if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type3ViewHolder){
            bindType3((Type3ViewHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else if (position == 1){
            return ITEM_TYPE2;
        }else if (position == 2){
            return ITEM_TYPE3;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_fapiao:
                if (onViewClickListner != null) onViewClickListner.onItemViewClick();
                break;

            case R.id.rel_address:
                if (onViewClickListner !=null) onViewClickListner.selectAddress();
                break;
        }
    }
    public interface OnViewClickListner{
        void onItemViewClick();
        void otherSay(String note);
        void selectAddress();
    }
    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_phone) TextView tvPhone;
        @BindView(R.id.tv_address) TextView tvAddress;
        @BindView(R.id.linear_name) LinearLayout linearName;
        @BindView(R.id.rel_address) RelativeLayout relAddress;
        @BindView(R.id.tv_addAddress) TextView tvAdd;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setFocusable(false);
        }
    }
    public class Type3ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.linear_fapiao) LinearLayout linearFapiao;
        @BindView(R.id.tv_fapiao) TextView tvFapiao;
        @BindView(R.id.tv_yunfei_amount) TextView tvYuFeiAmount;
        @BindView(R.id.et_other_say) EditText etOtherSay;
        @BindView(R.id.tv_phone) TextView tvPhone;
        public Type3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    private void bindType1(Type1ViewHolder holder){
        if (bean == null) return;
        if (bean.getAddress() != null && !"".equals(bean.getAddress())){
            holder.linearName.setVisibility(View.VISIBLE);
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.tvAdd.setVisibility(View.GONE);
            holder.tvName.setText(bean.getName());
            holder.tvPhone.setText(bean.getPhone());
            holder.tvAddress.setText(bean.getAddress());
        }else {
            /**还没有添加收货地址*/
            holder.linearName.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
            holder.tvAdd.setVisibility(View.VISIBLE);
        }
        holder.relAddress.setOnClickListener(this);
    }
    private void bindType2(Type2ViewHolder holder){
        if (bean == null) return;
        List<RightBuyBean.RightBuyList> data = bean.getData();
        RightBuyType2Adapter adapter = new RightBuyType2Adapter(context,data);
        holder.recyclerView.setAdapter(adapter);
    }
    private void bindType3(final Type3ViewHolder holder){
        if (bean == null) return;
        holder.tvPhone.setText("如要加工定制，请电联"+bean.getServicePhone());
        holder.linearFapiao.setOnClickListener(this);
        holder.tvYuFeiAmount.setText("￥"+bean.getYunFei());
        holder.tvFapiao.setText(faPiaoInfo);
        holder.etOtherSay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(holder.etOtherSay.getText())){
                    if (onViewClickListner != null) onViewClickListner.otherSay(holder.etOtherSay.getText().toString());
                }else {
                    if (onViewClickListner != null) onViewClickListner.otherSay(null);
                }
            }
        });
    }
}
