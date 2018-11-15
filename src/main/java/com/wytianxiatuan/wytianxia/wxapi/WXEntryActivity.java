package com.wytianxiatuan.wytianxia.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.CookieUtil;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.login.WeiChatLoginActivity;
import com.wytianxiatuan.wytianxia.view.main.MainActivity;

/**
 * Created by liuju on 2018/1/15.
 * 微信响应类
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler,IMainView{
    private String code;
    private MainPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.weiChatApi.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MainApplication.weiChatApi.handleIntent(intent , this);
    }

    @Override
    public void onReq(BaseReq baseReq) {}

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                /**用户拒绝授权*/
                this.finish();
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                /**用户取消*/
                this.finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH){
                    /**用户同意*/
                    code = ((SendAuth.Resp)baseResp).code;
                    if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                    cancelable = presenter.weiChatLogin(code);
                }
                break;

            default:
                this.finish();
                break;
        }
    }
    @Override
    public String getUrl() {
        return Constants.weichatLogin;
    }

    @Override
    public void getDataFailer(BaseBean bean) {
        if (bean != null && "LOGIN_SUCCESS".equals(bean.getStatus())){
            MainApplication.isMain = true;
            Intent intent = new Intent();
            intent.setAction("reflash");
            this.sendBroadcast(intent);
            intent.setClass(this , MainActivity.class);
            startActivity(intent);
            getToastdialog(this,"登录成功");
            CookieUtil.saveCookie(this);
            this.finish();
        }
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof String &&"success".equals(args[0])){
                /**绑定成功 去绑定手机号界面*/
                Intent intent = new Intent(this, WeiChatLoginActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }
}
