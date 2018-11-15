package com.wytianxiatuan.wytianxia.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MyCenterAdapter;
import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.bean.MyCenterBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.main.MainWebView;
import com.wytianxiatuan.wytianxia.view.setting.SettingActivity;

/**
 * Created by Administrator on 2018/1/10 0010.
 * 个人中心
 */

public class MyFragment extends BaseFragment implements MyCenterAdapter.OnViewClickListener,IMainView{
    private RecyclerView recyclerView;
    private MyCenterAdapter adapter;
    private LinearLayoutManager layoutManager;
    private MyDecoration myDecoration;
    private MainPresenter presenter;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my , container ,false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(getActivity(),10));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) presenter = new MainPresenter(getActivity().getApplicationContext(),this);
        cancelable = presenter.myCenter();
    }

    @Override
    public void onViewClick(int position) {
        Intent intent = new Intent();
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case 3:
                break;
            case 5:
                intent.setClass(getActivity(),MyOrderActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent.setClass(getActivity(),MyOrderActivity.class);
                intent.putExtra("pagerPos",1);
                startActivity(intent);
                break;
            case 7:
                intent.setClass(getActivity(),MyOrderActivity.class);
                intent.putExtra("pagerPos",2);
                startActivity(intent);
                break;
            case 8:
                intent.setClass(getActivity(),MyOrderActivity.class);
                intent.putExtra("pagerPos",3);
                startActivity(intent);
                break;
            case 9:
                intent.setClass(getActivity(),MyOrderActivity.class);
                intent.putExtra("pagerPos",5);
                startActivity(intent);
                break;
            case 10:
                intent.setClass(getActivity(),MyOrderCollectActivity.class);
                startActivity(intent);
                break;
            case 11:
                /**帮助中心*/
                if (bean != null){
                    intent.setClass(getActivity(),MainWebView.class);
                    intent.putExtra("name", bean.getHelpTitle());
                    intent.putExtra("url", CommonUtil.urlDecode(bean.getHelpLink()));
                    startActivity(intent);
                }
                break;
            case 12:
                intent.setClass(getActivity(),SelectAddressActivity.class);
                startActivity(intent);
                break;
            case 13:
                intent.setClass(getActivity(),ToBeSellerActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public String getUrl() {
        return Constants.myCenter;
    }
    private MyCenterBean bean;
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof MyCenterBean){
                MyCenterBean bean = (MyCenterBean) args[0];
                this.bean = bean;
                setData(bean);
            }
        }
    }

    @Override
    public void getDataFailer(BaseBean bean) {
    }

    private void setData(MyCenterBean bean) {
        if (adapter == null){
            adapter = new MyCenterAdapter(getActivity(),bean,this);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.reflash(bean);
        }
    }
}
