package com.wytianxiatuan.wytianxia.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.WeiChatBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.RegesUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/22 0022.
 * 微信联合登录
 */

public class WeiChatLoginActivity extends BaseActivity implements TextWatcher,IMainView{
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.et_msg) EditText etMsg;
    @BindView(R.id.tv_getMsg) TextView tvGetMsg;
    @BindView(R.id.tv_register) TextView tvRegister;
    @BindView(R.id.btn_sure) Button btnSure;
    private WeiChatBean bean;
    private String nickName;
    private String userPicture;
    private MainPresenter presenter;
    private String url;
    private String isVoice = "0";
    private MyTimeCount timeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weichat_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvTitle.setText("微信联合登录");
        bean = (WeiChatBean) getIntent().getSerializableExtra("bean");
        nickName = bean !=null?bean.getNickName():"";
        userPicture = bean != null?bean.getHeadImage():"";
        btnSure.setEnabled(false);
        btnSure.setBackgroundResource(R.drawable.button_login_unenable);
        etPhone.addTextChangedListener(this);
        etMsg.addTextChangedListener(this);
        timeCount = new MyTimeCount(60*1000 , 1000);
    }
    @OnClick({R.id.imageView_back,R.id.btn_sure,R.id.tv_getMsg})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.btn_sure:
                /**完成绑定*/
                url = Constants.login;
                showLoading(this);
                if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                cancelable= presenter.login(etPhone.getText().toString().trim(),etMsg.getText().toString().trim());
                break;

            case R.id.tv_getMsg:
                /**获取验证码*/
                sendCapcha(0);
                break;
        }
    }
    private void sendCapcha(int flag){
        if (TextUtils.isEmpty(etPhone.getText())){
            getToastdialog(this , "手机号码不能为空");
            return;
        }
        if (!RegesUtils.IsPhone(etPhone.getText().toString().trim())){
            getToastdialog(this ,"手机号格式错误");
            return;
        }
        if (presenter == null) presenter = new MainPresenter(getApplicationContext() ,this);
        showLoading(this);
        url = Constants.captchaMsg;
        cancelable = presenter.getMsg(etPhone.getText().toString().trim(),capther,isVoice,flag);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etPhone.getText()) && !TextUtils.isEmpty(etMsg.getText())){
            btnSure.setEnabled(true);
            btnSure.setBackgroundResource(R.drawable.button_login);
        }else {
            btnSure.setEnabled(false);
            btnSure.setBackgroundResource(R.drawable.button_login_grey);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length >0){
            if (args[0] instanceof String && "success".equals(args[0])){
                /**登录成功*/
                /**刷新下首页的数据*/
                MainApplication.isMain = true;
                Intent intent = new Intent();
                intent.setAction("reflash");
                this.sendBroadcast(intent);
                intent.setClass(this , MainActivity.class);
                startActivity(intent);
                getToastdialog(this,"登录成功");
                finish();
            }else if (args[0] instanceof String && "200".equals(args[0])){
                /**发送验证码成功*/
                getToastdialog(this,"验证码发送成功，请注意查收！");
                if (msgDialog != null && msgDialog.isShowing()) msgDialog.cancel();
                if (timeCount != null) timeCount.start();
            }
        }
        hideLoading();
    }
    public class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tvGetMsg.setEnabled(false);
            tvGetMsg.setTextColor(ContextCompat.getColor(WeiChatLoginActivity.this,R.color.black_color));
            tvGetMsg.setText(millisUntilFinished/1000+"S");
        }

        @Override
        public void onFinish() {
            tvGetMsg.setEnabled(true);
            tvGetMsg.setTextColor(ContextCompat.getColor(WeiChatLoginActivity.this,R.color.blue_color));
            tvGetMsg.setText("获取验证码");
        }
    }
}
