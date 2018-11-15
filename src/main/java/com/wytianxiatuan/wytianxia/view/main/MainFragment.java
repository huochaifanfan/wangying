package com.wytianxiatuan.wytianxia.view.main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MainAdapter;
import com.wytianxiatuan.wytianxia.bean.LocationBean;
import com.wytianxiatuan.wytianxia.bean.MainBean;
import com.wytianxiatuan.wytianxia.city.Activity01;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.LocationUtils;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.base.PermissionsResultListener;
import com.wytianxiatuan.wytianxia.view.classify.GoodListActivity;
import com.wytianxiatuan.wytianxia.view.classify.GoodsDetailActivity;
import com.wytianxiatuan.wytianxia.view.shop.MerchantShops;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by liuju on 2018/1/11.
 */

public class MainFragment extends BaseFragment implements IMainView,OnRefreshListener,OnLoadmoreListener,
        MainAdapter.OnViewClickListener,PermissionsResultListener,LocationUtils.LocationResultListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MainAdapter adapter;
    private MainPresenter presenter;
    private TextView tvTop,tvTitle;
    private int distance;
    private String url;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<MainBean.Recommand> dataAll;
    private MyRecveier myRecveier;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main , container ,false);
        init(view);
        return view;
    }
    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tvTop = (TextView) view.findViewById(R.id.tv_top);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        presenter = new MainPresenter(getActivity().getApplicationContext() ,this);
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener());
        UiUtils.setParams(getActivity(),tvTop,0);
        distance = UiUtils.dipToPx(getActivity(),106)- CommonUtil.getStatusBarHeight(getActivity());
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));/**经典官方主题*/
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        dataAll = new ArrayList<>();
        getTop();
        myRecveier = new MyRecveier();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wytianxia.location");
        intentFilter.addAction("reflash");
        getActivity().registerReceiver(myRecveier,intentFilter);
        performRequestPermissions("网赢天下请求定位权限", new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 200, this);
    }

    @Override
    public void onPermissionGranted() {
        LocationUtils locationUtils = LocationUtils.getInstance(getActivity().getApplicationContext(),this);
        locationUtils.getLocation();
    }
    @Override
    public void onPermissionDenied() {
        Constants.currentCity = "";
        if (adapter != null) adapter.reflash(bean);
    }
    private void location(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        /**获取当前的经纬度*/
        if (location != null){
            double mLng = location.getLongitude();
            double mLat = location.getLatitude();
            /**获取地理位置信息*/
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> locationList;
            try {
                locationList = geocoder.getFromLocation(mLat,mLng, 1);
                Address address = locationList.get(0);//得到Address实例
                String locality = address.getAdminArea();//得到城市名称
                if (locality != null && locality.length()>1&& locality.endsWith("市")) locality = locality.substring(0,locality.length()-1);
                Constants.currentCity = locality;
                if (adapter != null) handler.sendEmptyMessage(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200)adapter.reflash(bean);
        }
    };

    @Override
    public void locationResult(LocationBean locationBean) {
        if (locationBean != null){
            Constants.currentCity = locationBean.getCity();
            if (adapter != null) adapter.reflashLocation();
        }
    }

    public class MyRecveier extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "reflash".equals(intent.getAction())){
                getTop();
            }else if (intent != null &&"com.wytianxia.location".equals(intent.getAction())){
                if (adapter != null) adapter.reflashLocation();
            }
        }
    }
    @Override
    public void onDestroy() {
        if (getActivity() != null)getActivity().unregisterReceiver(myRecveier);
        LocationUtils.getInstance(getActivity().getApplicationContext(),this).stopLocation();
        super.onDestroy();
    }

    private void getTop(){
        showLoading(getActivity());
        url = Constants.mainTop;
        cancelable = presenter.getMainTopData();
    }
    @Override
    public String getUrl() {
        return url;
    }
    private MainBean bean;
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length ==1){
            if (args[0] instanceof MainBean){
                bean = (MainBean) args[0];
                setData(bean);
                getData(0);
            }
        }else if (args != null && args.length == 2){
            if (args[0] instanceof List && args[1] instanceof Integer){
                List<MainBean.Recommand> data = (List<MainBean.Recommand>) args[0];
                int flag = (int) args[1];
                setBottomData(data,flag);
            }
        }
        hideLoading();
    }
    private boolean isFirst =true;
    /**
     * 家在底部数据
     * @param data
     * @param flag
     */
    private void setBottomData(List<MainBean.Recommand> data , int flag){
        if (flag ==0 || flag ==1) dataAll = data;
        if (flag ==2 && data != null && data.size()>0)dataAll.addAll(data);
        if (flag ==2){
            if (data == null || data!= null&&data.size() ==0)refreshLayout.finishLoadmoreWithNoMoreData();
        }
        if (adapter != null) adapter.setBottom(dataAll);
        if (flag == 0)refreshLayout.finishRefresh(300,true);
        if (flag == 2 && data != null&&data.size() > 0) refreshLayout.finishLoadmore(200);
        /**请求定位*/
        if (isFirst){
            isFirst =false;
//            performRequestPermissions("网赢天下请求定位权限", new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION}, 200, this);
        }
    }

    private void getData(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        url = Constants.mainBottom;
        cancelable = presenter.getMainBottom(flag ,page+"");
    }
    private void setData(MainBean mainBean){
        if (adapter == null){
            adapter = new MainAdapter(getActivity(),mainBean,this);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.reflash(mainBean);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getData(2);
    }

    @Override
    public void analysisFailer(String info) {
        super.analysisFailer(info);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        refreshlayout.resetNoMoreData();
        getTop();
    }

    @Override
    public void onBannerClick(String name, String link,String linkType,String redirectType,String redirectId) {
        /**首先判断linkType*/
        if ("NONE".equals(linkType)){
            return;
        }else if ("HREF".equals(linkType)){
            /**跳转链接*/
            Intent intent = new Intent(getActivity(), MainWebView.class);
            intent.putExtra("name", name);
            intent.putExtra("url", CommonUtil.urlDecode(link));
            startActivity(intent);
        }else if ("DIRECT".equals(linkType)){
            /**应用内跳转 跳转商品详情 或者店铺*/
            Intent intent = new Intent();
            if ("SHOP".equals(redirectType)){
                /**去店铺*/
                intent.setClass(getActivity(),MerchantShops.class);
                intent.putExtra("id",redirectId);
            }else if ("DETAIL".equals(redirectType)){
                intent.setClass(getActivity(),GoodsDetailActivity.class);
                intent.putExtra("id",Integer.valueOf(redirectId));
            }
            startActivity(intent);
        }
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        changePage = (PageChange) context;
    }
    private PageChange changePage;
    public interface PageChange{
        void pageChange(int position);
    }
    @Override
    public void onViewClick(String tag) {
        Intent intent = new Intent();
        if ("location".equals(tag)){
            intent.setClass(getActivity(), Activity01.class);
            startActivityForResult(intent ,200);
        }else if ("search".equals(tag)){
            intent.setClass(getActivity(),SearchActivity.class);
            startActivity(intent);
        }else if ("msg".equals(tag)){
            /**消息管理*/
        }else if ("dymore".equals(tag)){
            /**动态*/
            if (changePage != null) changePage.pageChange(2);
        }else if ("develop".equals(tag)){

        }else if ("zhaopin".equals(tag) && bean != null){
            intent.setClass(getActivity(),MainWebView.class);
            intent.putExtra("name","招聘");
            intent.putExtra("url",CommonUtil.urlDecode(bean.getRequireMentLink()));
            startActivity(intent);
        }else if ("siwang".equals(tag)|| "temai".equals(tag) ||"quyang".equals(tag)){
            intent.setClass(getActivity(),GoodListActivity.class);
            intent.putExtra("main",tag);
            startActivity(intent);
        }else if ("jiagong".equals(tag)&& bean != null){
            intent.setClass(getActivity(),GoodListActivity.class);
            intent.putExtra("autoId",bean.getJiagongId());
            startActivity(intent);
        }else if ("peijian".equals(tag) && bean != null){
            intent.setClass(getActivity(),GoodListActivity.class);
            intent.putExtra("autoId",bean.getPeijianId());
            startActivity(intent);
        }
    }

    /**
     * 分类点击
     * @param autoId
     */
    @Override
    public void onClassifyClick(int autoId) {
        Intent intent = new Intent(getActivity(), GoodListActivity.class);
        intent.putExtra("autoId",String.valueOf(autoId));
        startActivity(intent);
    }

    @Override
    public void onDevelopClick(String link) {
        Intent intent = new Intent();
        intent.setClass(getActivity(),MainWebView.class);
        intent.putExtra("name","丝网发展史");
        intent.putExtra("url",CommonUtil.urlDecode(link));
        startActivity(intent);
    }

    @Override
    public void onDynamicClick(String link) {
        Intent intent = new Intent();
        intent.setClass(getActivity(),MainWebView.class);
        intent.putExtra("name","动态详情");
        intent.putExtra("url",CommonUtil.urlDecode(link));
        startActivity(intent);
    }

    @Override
    public void onDetailClick(int autoId) {
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }

    @Override
    public void onShopClick(int autoId,String name,String shopUrl) {
        Intent intent = new Intent(getActivity(), MerchantShops.class);
        intent.putExtra("id",autoId+"");
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 500){
            if (data != null){
                String city = data.getStringExtra("city");
                if (city != null && city.length()>1 && city.endsWith("市"))city = city.substring(0,city.length()-1);
                Constants.currentCity = city;
                if (adapter != null) adapter.reflash(bean);
            }
        }
    }

    public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener{
        int totalDy = 0;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int scrollY) {
            super.onScrolled(recyclerView, dx, scrollY);
            totalDy-= -scrollY;
            float alpha;
            alpha = (float) (totalDy*1.0/distance);
            if (alpha >= 1){
                alpha = 1;
            }
            tvTop.setAlpha(alpha);
            tvTitle.setAlpha(alpha);
        }
    }
}
