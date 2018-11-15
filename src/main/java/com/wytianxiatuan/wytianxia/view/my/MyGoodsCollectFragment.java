package com.wytianxiatuan.wytianxia.view.my;

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
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MyGoodsCollectAdapter;
import com.wytianxiatuan.wytianxia.bean.MyGoodsCollectBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.classify.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/1/18.
 * 商品收藏
 */

public class MyGoodsCollectFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener,
        IMainView ,MyGoodsCollectAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyDecoration myDecoration;
    private MyGoodsCollectAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<MyGoodsCollectBean> dataAll;
    private MainPresenter presenter;
    private LinearLayout tvNoResult;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect,container,false);
        init(view);
        return view;
    }

    @Override
    protected void initData() {
        getData(0);
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(getActivity(),1));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        tvNoResult = (LinearLayout) view.findViewById(R.id.empty_view);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));/**经典官方主题*/
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        dataAll = new ArrayList<>();
        presenter = new MainPresenter(getActivity().getApplicationContext(),this);
    }
    private void getData(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(), Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        cancelable = presenter.goodCollect(flag);
    }
    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page ++;
        getData(2);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page =1;
        refreshlayout.resetNoMoreData();
        getData(1);
    }

    @Override
    public String getUrl() {
        return Constants.goodCollect+"?page="+page;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length ==2){
            if (args[0] instanceof List && args[1] instanceof Integer){
                List<MyGoodsCollectBean> data = (List<MyGoodsCollectBean>) args[0];
                int flag = (int) args[1];
                if (flag == 0 || flag ==1)dataAll = data;
                setData(data , flag);
            }
        }
        hideLoading();
    }

    private void setData(List<MyGoodsCollectBean> data, int flag) {
        if (dataAll != null && dataAll.size()>0){
            refreshLayout.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            if (flag == 2 && data != null && data.size() > 0){
                dataAll.addAll(data);
            }else if (flag == 2 && data == null ||flag == 2 && data != null &&data.size() == 0){
                refreshLayout.finishLoadmoreWithNoMoreData();
            }
            if (adapter == null) {
                adapter = new MyGoodsCollectAdapter(dataAll,getActivity(),this);
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
    }

    @Override
    public void onItemClick(int autoId) {
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }
}
