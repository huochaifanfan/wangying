package com.wytianxiatuan.wytianxia.view.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.WelcomeBean;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.LightStatusBarUtils;
import com.wytianxiatuan.wytianxia.util.RomUtils;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.classify.ClassifyFragment;
import com.wytianxiatuan.wytianxia.view.dynamic.DynaMicFragment;
import com.wytianxiatuan.wytianxia.view.login.LoginActivity;
import com.wytianxiatuan.wytianxia.view.my.MyFragment;
import com.wytianxiatuan.wytianxia.view.shop.ShopCarFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

/**
 * 首页主Activity
 */
public class MainActivity extends BaseActivity implements MainFragment.PageChange,ShopCarFragment.OnShopCarGoodCountChangeListener{
    @BindView(R.id.image_main) ImageView imageMain;
    @BindView(R.id.tv_main) TextView tvMain;
    @BindView(R.id.image_classify) ImageView imageClassify;
    @BindView(R.id.tv_classify) TextView tvClassify;
    @BindView(R.id.image_dynamic) ImageView imageDynamic;
    @BindView(R.id.tv_dynamic) TextView tvDynamic;
    @BindView(R.id.image_shopCar) ImageView imageShopCar;
    @BindView(R.id.tv_shopCar) TextView tvShopCar;
    @BindView(R.id.image_my) ImageView imageMy;
    @BindView(R.id.tv_my) TextView tvMy;
    @BindView(R.id.rel_shopCar) RelativeLayout relShopCar;
    private int selectedColor;
    private int unSelectedColor;
    private Fragment[] fragments;
    private int curIndex = 0;
    private int index = 0;
    private int back = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init(savedInstanceState);
        checkUpdate();
    }

    private void init(Bundle savedInstanceState) {
        selectedColor = ContextCompat.getColor(this,R.color.main_red);
        unSelectedColor = ContextCompat.getColor(this,R.color.main_grey);
        if (fragments == null) {
            fragments = new Fragment[5];
            fragments[0] = new MainFragment();
            fragments[1] = new ClassifyFragment();
            fragments[2] = new DynaMicFragment();
            fragments[3] = new ShopCarFragment();
            fragments[4] = new MyFragment();
        }
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.rl_container, fragments[0]);
            fragmentTransaction.show(fragments[0]);
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }
    @OnClick({R.id.rel_main , R.id.rel_classify , R.id.rel_dynamic , R.id.rel_shopCar , R.id.rel_my})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rel_main:
                index = 0;
                break;

            case R.id.rel_classify:
                index = 1;
                break;

            case R.id.rel_dynamic:
                index = 2;
                break;

            case R.id.rel_shopCar:
                index = 3;
                break;

            case R.id.rel_my:
                index = 4;
                break;
        }
        changePage(index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainApplication.isMain){
            MainApplication.isMain= false;
            changePage(0);
        }else if (MainApplication.isMy){
            MainApplication.isMy =false;
            changePage(4);
        }
    }

    /**
     * 切换页面显示
     * @param index
     */
    private void changePage(int index) {
        if ((index == 4 || index == 3 )&& "0".equals(Constants.userId)){
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
        }else {
            unSelectedAll();
            selectedColor(index);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (curIndex != index && fragments != null) {
                fragmentTransaction.hide(fragments[curIndex]);
                if (!fragments[index].isAdded())
                    fragmentTransaction.add(R.id.rl_container, fragments[index]);
                fragmentTransaction.show(fragments[index]);
                fragmentTransaction.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
            curIndex = index;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (index == 1 || index == 3) {
                    if (RomUtils.getLightStatausBarAvailableRomType() == 1) {
                        LightStatusBarUtils.setMIUILightStatusBar(this, true);
                    } else {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                } else {
                    if (RomUtils.getLightStatausBarAvailableRomType() == 1) {
                        LightStatusBarUtils.setMIUILightStatusBar(this, false);
                    } else {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }
            }
        }
    }

    private void unSelectedAll() {
        imageMain.setImageResource(R.drawable.main_unselected);
        imageClassify.setImageResource(R.drawable.classify_unselected);
        imageDynamic.setImageResource(R.drawable.dynamic_unselected);
        imageShopCar.setImageResource(R.drawable.shop_car_unselected);
        imageMy.setImageResource(R.drawable.my_unselected);
        tvMain.setTextColor(unSelectedColor);
        tvClassify.setTextColor(unSelectedColor);
        tvDynamic.setTextColor(unSelectedColor);
        tvShopCar.setTextColor(unSelectedColor);
        tvMy.setTextColor(unSelectedColor);
    }
    private void selectedColor(int index){
        switch (index){
            case 0:
                imageMain.setImageResource(R.drawable.main_selected);
                tvMain.setTextColor(selectedColor);
                break;

            case 1:
                imageClassify.setImageResource(R.drawable.classify_selected);
                tvClassify.setTextColor(selectedColor);
                break;

            case 2:
                imageDynamic.setImageResource(R.drawable.dynamic_selected);
                tvDynamic.setTextColor(selectedColor);
                break;

            case 3:
                imageShopCar.setImageResource(R.drawable.shop_car_selected);
                tvShopCar.setTextColor(selectedColor);
                break;

            case 4:
                imageMy.setImageResource(R.drawable.my_selected);
                tvMy.setTextColor(selectedColor);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        back ++;
        if (back == 1) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    back = 0;
                }
            }, 2000);
        }
        if (back ==2) {
            MainApplication.exitApp();
            Log.i("des","退出了");
        }
    }

    @Override
    public void pageChange(int position) {
        changePage(position);
    }
    /**
     * 检查新版本
     */
    private void checkUpdate(){
        try {
            int version = CommonUtil.getVersion(this);
            WelcomeBean entity = Constants.welcomeBean;
            if (entity != null) {
                String url = entity.getAddress();
                if (entity.getVersionCode() != 0 && entity.getVersionCode() > version) {
                    showLoginDialog(url);
                }else{
//                    showLoginDialog(url);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private QBadgeView qBadgeView;
    @Override
    public void onShopCarGoodCountChange(int count) {
        if (qBadgeView == null) qBadgeView = new QBadgeView(this);
        if (count>0){
            qBadgeView.hide(false);
            qBadgeView.bindTarget(relShopCar);
            qBadgeView.setBadgeNumber(count);
            qBadgeView.setBadgeTextSize(10,true);
            qBadgeView.setBadgeTextColor(ContextCompat.getColor(this,R.color.white_color));
            qBadgeView.setBadgeBackgroundColor(ContextCompat.getColor(this,R.color.main_red));
            qBadgeView.setBadgePadding(2,true);
            qBadgeView.setBadgeGravity(Gravity.END|Gravity.TOP);
            qBadgeView.setGravityOffset(16,3,true);
        }else {
            qBadgeView.hide(true);
        }
    }
}
