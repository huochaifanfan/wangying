package com.wytianxiatuan.wytianxia.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.OrderDetailBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 订单详情
 */

public class MyOrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private OrderDetailBean bean;
    private Context context;
    private LayoutInflater inflater;
    private SimpleDateFormat sdf;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private static final int ITEM_TYPE3 = 3;
    private OnViewClickListener onViewClickListener;

    public MyOrderDetailAdapter(OrderDetailBean bean, Context context,OnViewClickListener onViewClickListener) {
        this.bean = bean;
        this.context = context;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
        sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    }
    public void reflash(OrderDetailBean bean){
        this.bean = bean;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.order_detail_type1,parent,false));
            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.order_detail_type2,parent,false));
            case ITEM_TYPE3:
                return new Type3ViewHolder(inflater.inflate(R.layout.order_detail_type3,parent ,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            bindType2((Type2ViewHolder) holder,position);
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
    public interface OnViewClickListener{
        void cancelOrder(String orderId);
        void souldOut(String orderId);
        void deleteOrder(String orderId);
        void cancelApply(String orderId);
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.order_id);
        switch (v.getId()){
            case R.id.tv_cancel_order:
                if (onViewClickListener != null) onViewClickListener.cancelOrder(bean.getOrderId());
                break;

            case R.id.tv_sould_out:
                if (onViewClickListener != null) onViewClickListener.souldOut(bean.getOrderId());
                break;

            case R.id.tv_delete:
                if (onViewClickListener != null) onViewClickListener.deleteOrder(bean.getOrderId());
                break;

            case R.id.tv_cancel_apply:
                if (onViewClickListener != null) onViewClickListener.cancelApply(bean.getOrderId());
                break;
        }
    }

    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_orderStatus) TextView tvOrderStatus;
        @BindView(R.id.rel_delviderWay) RelativeLayout relDelividerWay;
        @BindView(R.id.tv_deliverWay) TextView tvDelividerWay;
        @BindView(R.id.tv_deliverNum) TextView tvDeliverNum;
        @BindView(R.id.tv_fuzhi) TextView tvCopy;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_phone) TextView  tvPhone;
        @BindView(R.id.tv_address) TextView tvAddress;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_orderNum) TextView tvOrderNum;
        @BindView(R.id.recyclerView) RecyclerView recyclerViw;
        @BindView(R.id.tv_ticket) TextView tvTicket;
        @BindView(R.id.tv_fei) TextView tvFei;
        @BindView(R.id.tv_cancel_order) TextView tvCancelOrder;
        @BindView(R.id.linear_order_info)LinearLayout linearOrderInfo;
        @BindView(R.id.tv_sould_out) TextView tvSoudOut;
        @BindView(R.id.tv_delete) TextView tvDelete;
        @BindView(R.id.tv_cancel_apply) TextView tvCancelApply;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            recyclerViw.setLayoutManager(new LinearLayoutManager(context));
        }
    }
    public class Type3ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_orderNum) TextView tvOrderNum;
        @BindView(R.id.tv_pay_time) TextView tvPayTime;
        @BindView(R.id.tv_fahuo_time) TextView tvFaHuoTime;
        @BindView(R.id.tv_take_time) TextView tvTakeTime;
        @BindView(R.id.tv_add_time) TextView tvAddTime;
        public Type3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
    private void bindType1(Type1ViewHolder holder){
        if (bean == null) return;
        holder.tvOrderStatus.setText(bean.getOrderStatusText());
        if (bean.getDeliveryWay()!=null&&!"".equals(bean.getDeliveryWay())){
            /**显示快递方式和快递单号*/
            holder.relDelividerWay.setVisibility(View.VISIBLE);
            holder.tvDelividerWay.setText(Html.fromHtml("配送方式：<font color='#999999'>"+bean.getDeliveryWay()+"</font>"));
            holder.tvDeliverNum.setText(Html.fromHtml("快递单号：<font color='#999999'>"+bean.getDeliveryNumber()+"</font>"));
            holder.tvCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    /**创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）*/
                    ClipData clipData = ClipData.newPlainText(null,bean.getDeliveryNumber());
                    manager.setPrimaryClip(clipData);
                    Toast.makeText(context,"复制成功:"+bean.getDeliveryNumber(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            holder.relDelividerWay.setVisibility(View.GONE);
        }
        holder.tvName.setText(bean.getName());
        holder.tvPhone.setText(bean.getPhone());
        holder.tvAddress.setText(bean.getAddress());
    }
    private void bindType2(Type2ViewHolder holder,int position){
        if (bean == null) return;
        holder.tvOrderNum.setText(bean.getShopNames());
        MyOrderListAdapter adapter = new MyOrderListAdapter(bean.getOrderDetailList(),context,null);
        holder.recyclerViw.setAdapter(adapter);
        holder.tvFei.setText(Html.fromHtml("邮费：￥"+bean.getPostAges()+"，合计：<font color='#e64340'>￥"+bean.getTotalPrice()+"</font>"));
        String status = bean.getOrderStatus();
        if ("FINISH".equals(status)){
            /**订单已经完成*/
            holder.linearOrderInfo.setVisibility(View.VISIBLE);
            holder.tvSoudOut.setVisibility(View.VISIBLE);
            holder.tvCancelOrder.setVisibility(View.GONE);
        }else if ("WAIT_PAY".equals(status)){
            /**订单待付款*/
            holder.linearOrderInfo.setVisibility(View.GONE);
        }else if ("WAIT_DELIVER".equals(status)){
            /**订单待发货*/
            holder.linearOrderInfo.setVisibility(View.VISIBLE);
            holder.tvSoudOut.setVisibility(View.VISIBLE);
            holder.tvCancelOrder.setVisibility(View.GONE);
        }else if ("WAIT_TAKE".equals(status)){
            /**订单待收货*/
            holder.linearOrderInfo.setVisibility(View.VISIBLE);
            holder.tvSoudOut.setVisibility(View.VISIBLE);
            holder.tvCancelOrder.setVisibility(View.GONE);
        }else {
            /**其他状态*/
            holder.linearOrderInfo.setVisibility(View.VISIBLE);
            holder.tvCancelOrder.setVisibility(View.GONE);
            holder.tvSoudOut.setVisibility(View.GONE);
        }
        if (bean.isRefund()){
            /**可以申请退款的状态*/
            holder.linearOrderInfo.setVisibility(View.VISIBLE);
            holder.tvSoudOut.setVisibility(View.VISIBLE);
        }else {
            holder.tvSoudOut.setVisibility(View.GONE);
        }
        if ("FINISH".equals(status)||"CLOSED".equals(status)||"REFUND_ED".equals(status)){
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvDelete.setOnClickListener(this);
            holder.tvDelete.setTag(R.id.order_id,position);
        }else {
            holder.tvDelete.setVisibility(View.GONE);
        }
        if ("REFUND_APPLY".equals(status)){
            holder.tvCancelApply.setVisibility(View.VISIBLE);
            holder.tvCancelApply.setOnClickListener(this);
            holder.tvCancelApply.setTag(R.id.order_id,position);
        }else {
            holder.tvCancelApply.setVisibility(View.GONE);
        }
        holder.tvCancelOrder.setOnClickListener(this);
        holder.tvCancelOrder.setTag(R.id.order_id,position);
        holder.tvSoudOut.setOnClickListener(this);
        holder.tvSoudOut.setTag(R.id.order_id,position);
    }
    private void bindType3(Type3ViewHolder holder){
        if (bean == null) return;
        holder.tvOrderNum.setText("订单编号："+bean.getOrderNumber());
        if (bean.getAddTime() !=0){
            holder.tvAddTime.setVisibility(View.VISIBLE);
            holder.tvAddTime.setText("下单时间："+sdf.format(new Date(bean.getAddTime()*1000)));
        }else {
            holder.tvAddTime.setVisibility(View.GONE);
        }
        if (bean.getPayTime() !=0){
            holder.tvPayTime.setVisibility(View.VISIBLE);
            holder.tvPayTime.setText("支付时间："+sdf.format(new Date(bean.getPayTime()*1000)));
        }else{
            holder.tvPayTime.setVisibility(View.GONE);
        }
        if (bean.getDeliverGoodsTime()!=0){
            holder.tvFaHuoTime.setVisibility(View.VISIBLE);
            holder.tvFaHuoTime.setText("发货时间："+sdf.format(new Date(bean.getDeliverGoodsTime()*1000)));
        }else {
            holder.tvFaHuoTime.setVisibility(View.GONE);
        }
        if (bean.getTakeGoodsTime() != 0){
            holder.tvTakeTime.setVisibility(View.VISIBLE);
            holder.tvTakeTime.setText("完成时间："+sdf.format(new Date(bean.getTakeGoodsTime()*1000)));
        }else {
            holder.tvTakeTime.setVisibility(View.GONE);
        }
    }
}
