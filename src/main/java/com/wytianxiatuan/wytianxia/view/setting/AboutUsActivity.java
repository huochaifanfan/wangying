package com.wytianxiatuan.wytianxia.view.setting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/22 0022.
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_version) TextView tvVersion;
    @BindView(R.id.tv_top_title) TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        tvVersion.setText("版本号：V"+ CommonUtil.getVersonName(this));
        textView.setText("关于我们");
    }
    @OnClick({R.id.imageView_back,R.id.linear_guli,R.id.linear_suggestion})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.linear_guli:
                if (Build.MODEL.contains("SM")){
                    CommonUtil.goToSamsungappsMarket(this);
                }else {
                    CommonUtil.shareAppShop(this);
                }
                break;

            case R.id.linear_suggestion:
                intent.setClass(this, SuggestionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
