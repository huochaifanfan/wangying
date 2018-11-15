package com.wytianxiatuan.wytianxia.view.classify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.GoodsListAdapter;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/1/28.
 * 商品列表
 */

public class GoodsListFragment extends BaseFragment implements IGoodListView,
        GoodsListAdapter.OnItemClickListener,OnRefreshListener,OnRefreshLoadmoreListener {
    private String type="0";
    private String content;
    private RecyclerView recyclerView;
    private MainPresenter presenter;
    private String orderId;
    private int page = 1;
    private GoodsListAdapter adapter;
    private TextView tvNoResult;
    private SmartRefreshLayout refreshLayout;
    private List<GoodsListBean.GoodsList> dataAll;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_list ,container,false);
        Bundle args = getArguments();
        type = args != null ? args.getString("type"):"";
        content = args !=null?args.getString("content"):"";
        orderId = args!=null ?args.getString("orderid"):"";
        init(view);
        return view;
    }

    private void init(View view) {
        presenter = new MainPresenter(getActivity(),this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(getActivity(),1)));
        tvNoResult = (TextView) view.findViewById(R.id.tv_noResult);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        dataAll = new ArrayList<>();
    }
    @Override
    protected void initData() {
        getData(0);
    }
    private void getData(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
//        if (presenter == null) presenter = new MainPresenter(getActivity(),this);
        cancelable = presenter.getGoodList(flag,0);
    }
    @Override
    public String getUrl() {
        return Constants.classify;
    }
    @Override
    public String getWd() {
        return content;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getCatId() {
        return type;
    }
    @Override
    public String page() {
        return page+"";
    }
    @Override
    public boolean isOrder() {
        return false;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof GoodsListBean && args[1] instanceof Integer){
                GoodsListBean bean = (GoodsListBean) args[0];
                int flag = (int) args[1];
                List<GoodsListBean.GoodsList> data = bean!=null?bean.getGoodsLists():null;
                if (flag == 0 || flag ==1){
//                    if (data != null &&data.size() > 0){
                        dataAll = data;
//                    }
                }
                setData(data,flag);
            }
        }
    }
    private void setData(List<GoodsListBean.GoodsList> data , int flag){
        if (dataAll != null && dataAll.size()>0){
            refreshLayout.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            if (flag == 2 && data != null && data.size() > 0){
                dataAll.addAll(data);
            }else if (flag == 2 && data == null ||flag == 2 && data != null &&data.size() == 0){
                refreshLayout.finishLoadmoreWithNoMoreData();
            }
            if (adapter == null) {
                adapter = new GoodsListAdapter(getActivity(), dataAll, this);
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
    public void onItemCLick(int autoId) {
        Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        GoodListActivity activity = (GoodListActivity) context;
//        activity.setOnOrder(this);
    }

//    @Override
//    public void onOrderClick(String autoId) {
//        getToastdialog(getActivity() ,autoId);
//        orderId = autoId;
//        getData(0);
//    }
//
//    @Override
//    public void onContentChange(String content) {
//        this.content = content;
////        onRefresh(refreshLayout);
//        getToastdialog(getActivity(),content);
//        getData(1);
//    }

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
