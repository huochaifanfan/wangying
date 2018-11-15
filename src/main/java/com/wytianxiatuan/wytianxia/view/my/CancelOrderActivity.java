package com.wytianxiatuan.wytianxia.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17 0017.
 * 申请售后
 */

public class CancelOrderActivity extends BaseActivity implements IMainView{
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.etPhone) EditText etPhone;
    @BindView(R.id.cancel_amount) TextView  tvCancelAmount;
    private String orderId;
    private String totalPrice;
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        orderId = getIntent().getStringExtra("orderId");
        totalPrice = getIntent().getStringExtra("price");
        tvTitle.setText("申请售后");
        etPhone.setText(Constants.userPhone);
        tvCancelAmount.setText(Html.fromHtml("退款金额：<font color='#e64340'>￥"+totalPrice+"</font>"));
    }

    @OnClick({R.id.imageView_back,R.id.btn_conmit})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.btn_conmit:
                /**提交退款申请*/
                if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                if (!TextUtils.isEmpty(etPhone.getText())){
                    cancelable = presenter.refundOrder(orderId,etPhone.getText().toString());
                }else {
                    getToastdialog(this,"请输入手机号码");
                }
                break;
        }
    }

    @Override
    public String getUrl() {
        return Constants.refundOrder;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null &&args.length>0){
            if (args[0] instanceof String && args[0].equals("refund")){
                getToastdialog(this,"退款申请成功");
                Intent intent = new Intent();
                intent.putExtra("flag","success");
                this.setResult(300,intent);
                this.finish();
            }
        }
    }
}
