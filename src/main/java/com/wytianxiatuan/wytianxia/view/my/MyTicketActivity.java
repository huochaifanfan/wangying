package com.wytianxiatuan.wytianxia.view.my;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MyTicketAdapter;
import com.wytianxiatuan.wytianxia.bean.MyTicketBean;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
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
 * Created by Administrator on 2018/1/16 0016.
 * 我的优惠券
 */

public class MyTicketActivity extends BaseActivity {
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.viewPagerIndicator) MagicIndicator magicIndicator;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private List<Fragment> fragments;
    private String[] tabTitle = {"未使用","已失效"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        tvTitle.setText("我的优惠券");
        fragments = new ArrayList<>();
        for (int i = 0;i < tabTitle.length;i++){
            Bundle bundle = new Bundle();
            bundle.putString("type",tabTitle[i]);
            MyTicketFragment noticeFragment = new MyTicketFragment();
            noticeFragment.setArguments(bundle);
            fragments.add(noticeFragment);
        }
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
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(MyTicketActivity.this , R.color.black_color));
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(MyTicketActivity.this , R.color.main_red));
                colorTransitionPagerTitleView.setText(tabTitle[i]);
                colorTransitionPagerTitleView.setTextSize(16);
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
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UiUtils.dipToPx(MyTicketActivity.this,70));
                indicator.setColors(ContextCompat.getColor(MyTicketActivity.this , R.color.main_red));
                indicator.setLineHeight(UiUtils.dipToPx(MyTicketActivity.this , 2));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator , viewPager);
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
        }
    }
    @OnClick({R.id.imageView_back})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;
        }
    }
}
