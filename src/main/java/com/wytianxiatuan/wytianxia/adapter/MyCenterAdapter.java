package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MyCenterBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by liuju on 2018/1/16.
 * 个人中心
 */

public class MyCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private LayoutInflater inflater;
    private Context context;
    private MyCenterBean myCenterBean;
    private OnViewClickListener onViewClickListener;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;

    public MyCenterAdapter(Context context, MyCenterBean myCenterBean,OnViewClickListener onViewClickListener) {
        this.context = context;
        this.myCenterBean = myCenterBean;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void reflash(MyCenterBean bean){
        this.myCenterBean = bean;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.my_center_top , parent , false));

            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.my_center_bottom , parent , false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            bindType2((Type2ViewHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else if (position == 1){
            return ITEM_TYPE2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag(R.id.my_center_id);
        if (onViewClickListener != null) onViewClickListener.onViewClick(tag);
    }

    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_back) ImageView imageViewBack;
        @BindView(R.id.image_msg) ImageView imageMsg;
        @BindView(R.id.image_setting) ImageView imageSetting;
        @BindView(R.id.image_icon) CircleImageView circleImageView;
        @BindView(R.id.image_edit)ImageView imageEdit;
        @BindView(R.id.text_name) TextView tvName;
        @BindView(R.id.linear_all_order) LinearLayout linearOrder;
        @BindView(R.id.rel_wait_pay) RelativeLayout relWaitPay;
        @BindView(R.id.rel_delvider) RelativeLayout relDelvider;
        @BindView(R.id.rel_shouhuo) RelativeLayout relShouHuo;
        @BindView(R.id.rel_refund) RelativeLayout relRefund;
        public QBadgeView badgeViewWaitPay,badgeViewWaitDeviler,badgeViewWaitTake,badgeViewWaitFefund;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            badgeViewWaitPay = new QBadgeView(context);
            badgeViewWaitDeviler = new QBadgeView(context);
            badgeViewWaitTake = new QBadgeView(context);
            badgeViewWaitFefund = new QBadgeView(context);
            setBadgeViewBackground(badgeViewWaitPay,relWaitPay);
            setBadgeViewBackground(badgeViewWaitDeviler,relDelvider);
            setBadgeViewBackground(badgeViewWaitTake,relShouHuo);
            setBadgeViewBackground(badgeViewWaitFefund,relRefund);
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.linear_collect) LinearLayout linearCollect;
        @BindView(R.id.linear_ticket) LinearLayout linearTicket;
        @BindView(R.id.linear_address) LinearLayout linearAddress;
        @BindView(R.id.linear_shanghu) LinearLayout linearShangHu;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public interface OnViewClickListener{
        void onViewClick(int position);
    }
    private void bindType1(Type1ViewHolder holder){
        holder.imageViewBack.setOnClickListener(this);
        holder.imageViewBack.setTag(R.id.my_center_id ,0);
        holder.imageMsg.setOnClickListener(this);
        holder.imageMsg.setTag(R.id.my_center_id , 1);
        holder.imageSetting.setOnClickListener(this);
        holder.imageSetting.setTag(R.id.my_center_id , 2);
        holder.imageEdit.setOnClickListener(this);
        holder.imageEdit.setTag(R.id.my_center_id ,3);
        holder.linearOrder.setOnClickListener(this);
        holder.linearOrder.setTag(R.id.my_center_id ,5);
        holder.relWaitPay.setOnClickListener(this);
        holder.relWaitPay.setTag(R.id.my_center_id , 6);
        holder.relDelvider.setOnClickListener(this);
        holder.relDelvider.setTag(R.id.my_center_id ,7);
        holder.relShouHuo.setOnClickListener(this);
        holder.relShouHuo.setTag(R.id.my_center_id , 8);
        holder.relRefund.setOnClickListener(this);
        holder.relRefund.setTag(R.id.my_center_id ,9);

        Glide.with(context).load(myCenterBean.getPictureUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.circleImageView);
        holder.tvName.setText(myCenterBean.getNickName());
        if (myCenterBean.getWaitPayCount()>0){
            holder.badgeViewWaitPay.hide(false);
            holder.badgeViewWaitPay.setBadgeNumber(myCenterBean.getWaitPayCount());
        }else {
            holder.badgeViewWaitPay.hide(true);
        }
        if (myCenterBean.getWaitDeliverCount()>0){
            holder.badgeViewWaitDeviler.hide(false);
            holder.badgeViewWaitDeviler.setBadgeNumber(myCenterBean.getWaitDeliverCount());
        }else {
            holder.badgeViewWaitDeviler.hide(true);
        }
        if (myCenterBean.getWaitTakeCount()>0){
            holder.badgeViewWaitTake.hide(false);
            holder.badgeViewWaitTake.setBadgeNumber(myCenterBean.getWaitTakeCount());
        }else {
            holder.badgeViewWaitTake.hide(true);
        }
        if (myCenterBean.getRefundCount()>0){
            holder.badgeViewWaitFefund.hide(false);
            holder.badgeViewWaitFefund.setBadgeNumber(myCenterBean.getRefundCount());
        }else {
            holder.badgeViewWaitFefund.hide(true);
        }
    }
    private void setBadgeViewBackground(QBadgeView qBadgeView,View view){
        qBadgeView.bindTarget(view);
        qBadgeView.setBadgeTextSize(10,true);
        qBadgeView.setBadgeTextColor(ContextCompat.getColor(context,R.color.white_color));
        qBadgeView.setBadgeBackgroundColor(ContextCompat.getColor(context,R.color.main_red));
        qBadgeView.setBadgePadding(2,true);
        qBadgeView.setBadgeGravity(Gravity.END|Gravity.TOP);
        qBadgeView.setGravityOffset(20,12,true);
    }
    private void bindType2(Type2ViewHolder holder){
        holder.linearCollect.setOnClickListener(this);
        holder.linearCollect.setTag(R.id.my_center_id , 10);
        holder.linearTicket.setOnClickListener(this);
        holder.linearTicket.setTag(R.id.my_center_id,11);
        holder.linearAddress.setOnClickListener(this);
        holder.linearAddress.setTag(R.id.my_center_id ,12);
        holder.linearShangHu.setOnClickListener(this);
        holder.linearShangHu.setTag(R.id.my_center_id , 13);
    }
}
