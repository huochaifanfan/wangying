package com.wytianxiatuan.wytianxia.view.my;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MyOrderDetailAdapter;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;
import com.wytianxiatuan.wytianxia.bean.OrderDetailBean;
import com.wytianxiatuan.wytianxia.bean.PaySureBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.base.PermissionsResultListener;
import com.wytianxiatuan.wytianxia.view.main.MainActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 订单详情
 */

public class MyOrderDetailActivity extends BaseActivity implements IMainView,MyOrderDetailAdapter.OnViewClickListener,PermissionsResultListener{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.tv_total_price) TextView tvTotalPrice;
    @BindView(R.id.tv_orderAmount) TextView tvOrderAmount;
    @BindView(R.id.tv_sure) TextView tvSure;
    private LinearLayoutManager layoutManager;
    private MyOrderDetailAdapter adapter;
    private String orderId;
    private MainPresenter presenter;
    private List<MyOrderBean.OrderList> data;
    private String url;
    private String totalPrice;
    private PaySuccessReceiver paySuccessReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        orderId = getIntent().getStringExtra("orderId");
        tvTitle.setText("订单详情");
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        presenter = new MainPresenter(getApplicationContext(),this);
        IntentFilter intentFilter = new IntentFilter("com.wytianxia.paySuccess");
        paySuccessReceiver = new PaySuccessReceiver();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(paySuccessReceiver,intentFilter);
        getData();
    }
    private void getData(){
        url = Constants.orderDetail+"?id="+orderId;
        cancelable = presenter.orderDetail();
    }
    public class PaySuccessReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getToastdialog(MyOrderDetailActivity.this,"支付成功！");
            getData();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(paySuccessReceiver);
        super.onDestroy();
    }

    @OnClick({R.id.imageView_back,R.id.tv_sure})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.tv_sure:
                /**评价或者支付订单*/
                if ("FINISH".equals(status)){
                    Intent intent = new Intent(this , QualityActivity.class);
                    intent.putExtra("data", (Serializable) data);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }else if ("WAIT_PAY".equals(status)){
                    /**订单支付*/
                    url = Constants.orderManagePaySure;
                    if (presenter != null)presenter = new MainPresenter(getApplicationContext(),this);
                    showLoading(this);
                    cancelable = presenter.rightBuy(orderNumber);
                }
                break;
        }
    }

    @Override
    public String getUrl() {
        return url;
    }
    private String status;
    private String orderNumber;
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof OrderDetailBean){
                OrderDetailBean bean = (OrderDetailBean) args[0];
                data = bean.getOrderDetailList();
                if (adapter == null){
                    adapter = new MyOrderDetailAdapter(bean,this,this);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.reflash(bean);
                }
                if (bean != null){
                    totalPrice = bean.getTotalOrderPrice();
                    tvTotalPrice.setText("￥"+totalPrice);
                    orderNumber = bean.getOrderNumber();
                    tvOrderAmount.setText("共"+bean.getTotalOrderAmount()+"件商品");
                    status = bean.getOrderStatus();
                    if ("FINISH".equals(status)&&"0".equals(bean.getIsComment())){
                        tvSure.setText("去评价");
                    }else if ("WAIT_PAY".equals(status)){
                        tvSure.setText("去付款");
                    }else {
                        tvSure.setVisibility(View.GONE);
                    }
                }
            }else if (args[0] instanceof String && args[0].equals("cancel") && args[1] instanceof Integer){
                int flag = (int) args[1];
                String text="";
                if (flag == 0){
                    text = "订单取消成功";
                    getToastdialog(this,text);
                    getData();
                }else if (flag ==1){
                    text = "订单删除成功";
                    getToastdialog(this,text);
                    this.finish();
                }else if (flag == 2){
                    text = "取消申请退款成功";
                    getToastdialog(this,text);
                    getData();
                }
            }else if (args[0] instanceof PaySureBean){
                PaySureBean bean = (PaySureBean) args[0];
                payBuyWeiChat(bean);
            }
        }
        hideLoading();
    }

    /**
     * 订单支付
     * @param bean
     */
    private void payBuyWeiChat(PaySureBean bean) {
        if (bean == null)return;
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepayid();
        request.packageValue = bean.getPackageType();
        request.nonceStr = bean.getNoncestr();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        if (MainApplication.weiChatApi != null) {
            MainApplication.weiChatApi.sendReq(request);
        }
    }

    @Override
    public void cancelOrder(String orderId) {
        if (presenter != null){
            url = Constants.cancelOrder;
            cancelable = presenter.cancelOrder(orderId,0);
        }
    }

    /**
     * 申请售后
     * @param orderId
     */
    @Override
    public void souldOut(String orderId) {
        Intent intent = new Intent(this,CancelOrderActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("price",totalPrice);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300){
            if (data != null && "success".equals(data.getStringExtra("flag"))){
                /**取消申请成功*/
                getData();
            }
        }
    }
    /**
     * 删除订单
     * @param orderId
     */
    @Override
    public void deleteOrder(final String orderId) {
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0, UiUtils.dipToPx(this,295));
        TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
        textContent.setText("您确定要删除该订单吗？");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.cancel();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = Constants.deleteOrder;
                cancelable = presenter.cancelOrder(orderId,1);
                if (dialog != null) dialog.cancel();
            }
        });
    }
    /**
     * 取消退款申请
     * @param orderId
     */
    @Override
    public void cancelApply(final String orderId) {
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0, UiUtils.dipToPx(this,295));
        TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
        textContent.setText("您确定要取消退款申请吗？");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.cancel();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    url = Constants.cancelApply;
                    cancelable = presenter.cancelOrder(orderId, 2);
                }
                if (dialog != null) dialog.cancel();
            }
        });
    }

    @Override
    public void onPermissionGranted() {}

    @Override
    public void onPermissionDenied() {
        getToastdialog(this,"拨打电话需要相关权限，请检查权限设置");
    }
}
