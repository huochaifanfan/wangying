package com.wytianxiatuan.wytianxia.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.CookieUtil;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17 0017.
 * 设置页面
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.image_switch) ImageView imageSwitch;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvTitle.setText("设置");
        tvPhone.setText(Constants.userPhone);
    }
    @OnClick({R.id.imageView_back , R.id.image_switch,R.id.linear_bind_phone ,
            R.id.linear_setPwd ,R.id.linear_aboutUs ,R.id.btn_logout})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.image_switch:
                break;

            case R.id.linear_bind_phone:
                break;

            case R.id.linear_setPwd:
                getToastdialog(this,"清除成功！");
                break;

            case R.id.linear_aboutUs:
                Intent intent1 = new Intent(this ,AboutUsActivity.class);
                startActivity(intent1);
                break;

            case R.id.btn_logout:
                /**退出登录*/
                CookieUtil.logout(this);
                Constants.userId = "0";
                Intent intent = new Intent(this , LoginActivity.class);
                intent.putExtra("logout","logout");
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
