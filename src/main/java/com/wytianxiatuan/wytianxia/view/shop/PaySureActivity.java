package com.wytianxiatuan.wytianxia.view.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/19.
 * 确认付款
 */

public class PaySureActivity extends BaseActivity {
    @BindView(R.id.tv_top_title) TextView tvTopTitle;
    @BindView(R.id.image_aiply) ImageView imageAiply;
    @BindView(R.id.image_weichat) ImageView imageWeiChat;
    @BindView(R.id.tv_total_price) TextView tvTotalPrice;
    @BindView(R.id.good_count) TextView tvGoodCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_sure);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvTopTitle.setText("确认付款");
    }
    @OnClick({R.id.imageView_back,R.id.tv_pay_sure})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.tv_pay_sure:
                /**确定付款*/
                break;
        }
    }
}
