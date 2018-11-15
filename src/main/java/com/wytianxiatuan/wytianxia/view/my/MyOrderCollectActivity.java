package com.wytianxiatuan.wytianxia.view.my;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 我的收藏
 */

public class MyOrderCollectActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imageBack;
    private MagicIndicator indicator;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private String[] tabTitles = {"商品","店铺"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_collect);
        init();
    }

    private void init() {
        imageBack = (ImageView) findViewById(R.id.imageView_back);
        indicator = (MagicIndicator) findViewById(R.id.viewPagerIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageBack.setOnClickListener(this);
        fragments = new ArrayList<>();
        MyGoodsCollectFragment goodsFragment = new MyGoodsCollectFragment();
        MyShopCollectFragment shopFragment = new MyShopCollectFragment();
        fragments.add(goodsFragment);
        fragments.add(shopFragment);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabTitles.length;
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(MyOrderCollectActivity.this , R.color.black_color));
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(MyOrderCollectActivity.this , R.color.main_red));
                colorTransitionPagerTitleView.setText(tabTitles[i]);
                colorTransitionPagerTitleView.setTextSize(16);
                colorTransitionPagerTitleView.setGravity(Gravity.CENTER);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {viewPager.setCurrentItem(i);}
                });
                return colorTransitionPagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UiUtils.dipToPx(MyOrderCollectActivity.this , 40));
                indicator.setColors(ContextCompat.getColor(MyOrderCollectActivity.this , R.color.main_red));
                indicator.setLineHeight(UiUtils.dipToPx(MyOrderCollectActivity.this , 2));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator , viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;
        }
    }
    public class MyPagerAdapter extends FragmentPagerAdapter{
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
}
