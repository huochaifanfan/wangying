package com.wytianxiatuan.wytianxia.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.WelcomeBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.classify.GoodsDetailActivity;
import com.wytianxiatuan.wytianxia.view.shop.MerchantShops;

/**
 * Created by liuju on 2018/1/13.
 * 首页欢迎页面
 */

public class WelcomeActivity extends BaseActivity implements IMainView ,View.OnClickListener{
    private ImageView imageView;
    private MainPresenter presenter;
    private TextView tvSkip;
    private boolean isSuccess;
    private TimeCounter timeCounter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200){
                if (!isSuccess){
                    if (cancelable != null) cancelable.cancel();
                    Intent intent = new Intent(WelcomeActivity.this , MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init() {
        presenter = new MainPresenter(getApplicationContext(),this);
        imageView = (ImageView) findViewById(R.id.imageView);
        tvSkip = (TextView) findViewById(R.id.tv_skip);
        imageView.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        cancelable = presenter.getConfig();
        handler.sendEmptyMessageDelayed(200,4000);
    }

    @Override
    public String getUrl() {
        return Constants.splashPage;
    }
    public class TimeCounter extends CountDownTimer{
        public TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long time) {
            tvSkip.setText("跳过"+time/1000);
        }
        @Override
        public void onFinish() {
            Intent intent = new Intent(WelcomeActivity.this , MainActivity.class);
            startActivity(intent);
            if (timeCounter != null)timeCounter.cancel();
            WelcomeActivity.this.finish();
        }
    }
    private String link;
    private WelcomeBean bean;
    @Override
    public void getDataSuccess(Object[] args) {
        handler.removeMessages(200);
        isSuccess = true;
        if (args!= null && args.length==1 &&args[0] instanceof WelcomeBean){
            WelcomeBean bean = (WelcomeBean) args[0];
            Constants.welcomeBean = bean;
            this.bean = bean;
            if (bean == null) return;
            link = bean.getLink();
            if (bean.getImage()!=null && !"".equals(bean.getImage())){
                Glide.with(this).load(bean.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                timeCounter = new TimeCounter(4*1000,1000);
                timeCounter.start();
                tvSkip.setVisibility(View.GONE);
            }else {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView:
                if (link != null && link.length()>8){
                    handler.removeMessages(200);
                    if (timeCounter != null)timeCounter.cancel();
                    if (bean == null) return;
                    String type = bean.getLinkType();
                    if ("NONE".equals(type)){
                        return;
                    }else if ("HREF".equals(type)){
                        Intent intent = new Intent(this , MainWebView.class);
                        intent.putExtra("url",link);
                        intent.putExtra("where","main");
                        startActivity(intent);
                    }else if ("DIRECT".equals(type)){
                        /**应用内跳转 跳转商品详情 或者店铺*/
                        String redirectType = bean.getRedirectType();
                        String redirectId = bean.getRedirectId();
                        Intent intent = new Intent();
                        if ("SHOP".equals(redirectType)){
                            /**去店铺*/
                            intent.setClass(this,MerchantShops.class);
                            intent.putExtra("id",redirectId);
                        }else if ("DETAIL".equals(redirectType)){
                            intent.setClass(this,GoodsDetailActivity.class);
                            intent.putExtra("id",Integer.valueOf(redirectId));
                        }
                        startActivity(intent);
                    }
                    this.finish();
                }
                break;

            case R.id.tv_skip:
                timeCounter.onFinish();
                break;
        }
    }
}
