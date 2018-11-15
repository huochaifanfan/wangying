package com.wytianxiatuan.wytianxia.view.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.GoodsListAdapter;
import com.wytianxiatuan.wytianxia.adapter.MainType6Adapter;
import com.wytianxiatuan.wytianxia.bean.MainBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.GridDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.classify.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class ShopMainFragment extends BaseFragment implements IMainView,OnRefreshListener,OnRefreshLoadmoreListener,MainType6Adapter.OnDetailClickListener {
    private RecyclerView recyclerView;
    private MainPresenter presenter;
    private MainType6Adapter adapter;
    private String shopId;
    private String content;
    private int page = 1;
    private MyCastReceiver myCastReceiver;
    private SmartRefreshLayout refreshLayout;
    private List<MainBean.Recommand> dataAll;
    private LinearLayout linearEmpty;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_new,container,false);
        Bundle args = getArguments();
        shopId = args !=null?args.getString("shopId"):"";
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addItemDecoration(new GridDecoration(getActivity(),R.color.white_color, LinearLayoutManager.HORIZONTAL , UiUtils.dipToPx(getActivity(),15)));
        recyclerView.addItemDecoration(new GridDecoration(getActivity(),R.color.white_color, LinearLayoutManager.VERTICAL , UiUtils.dipToPx(getActivity(),15)));
        myCastReceiver = new MyCastReceiver();
        IntentFilter intentFilter = new IntentFilter("content");
        getActivity().registerReceiver(myCastReceiver,intentFilter);
        dataAll = new ArrayList<>();
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        linearEmpty = (LinearLayout) view.findViewById(R.id.empty_view);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getData(2);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        refreshlayout.resetNoMoreData();
        getData(1);
    }

    @Override
    public void onDetailClick(int autoId) {
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }

    public class MyCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) content = intent.getStringExtra("content");
            getData(0);
        }
    }
    @Override
    protected void initData() {
        if (presenter == null) presenter = new MainPresenter(getActivity().getApplicationContext(),this);
        getData(0);
    }
    private void getData(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(), Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        cancelable = presenter.merchantShops(shopId,"0",content,page+"",flag);
    }
    @Override
    public String getUrl() {
        return Constants.merchantShops;
    }
    @Override
    public void onDestroy() {
        if (myCastReceiver != null&& getActivity() != null) getActivity().unregisterReceiver(myCastReceiver);
        super.onDestroy();
    }
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length > 0) {
            if (args[0] instanceof List && args[1] instanceof Integer) {
                List<MainBean.Recommand> data = (List<MainBean.Recommand>) args[0];
                int flag = (int) args[1];
                setData(data,flag);
            }
        }
    }
    private void setData(List<MainBean.Recommand> data ,int flag){
        if (flag == 0 || flag ==1)dataAll = data;
        if (dataAll != null && dataAll.size()>0){
            refreshLayout.setVisibility(View.VISIBLE);
            linearEmpty.setVisibility(View.GONE);
            if (flag == 2 && data != null && data.size() > 0){
                dataAll.addAll(data);
            }else if (flag == 2 && data == null ||flag == 2 && data != null &&data.size() == 0){
                refreshLayout.finishLoadmoreWithNoMoreData();
            }
            if (adapter == null) {
                adapter = new MainType6Adapter(dataAll,getActivity(),1,null,this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.update(dataAll);
            }
            if (flag == 1)refreshLayout.finishRefresh(300,true);
            if (flag == 2 && data != null&&data.size() > 0) refreshLayout.finishLoadmore(200);
        }else {
            refreshLayout.setVisibility(View.GONE);
            linearEmpty.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }
}
