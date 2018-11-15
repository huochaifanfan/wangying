package com.wytianxiatuan.wytianxia.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

/**
 * Created by liuju on 2018/3/24.
 */

public class WXPayEntryActivity extends BaseActivity  implements IWXAPIEventHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.weiChatApi.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MainApplication.weiChatApi.handleIntent(intent,this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    getToastdialog(this,"支付取消！");
                    /**用户取消*/
                    this.finish();
                    break;

                case BaseResp.ErrCode.ERR_OK:
                    /**发送广播*/
                    Intent intent = new Intent("com.wytianxia.paySuccess");
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    this.finish();
                    break;
                default:
                    getToastdialog(this,"支付失败！");
                    this.finish();
                    break;
            }
        }
    }
}
