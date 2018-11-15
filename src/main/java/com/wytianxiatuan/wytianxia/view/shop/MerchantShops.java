package com.wytianxiatuan.wytianxia.view.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.CallBack;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.HttpUtils;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.login.LoginActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by junliu on 2018/1/22 0022.
 * 商户的店铺
 */

public class MerchantShops extends BaseActivity implements TextWatcher,IMainView{
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.et_search) EditText etSearch;
    @BindView(R.id.image_search) ImageView imageSearch;
    @BindView(R.id.viewPagerIndicator) MagicIndicator magicIndicator;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.text_tool) TextView textTool;
    @BindView(R.id.shop_picture) CircleImageView logoImageView;
    @BindView(R.id.shop_name) TextView textShopName;
    @BindView(R.id.btn_collect) Button btnCollect;
    private List<Fragment> fragments;
    private String[] tabTitle = {"首页","最新","公司介绍"};
    private String shopId;
    private String content;
    private ShopMainFragment shopMainFragment;
    private ShopNewFragment shopNewFragment;
    private ShopDesFragment shopDesFragment;
    private String name;
    private String logoUrl;
    private boolean isCollect;
    private MainPresenter presenter;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_merchant);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        shopId = getIntent().getStringExtra("id");
        UiUtils.setParams(this,textTop,1);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textTool.getLayoutParams();
        params.height = CommonUtil.getStatusBarHeight(this);
        textTool.setLayoutParams(params);
        fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("shopId",shopId);
        shopMainFragment = new ShopMainFragment();
        shopNewFragment = new ShopNewFragment();
        shopDesFragment = new ShopDesFragment();
        shopMainFragment.setArguments(bundle);
        shopNewFragment.setArguments(bundle);
        fragments.add(shopMainFragment);
        fragments.add(shopNewFragment);
        fragments.add(shopDesFragment);
        isShopCollect(Constants.shopIsCollect+"?id="+shopId);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabTitle.length;
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(MerchantShops.this , R.color.black_color));
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(MerchantShops.this , R.color.main_red));
                colorTransitionPagerTitleView.setText(tabTitle[i]);
                colorTransitionPagerTitleView.setTextSize(15);
                colorTransitionPagerTitleView.setGravity(Gravity.CENTER);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i);
                    }
                });
                return colorTransitionPagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(ContextCompat.getColor(MerchantShops.this , R.color.main_red));
                indicator.setLineHeight(UiUtils.dipToPx(MerchantShops.this , 2));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator , viewPager);
        etSearch.addTextChangedListener(this);
    }
    @OnClick({R.id.imageView_back,R.id.image_search,R.id.btn_collect})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.image_search:
                if (!TextUtils.isEmpty(etSearch.getText())){
                    content = etSearch.getText().toString();
                }else {
                    content = "";
                }
                sendCast(content);
                break;

            case R.id.btn_collect:
                if ("0".equals(Constants.userId)){
                    Intent intent = new Intent(this , LoginActivity.class);
                    intent.putExtra("notmain","notmain");
                    startActivity(intent);
                }else {
                    if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                    showLoading(this);
                    if (isCollect){
                        /**已经收藏了 点击取消收藏*/
                        url = Constants.cancelShopCollect;
                        cancelable = presenter.collectGood(1 ,shopId);/** flag 1是取消收藏 2个收藏*/
                    }else {
                        /**还没有收藏 点击收藏*/
                        url = Constants.collectShop;
                        cancelable = presenter.collectGood(2 ,shopId);/** flag 1是取消收藏 2个收藏*/
                    }
                }
                break;
        }
    }
    private void sendCast(String content){
        Intent intent = new Intent();
        intent.putExtra("content",content);
        intent.setAction("content");
        sendBroadcast(intent);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(etSearch.getText())){
            content ="";
            sendCast(content);
        }
    }

    @Override
    public String getUrl() {
        return url;
    }
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
           if (args[0] instanceof Integer){
                int flag = (int) args[0];
                if (flag ==1){/**取消收藏成功*/
                    btnCollect.setBackgroundResource(R.drawable.button_login);
                    btnCollect.setText("收藏");
                    isCollect = false;
                }else {
                    isCollect = true;
                    btnCollect.setBackgroundResource(R.drawable.button_login_unenable);
                    btnCollect.setText("已收藏");
                }
            }
        }
        hideLoading();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {}
    }
    private void isShopCollect(String url){
        RequestParams params = new RequestParams(url);
        HttpUtils.send(HttpMethod.GET,this,params, new CallBack<String>(null) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject object = jsonObject.getJSONObject("data");
                    isCollect = object.getBoolean("is_collect");
                    JSONObject shopObject = object.getJSONObject("shop");
                    String shopIntrodice = shopObject.getString("content");
                    name = shopObject.getString("name");
                    logoUrl = shopObject.getString("logo_url");
                    shopDesFragment.setShopDes(shopIntrodice);
                    if (isCollect){
                        btnCollect.setBackgroundResource(R.drawable.button_login_unenable);
                        btnCollect.setText("已收藏");
                    }else {
                        btnCollect.setBackgroundResource(R.drawable.button_login);
                        btnCollect.setText("收藏");
                    }
                    Glide.with(MerchantShops.this).load(logoUrl).into(logoImageView);
                    textShopName.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
