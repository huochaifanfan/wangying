package com.wytianxiatuan.wytianxia.view.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.RegesUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.main.MainActivity;
import com.wytianxiatuan.wytianxia.view.main.MainWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/18.
 * 登录
 */

public class LoginActivity extends BaseActivity implements IMainView,TextWatcher{
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.et_msg) EditText etMsg;
    @BindView(R.id.tv_getMsg) TextView tvGetMsg;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.tv_agree) TextView tvAgree;
    private MainPresenter presenter;
    private String url;
    private String phone;
    private String isVoice = "0";
    private MyTimeCount timeCount;
    private String logout;
    private String notmain;
    private MyReciver myReciver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        logout = getIntent().getStringExtra("logout");
        notmain = getIntent().getStringExtra("notmain");
        tvAgree.setText(Html.fromHtml("温馨提示：未注册的手机号将在登录的时候自动注册且代表您已同意<font color='#11b7f4'>《用户服务协议》</font>"));
        btnLogin.setEnabled(false);
        btnLogin.setBackgroundResource(R.drawable.button_login_unenable);
        etPhone.addTextChangedListener(this);
        etMsg.addTextChangedListener(this);
        timeCount = new MyTimeCount(60*1000 , 1000);
        /**注册广播*/
        myReciver = new MyReciver();
        IntentFilter intentFilter = new IntentFilter("reflash");
        registerReceiver(myReciver,intentFilter);
    }
    public class MyReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            LoginActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReciver);
        super.onDestroy();
    }

    @OnClick({R.id.image_close,R.id.btn_login,R.id.tv_getMsg,R.id.image_weichat,R.id.tv_agree})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_close:
                if ("logout".equals(logout)){
                    Intent intent = new Intent(this ,MainActivity.class);
                    MainApplication.isMain = true;
                    startActivity(intent);
                }
                this.finish();
                break;

            case R.id.btn_login:
                url = Constants.login;
                showLoading(this);
                if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                cancelable= presenter.login(etPhone.getText().toString().trim(),etMsg.getText().toString().trim());
                break;

            case R.id.tv_getMsg:
                sendCapcha(0);
                break;

            case R.id.image_weichat:
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = System.currentTimeMillis()+"";
                MainApplication.weiChatApi.sendReq(req);
                break;

            case R.id.tv_agree:
                Intent intent = new Intent(this, MainWebView.class);
                intent.putExtra("name","用户服务协议");
                intent.putExtra("url",Constants.registerNotice);
                startActivity(intent);
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
        if (presenter == null) presenter = new MainPresenter(this ,this);
        showLoading(this);
        url = Constants.captchaMsg;
        cancelable = presenter.getMsg(etPhone.getText().toString().trim(),capther,isVoice,flag);
    }
    @Override
    public String getUrl() {
        return url;
    }
    /**点击弹框确认按钮时候 带上图片验证码 直接请求接口*/
    @Override
    protected void msgDialogClick() {
        sendCapcha(1);
    }
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length >0){
            if (args[0] instanceof String && "success".equals(args[0])){
                /**判断是否去首页*/
                if ("notmain".equals(notmain)){
                    this.finish();
                }else {
                    /**登录成功*/
                    /**刷新下首页的数据*/
                    MainApplication.isMain = true;
                    Intent intent = new Intent();
                    intent.setAction("reflash");
                    this.sendBroadcast(intent);
                    intent.setClass(this , MainActivity.class);
                    startActivity(intent);
                    getToastdialog(this,"登录成功");
                }
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
    public class MyTimeCount extends CountDownTimer{
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tvGetMsg.setEnabled(false);
            tvGetMsg.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.black_color));
            tvGetMsg.setText(millisUntilFinished/1000+"S");
        }

        @Override
        public void onFinish() {
            tvGetMsg.setEnabled(true);
            tvGetMsg.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.blue_color));
            tvGetMsg.setText("获取验证码");
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(etPhone.getText())&& !TextUtils.isEmpty(etMsg.getText())){
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.button_login);
        }else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.button_login_unenable);
        }
    }

    @Override
    public void getDataFailer(BaseBean bean) {
        if (bean != null) {
            if ("INPUT_IMG_CAPTCHA".equals(bean.getStatus())){
                /**输入图形验证码*/
                showMsgDialog();
            }else if ("450".equals(bean.getStatus())){
                /**图形验证码输入错误*/
                if (textToast != null && msgDialog != null && msgDialog.isShowing()){
                    textToast.setText("验证码输入错误，请重新输入");
                    textToast.setTextColor(ContextCompat.getColor(this , R.color.main_red));
                    imageClick();
                }else if (textToast != null){
                    textToast.setText("您的操作过于频繁，请先输入验证码");
                    textToast.setTextColor(ContextCompat.getColor(this , R.color.title_color));
                }
                if (msgDialog != null && !msgDialog.isShowing()) showMsgDialog();
            }else {
                getToastdialog(this , bean.getInfo());
            }
        }
        hideLoading();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN){
            if ("logout".equals(logout)){
                Intent intent = new Intent(this ,MainActivity.class);
                MainApplication.isMain = true;
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
