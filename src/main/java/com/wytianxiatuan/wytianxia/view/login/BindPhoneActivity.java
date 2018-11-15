package com.wytianxiatuan.wytianxia.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/19 0019.
 * 绑定手机号
 */

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.et_msg) EditText etMsg;
    @BindView(R.id.tv_getMsg) TextView tvGetMsg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btn_login})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                break;
        }
    }
}
