package com.wytianxiatuan.wytianxia.view.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.GoodsListAdapter;
import com.wytianxiatuan.wytianxia.adapter.TopSelectorAdapter;
import com.wytianxiatuan.wytianxia.adapter.TopTextAdapter;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;
import com.wytianxiatuan.wytianxia.city.Activity01;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.main.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/27.
 * 商品列表
 */

public class GoodListActivity extends BaseActivity implements TopTextAdapter.OnViewClickListener,IGoodListView,
        GoodsListAdapter.OnItemClickListener,OnRefreshListener,OnRefreshLoadmoreListener ,TopSelectorAdapter.OnTopClickListener{
    private String content;
    @BindView(R.id.et_search) TextView etSearch;
    @BindView(R.id.tv_location) TextView tvLocation;
    @BindView(R.id.viewPagerIndicator) RecyclerView indicator;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.linear_location) LinearLayout linearLocation;
    @BindView(R.id.recyclerView_list) RecyclerView recyclerViewList;
    @BindView(R.id.smartRefreshLayout) SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_view) LinearLayout tvNoResult;
    private List<GoodsListBean.Category> category;
    private List<GoodsListBean.OrderList> orderList;
    private MainPresenter presenter;
    private String orderId;
    private String catId="0";
    private int page = 1;
    private GoodsListAdapter adapter;
    private boolean isFirst = true;
    private List<GoodsListBean.GoodsList> dataAll;
    private String main;
    private String autoId;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_good_list);
        ButterKnife.bind(this);
        content = getIntent().getStringExtra("content");
        main = getIntent().getStringExtra("main");
        autoId = getIntent().getStringExtra("autoId");
        init();
        getData(0,0);
    }
    private void getData(int flag,int isa){
        if (!NetworkUtil.isNetWorkConnected(this)){
            getToastdialog(this , Constants.NETWORK_ERROR);
            return;
        }
        if (isa == 0)showLoading(this);
        if ("siwang".equals(main)){
            cancelable = presenter.getGoodList(flag ,1);
        }else if ("temai".equals(main)){
            cancelable = presenter.getGoodList(flag ,2);
        }else if ("quyang".equals(main)){
            cancelable = presenter.getGoodList(flag ,3);
        }else {
            cancelable = presenter.getGoodList(flag,0);
        }
    }
    private void init() {
        presenter = new MainPresenter(getApplicationContext(),this);
        linearLocation.setVisibility(View.VISIBLE);
        dataAll = new ArrayList<>();
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewList.addItemDecoration(new MyDecoration(this,R.color.divider_color,LinearLayoutManager.HORIZONTAL,UiUtils.dipToPx(this ,1)));
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        tvLocation.setText(Constants.currentCity);
    }
    private void initFragment(GoodsListBean bean){
        if (bean != null){
            category = bean.getCategories();
            orderList = bean.getOrderLists();
            orderId = orderList.get(0).getOrderId();
        }
        if (category != null && category.size()>0){
            GoodsListBean.Category all = new GoodsListBean.Category();
            all.setName("全部");
            all.setAutoId("0");
            category.add(0,all);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            indicator.setLayoutManager(layoutManager);
            TopSelectorAdapter adapter = new TopSelectorAdapter(category,this,this);
            indicator.setAdapter(adapter);
            if (!"".equals(autoId)&&autoId !=null){
                for (int i = 0;i<category.size();i++){
                    if (String.valueOf(autoId).equals(category.get(i).getAutoId())){
                        position = i;
                        catId = autoId;
                    }
                }
                getData(1,1);
                if (position >4) indicator.scrollToPosition(position-4);
                adapter.update(position);
            }
        }
        if (orderList != null && orderList.size()>0) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,orderList.size());
            recyclerView.setLayoutFrozen(true);
            recyclerView.setLayoutManager(layoutManager);
            TopTextAdapter textAdapter = new TopTextAdapter(orderList,this,this);
            recyclerView.setAdapter(textAdapter);
        }


        Gson gson = new Gson();
        gson.toJson(new Object());

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
        return catId;
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
                setData(bean,flag);
            }
        }
    }

    /**
     * 设置数据
     * @param bean
     * @param flag
     */
    private void setData(GoodsListBean bean ,int flag){
        if (bean == null) return;
        if (isFirst){
            isFirst = false;
            initFragment(bean);
        }
        List<GoodsListBean.GoodsList> data = bean.getGoodsLists();
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
                adapter = new GoodsListAdapter(this, dataAll, this);
                recyclerViewList.setAdapter(adapter);
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

    /**
     * item点击事件
     * @param autoId
     */
    @Override
    public void onItemCLick(int autoId) {
        Intent intent = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }
    @Override
    public void onItemViewClick(String orderId) {
        this.orderId = orderId;
        getData(1,0);
    }
    @OnClick({R.id.imageView_back,R.id.linear_search,R.id.linear_location})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.linear_search:
                Intent intent = new Intent(this , SearchActivity.class);
                intent.putExtra("where","list");
                startActivityForResult(intent ,200);
                break;

            case R.id.linear_location:
                /**城市选择*/
                Intent intent1 = new Intent(this, Activity01.class);
                startActivityForResult(intent1 ,200);
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getData(2,0);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        refreshlayout.resetNoMoreData();
        getData(1,0);
    }

    /**
     * 选择不同分类的点击
     * @param autoId
     */
    @Override
    public void onTopClick(String autoId) {
        this.catId = autoId;
        getData(1,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300){
            if (data != null){
                String content = data.getStringExtra("content");
                /**通知fragment重新请求接口*/
                this.content = content;
                getData(1,0);
            }
        }else if (requestCode == 200 && resultCode == 500){
            if (data != null) {
                String city = data.getStringExtra("city");
                tvLocation.setText(city);
                Constants.currentCity = city;
                /**发送广播 更新首页的城市定位*/
                Intent intent = new Intent();
                intent.setAction("com.wytianxia.location");
                this.sendBroadcast(intent);
            }
        }
    }
}
