package com.wytianxiatuan.wytianxia.view.shop;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.RightBuyAdapter;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.JsonParamsBean;
import com.wytianxiatuan.wytianxia.bean.PaySureBean;
import com.wytianxiatuan.wytianxia.bean.RightBuyBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.my.MyOrderActivity;
import com.wytianxiatuan.wytianxia.view.my.SelectAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/19.
 * 立即结算
 */

public class RightBuyActivity extends BaseActivity implements IMainView,RightBuyAdapter.OnViewClickListner{
    @BindView(R.id.tv_top_title) TextView tvTopTitle;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tv_price_total) TextView tvTotalPrice;
    @BindView(R.id.goodsAmount) TextView tvGoodsAmount;
    @BindView(R.id.tv_commit) TextView tvCommit;
    private LinearLayoutManager layoutManager;
    private RightBuyAdapter adapter;
    private MainPresenter presenter;
    private String url;
    private String params;
    private String faPiaoInfo="不开发票";
    private List<String> carts;
    private String goodId;
    private String skuId;
    private int goodCount;
    private String invoiceType="";
    private String personName="";
    private String personTelephone="";
    private String companyName="";
    private String companyCode="";
    private String note;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_buy);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        params = getIntent().getStringExtra("params");
        carts = getIntent().getStringArrayListExtra("carts");
        goodId = getIntent().getStringExtra("goodId");
        skuId = getIntent().getStringExtra("skuId");
        goodCount = getIntent().getIntExtra("goodCount",0);
        tvTopTitle.setText("立即购买");
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
        /**注册广播*/
        IntentFilter intentFilter = new IntentFilter("com.wytianxia.paySuccess");
        paySuccessReceiver = new PaySuccessReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(paySuccessReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(paySuccessReceiver);
        super.onDestroy();
    }

    private void getData(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            getToastdialog(this, Constants.NETWORK_ERROR);
            return;
        }
        showLoading(this);
        presenter = new MainPresenter(getApplicationContext(),this);
        url = Constants.shopCarBuy +"?"+ params;
        cancelable = presenter.shopCarAccount();
    }
    @OnClick({R.id.imageView_back,R.id.tv_commit})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;


            case R.id.tv_commit:
                /**提交订单 调起微信支付*/
                url = Constants.paySure;
                String json = setJsonParams();
                showLoading(this);
                cancelable = presenter.rightBuy(json);
                break;
        }
    }
    private String setJsonParams(){
        try{
            JsonParamsBean bean = new JsonParamsBean();
            bean.setInvoice_type(invoiceType);
            bean.setNote(note == null?"":note);
            bean.setInvoice_comp_code(companyCode);
            bean.setInvoice_comp_name(companyName);
            bean.setCarts(carts==null?new ArrayList<String>():carts);
            bean.setCount(goodCount);
            bean.setGoods_id(goodId==null?"":goodId);
            bean.setSku_id(skuId == null?"":skuId);
            bean.setInvoice_name(personName);
            bean.setInvoice_phone(personTelephone);
            bean.setTrade_num(tradeNum == null?"":tradeNum);
            bean.setAddr_id(addressId);
            Gson gson = new Gson();
            String json = gson.toJson(bean);
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String getUrl() {
        return url;
    }
    private String tradeNum;
    private String addressId;
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof RightBuyBean){
                RightBuyBean bean = (RightBuyBean) args[0];
                if (bean == null) return;
                tvTotalPrice.setText("合计:￥"+bean.getTotalPrice());
                tvGoodsAmount.setText("共"+bean.getTotalAmount()+"件商品");
                addressId = bean.getAddressId();
                if (adapter == null) {
                    adapter = new RightBuyAdapter(this,bean,this,"不开发票");
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.notifyData(bean);
                }
            }else if (args[0] instanceof PaySureBean){
                PaySureBean bean = (PaySureBean) args[0];
                /**调起微信支付*/
                payBuyWeiChat(bean);
            }
        }
        hideLoading();
    }
    private PaySuccessReceiver paySuccessReceiver;
    public class PaySuccessReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            showPayDialog();
            handler.sendEmptyMessageDelayed(200,5000);
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200){
                if (payDialog != null && payDialog.isShowing()) payDialog.dismiss();
                getToastdialog(RightBuyActivity.this ,"支付成功!");
                Intent intent = new Intent(RightBuyActivity.this, MyOrderActivity.class);
                startActivity(intent);
                RightBuyActivity.this.finish();
            }
        }
    };
    private Dialog payDialog;
    private void showPayDialog(){
        payDialog = showDialogFrame(R.layout.weichat_pay_success_dialog, Gravity.CENTER,R.drawable.dialog_background, UiUtils.dipToPx(this,295));
        payDialog.setCancelable(false);
    }
    private void payBuyWeiChat(PaySureBean bean) {
        if (bean == null)return;
        tradeNum = bean.getTrade_num();
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
    public void onItemViewClick() {
        showFaPiao();
    }

    @Override
    public void otherSay(String note) {
        this.note = note;
    }

    /**
     * 选择收货地址
     */
    @Override
    public void selectAddress() {
        Intent intent = new Intent(this , SelectAddressActivity.class);
        intent.putExtra("from","rightBuy");
        startActivityForResult(intent ,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300){
            /**选择地址成功 刷新页面*/
            getData();
        }
    }

    /**
     * 发票信息
     */
    private void showFaPiao(){
        final Dialog dialog = showDialogFrame(R.layout.activity_invoice_info, Gravity.BOTTOM,0, CommonUtil.getScreenInfo(this)[0]);
        TextView tvClose = (TextView) dialog.findViewById(R.id.tv_close);
        final CheckBox checkCompany = (CheckBox) dialog.findViewById(R.id.check_company);
        final CheckBox checkPerson = (CheckBox) dialog.findViewById(R.id.check_person);
        final CheckBox checkNon = (CheckBox) dialog.findViewById(R.id.check_non);
        final TextView tvInfo = (TextView) dialog.findViewById(R.id.text_top_info);
        final LinearLayout linearName = (LinearLayout) dialog.findViewById(R.id.linear_name);
        final TextView tvName = (TextView) dialog.findViewById(R.id.tv_name);
        final EditText etName = (EditText) dialog.findViewById(R.id.et_Name);
        final LinearLayout linearPhone = (LinearLayout) dialog.findViewById(R.id.linear_phone);
        final TextView  tvPhone = (TextView) dialog.findViewById(R.id.tv_phone);
        final EditText etPhone = (EditText) dialog.findViewById(R.id.et_phone);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) dialog.cancel();
            }
        });
        checkCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkNon.setChecked(false);
                    checkPerson.setChecked(false);
                    tvInfo.setVisibility(View.VISIBLE);
                    linearName.setVisibility(View.VISIBLE);
                    linearPhone.setVisibility(View.VISIBLE);
                    tvInfo.setText("公司信息");
                    tvName.setText("收票公司名称");
                    etName.setHint("请输入公司名称");
                    tvPhone.setText("纳税人识别号");
                    etPhone.setHint("请输入纳税人识别号");
                }
            }
        });
        checkPerson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkCompany.setChecked(false);
                    checkNon.setChecked(false);
                    tvInfo.setVisibility(View.VISIBLE);
                    linearName.setVisibility(View.VISIBLE);
                    linearPhone.setVisibility(View.VISIBLE);
                    tvInfo.setText("收票人信息");
                    tvName.setText("姓名");
                    etName.setHint("请输入姓名");
                    tvPhone.setText("手机");
                    etPhone.setHint("请输入手机号");
                }
            }
        });
        checkNon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkCompany.setChecked(false);
                    checkPerson.setChecked(false);
                    tvInfo.setVisibility(View.GONE);
                    linearName.setVisibility(View.GONE);
                    linearPhone.setVisibility(View.GONE);
                }
            }
        });
        if ("不开发票".equals(faPiaoInfo)){
            checkNon.setChecked(true);
        }else if ("公司".equals(faPiaoInfo)){
            checkCompany.setChecked(true);
        }else if ("个人".equals(faPiaoInfo)){
            checkPerson.setChecked(true);
        }
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCompany.isChecked() || checkPerson.isChecked()){
                    if (!TextUtils.isEmpty(etName.getText())&& !TextUtils.isEmpty(etPhone.getText())){
                        /**确定发票类型*/
                        if (checkCompany.isChecked()){
                            faPiaoInfo = "公司";
                            invoiceType = "comp";
                            companyName = etName.getText().toString();
                            companyCode = etPhone.getText().toString();
                            personName = "";
                            personTelephone="";
                        }else if (checkPerson.isChecked()){
                            faPiaoInfo = "个人";
                            invoiceType = "ind";
                            companyName = "";
                            companyCode = "";
                            personName = etName.getText().toString();
                            personTelephone=etPhone.getText().toString();
                        }else {
                            faPiaoInfo = "不开发票";
                            invoiceType="";
                            companyName = "";
                            companyCode = "";
                            personName = "";
                            personTelephone="";
                        }
                        if (dialog!= null) dialog.cancel();
                        if (adapter != null) adapter.reflash(faPiaoInfo);
                    }else {
                        getToastdialog(RightBuyActivity.this,"请输入发票相关信息");
                    }
                }else {
                    if (dialog!= null) dialog.cancel();
                    if (adapter != null) adapter.reflash(faPiaoInfo);
                }
            }
        });
    }
}
