package com.wytianxiatuan.wytianxia.view.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.DynamicAdapter;
import com.wytianxiatuan.wytianxia.adapter.GoodsListAdapter;
import com.wytianxiatuan.wytianxia.bean.DynaMicBean;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.main.MainWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class DynaMicFragment extends BaseFragment implements DynamicAdapter.OnItemClickListener,
        IMainView,OnRefreshListener,OnRefreshLoadmoreListener{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.smartRefreshLayout) SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_view) LinearLayout tvNoResult;
    private LinearLayoutManager layoutManager;
    private MyDecoration myDecoration;
    private DynamicAdapter adapter;
    private MainPresenter presenter;
    private int page=1;
    private List<DynaMicBean> dataAll;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic , container ,false);
        ButterKnife.bind(this,view);
        init(view);
        return view;
    }
    private void getData(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        cancelable = presenter.getDyanmic(flag);
    }
    @Override
    protected void initData() {
        /**数据请求*/
        getData(0);
    }

    private void init(View view) {
        presenter = new MainPresenter(getActivity().getApplicationContext(), this);
        UiUtils.setParams(getActivity(),textTop,1);
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(getActivity(),1));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        dataAll = new ArrayList<>();
    }

    @Override
    public void onItemClick(int itemId) {
        /**动态详情页面*/
        Intent intent = new Intent(getActivity(), MainWebView.class);
        intent.putExtra("url",Constants.dynamicDetail+"?id="+itemId);
        startActivity(intent);
    }

    @Override
    public String getUrl() {
        return Constants.dynamic+"?page="+page;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof List && args[1] instanceof Integer){
                List<DynaMicBean> data = (List<DynaMicBean>) args[0];
                int flag = (int) args[1];
                setData(data,flag);
            }
        }
    }

    private void setData(List<DynaMicBean> data, int flag) {
        if (flag == 0 || flag ==1)dataAll = data;
        if (dataAll != null && dataAll.size()>0){
            refreshLayout.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            if (flag == 2 && data != null && data.size() > 0){
                dataAll.addAll(data);
            }else if (flag == 2 && data == null ||flag == 2 && data != null &&data.size() == 0){
                refreshLayout.finishLoadmoreWithNoMoreData();
            }
            if (adapter == null) {
                adapter = new DynamicAdapter(getActivity(), dataAll, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.update(dataAll);
            }
            if (flag == 1)refreshLayout.finishRefresh(300,true);
            if (flag == 2 && data != null&&data.size() > 0) refreshLayout.finishLoadmore(200);
        }else {
            refreshLayout.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page ++;
        getData(2);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        refreshlayout.resetNoMoreData();
        getData(1);
    }
}
