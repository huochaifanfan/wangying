package com.wytianxiatuan.wytianxia.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujun on 2017/10/11 0011.
 * 懒加载fragment
 */

public class BaseLazyFragment extends Fragment {
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    protected View mRootView;
    private void lazyFetchDataIfPrepared() {
    /**用户可见fragment && 没有加载过数据 && 视图已经准备完毕*/
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true; //已加载过数据
            initData();
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewPrepared = false;
        hasFetchData = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) lazyFetchDataIfPrepared();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = initView(inflater,container,savedInstanceState);
        }
        return mRootView;
    }
    /**
     * 这里获取数据，刷新界面
     */
    protected void initData(){}
    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return null;
    }
}
