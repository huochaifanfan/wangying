package com.wytianxiatuan.wytianxia.view.classify;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.AllClassifyAdapter;
import com.wytianxiatuan.wytianxia.adapter.ClassifyLeftAdapter;
import com.wytianxiatuan.wytianxia.adapter.ClassifyRightAdapter;
import com.wytianxiatuan.wytianxia.bean.ClassifyBean;
import com.wytianxiatuan.wytianxia.bean.ClassifyRightSecondBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.main.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/10 0010.
 * 分类
 */

public class ClassifyFragment extends BaseFragment implements IMainView ,
        ClassifyLeftAdapter.OnItemClickListener,ClassifyRightAdapter.OnViewClickListener,
        AllClassifyAdapter.OnAllClassifyClickListener,OnRefreshLoadmoreListener{
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.image_search) ImageView imageSearch;
    @BindView(R.id.image_msg) ImageView imageMsg;
    @BindView(R.id.recyclerView_left) RecyclerView recyclerViewLeft;
    @BindView(R.id.recyclerView_right) RecyclerView recyclerViewRight;
    @BindView(R.id.empty_view) LinearLayout tvNoResult;
    @BindView(R.id.smartRefreshLayout) SmartRefreshLayout refreshLayout;
    private ClassifyLeftAdapter leftAdapter;
    private AllClassifyAdapter allClassifyAdapter;
    private ClassifyRightAdapter rightAdapter;
    private MainPresenter presenter;
    private MainPresenter classifyPresenter;
    private String url;
    private int page = 1;
    private boolean isAllClick = false;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify , container ,false);
        ButterKnife.bind(this ,view);
        init();
        return view;
    }

    private void init() {
        UiUtils.setParams(getActivity(),textTop,1);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            textTop.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white_color));
        }
        presenter = new MainPresenter(getActivity().getApplicationContext(),this);
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshLoadmoreListener(this);
        dataAll = new ArrayList<>();
        tvNoResult.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.background));
        getDataAll();
    }
    private void getDataAll(){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        url = Constants.allClassify;
        cancelable = presenter.getAllClassify();
    }
    @OnClick({R.id.rel_search})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.rel_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length ==1){
            if (args[0] instanceof List){
                List<ClassifyBean> data = (List<ClassifyBean>) args[0];
                setData(data);
            }
        }else if (args != null && args.length == 3){
            if (args[0] instanceof List && args[1] instanceof String && args[2] instanceof Integer){
                List<ClassifyRightSecondBean> data = (List<ClassifyRightSecondBean>) args[0];
                int flag = (int) args[2];
                setClassifyData(data,flag);
            }
        }
        hideLoading();
    }

    private void setData(List<ClassifyBean> data) {
        if (leftAdapter == null){
            leftAdapter = new ClassifyLeftAdapter(data ,getActivity(),this);
            recyclerViewLeft.setAdapter(leftAdapter);
        }
        allClassifyAdapter = null;
        tvNoResult.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        recyclerViewRight.setLayoutManager(new GridLayoutManager(getActivity(),3));
        if (allClassifyAdapter == null) {
            allClassifyAdapter = new AllClassifyAdapter(data, getActivity(), this);
            recyclerViewRight.setAdapter(allClassifyAdapter);
        }
    }
    private int autoId;
    private int itemPosition;
    @Override
    public void onItemClick(int autoId, int position) {
        this.itemPosition = position;
        if (classifyPresenter == null) classifyPresenter = new MainPresenter(getActivity().getApplication(),this);
        if (position >0){
            page = 1;
            refreshLayout.resetNoMoreData();
            this.autoId = autoId;
            getDataRight(autoId,0);
        }else {
            isAllClick = true;
            getDataAll();
        }
    }
    private void getDataRight(int autoId,int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        url = Constants.classify;
        cancelable = classifyPresenter.getClassify(autoId+"",page+"",flag);
    }
    private List<ClassifyRightSecondBean> dataAll;
    private void setClassifyData(List<ClassifyRightSecondBean> data,int flag){
        if (isAllClick) {
            isAllClick = false;
            rightAdapter = null;
        }
        if (flag != 2) dataAll = data;
        if (dataAll != null && dataAll.size()>0){
            refreshLayout.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            if (flag == 2 && data != null && data.size() > 0){
                dataAll.addAll(data);
            }else if (flag == 2 && data == null ||flag == 2 && data != null &&data.size() == 0){
                refreshLayout.finishLoadmoreWithNoMoreData();
            }
            if (rightAdapter == null) {
                recyclerViewRight.setLayoutManager(new LinearLayoutManager(getActivity()));
                rightAdapter = new ClassifyRightAdapter(dataAll, getActivity(), this);
                recyclerViewRight.setAdapter(rightAdapter);
            } else {
                rightAdapter.update(dataAll);
            }
            if (flag == 2 && data != null&&data.size() > 0) refreshLayout.finishLoadmore(200);
        }else {
            refreshLayout.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemViewClick(int autoId) {
        Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }

    @Override
    public void onAllClassifyClick(String autoId) {
        Intent intent = new Intent(getActivity(),GoodListActivity.class);
        intent.putExtra("autoId",autoId);
        startActivity(intent);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (itemPosition>0) {
            page++;
            getDataRight(autoId, 2);
        }else {
            refreshLayout.finishLoadmoreWithNoMoreData();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
