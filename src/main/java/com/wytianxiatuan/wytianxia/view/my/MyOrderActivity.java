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
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/17.
 * 我的订单
 */

public class MyOrderActivity extends BaseActivity implements IMainView{
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.viewPagerIndicator) MagicIndicator  magicIndicator;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private List<Fragment> fragments;
    private MainPresenter presenter;
    private int pagerPos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        pagerPos = getIntent().getIntExtra("pagerPos",0);
        presenter = new MainPresenter(getApplicationContext(),this);
        getData();
        tvTitle.setText("我的订单");
        fragments = new ArrayList<>();
    }
    private void getData(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            getToastdialog(this, Constants.NETWORK_ERROR);
            return;
        }
        cancelable = presenter.orderManage(0);
    }
    @OnClick({R.id.imageView_back})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;
        }
    }

    @Override
    public String getUrl() {
        return Constants.orderManage;
    }
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof MyOrderBean){
                MyOrderBean bean = (MyOrderBean) args[0];
                initFragment(bean);
            }
        }
    }

    private void initFragment(MyOrderBean bean) {
        if (bean == null) return;
        final List<MyOrderBean.OrderType> orderTypes = bean.getTypeData();
        if (orderTypes != null && orderTypes.size()>0) {
            for (int i = 0; i < orderTypes.size(); i++) {
                Bundle bundle = new Bundle();
                bundle.putString("type", orderTypes.get(i).getType());
                MyOrderFragment fragment = new MyOrderFragment();
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
            MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            CommonNavigator commonNavigator = new CommonNavigator(this);
            commonNavigator.setAdjustMode(false);
            commonNavigator.setAdapter(new CommonNavigatorAdapter() {
                @Override
                public int getCount() {
                    return orderTypes.size();
                }
                @Override
                public IPagerTitleView getTitleView(Context context, final int i) {
                    ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                    colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(MyOrderActivity.this, R.color.black_color));
                    colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(MyOrderActivity.this, R.color.main_red));
                    colorTransitionPagerTitleView.setText(orderTypes.get(i).getTitle());
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
                    indicator.setColors(ContextCompat.getColor(MyOrderActivity.this, R.color.main_red));
                    indicator.setLineHeight(UiUtils.dipToPx(MyOrderActivity.this, 2));
                    return indicator;
                }
            });
            magicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(magicIndicator, viewPager);
            if (pagerPos <=orderTypes.size()-1){
                viewPager.setCurrentItem(pagerPos);
            }
        }
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
