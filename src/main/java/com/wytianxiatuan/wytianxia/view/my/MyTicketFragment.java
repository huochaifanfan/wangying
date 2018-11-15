package com.wytianxiatuan.wytianxia.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MyTicketAdapter;
import com.wytianxiatuan.wytianxia.bean.MyTicketBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class MyTicketFragment extends BaseFragment implements IMainView{
    private String type;
    private String url;
    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private MyTicketAdapter adapter;
    private LinearLayout tvNoResult;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket,container,false);
        Bundle args = getArguments();
        type = args != null ? args.getString("type"):"";
        init(view);
        return view;
    }

    private void init(View view) {
        if ("未使用".equals(type)){
            url = Constants.canUseTicket;
        }else {
            url = Constants.notUseTicket;
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tvNoResult = (LinearLayout) view.findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(getActivity(),20)));
        presenter = new MainPresenter(getActivity().getApplicationContext(),this);
    }

    @Override
    protected void initData() {
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        cancelable = presenter.getTicket();
    }
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof List){
                List<MyTicketBean> data = (List<MyTicketBean>) args[0];
                setData(data);
            }
        }
        hideLoading();
    }

    public void setData(List<MyTicketBean> data) {
        if (data != null && data.size()>0){
            tvNoResult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new MyTicketAdapter(getActivity(),data);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        }
    }
}
