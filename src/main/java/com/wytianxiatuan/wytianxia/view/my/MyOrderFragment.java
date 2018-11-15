package com.wytianxiatuan.wytianxia.view.my;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.MyOrderAdapter;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;
import com.wytianxiatuan.wytianxia.bean.PaySureBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.base.PermissionsResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 我的订单
 */

public class MyOrderFragment extends BaseFragment implements MyOrderAdapter.onItemClickListener,IMainView,OnRefreshListener,
        OnRefreshLoadmoreListener ,PermissionsResultListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyDecoration myDecoration;
    private MyOrderAdapter adapter;
    private LinearLayout linearLayout;
    private String type;
    private MainPresenter presenter;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<MyOrderBean.Orders> dataAll;
    private String url;
    private PaySuccessReceiver paySuccessReceiver;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order ,container ,false);
        Bundle bundle = getArguments();
        type = bundle !=null?bundle.getString("type"):"";
        init(view);
        return view;
    }
    private void getData(int flag){
        if (!NetworkUtil.isNetWorkConnected(getActivity())){
            getToastdialog(getActivity(),Constants.NETWORK_ERROR);
            return;
        }
        showLoading(getActivity());
        url = Constants.orderManage+"?type="+type+"&page="+page;
        cancelable = presenter.orderManage(flag);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(0);
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(getActivity(),15));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        linearLayout = (LinearLayout) view.findViewById(R.id.empty_view);
        presenter = new MainPresenter(getActivity().getApplicationContext(),this);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        dataAll = new ArrayList<>();
        /**注册广播*/
        IntentFilter intentFilter = new IntentFilter("com.wytianxia.paySuccess");
        paySuccessReceiver = new PaySuccessReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(paySuccessReceiver,intentFilter);
    }
    public class PaySuccessReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getToastdialog(getActivity(),"支付成功！");
//            getData(0);
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(paySuccessReceiver);
        super.onDestroy();
    }

    @Override
    public void onItemClick(String orderId) {
        Intent intent = new Intent(getActivity(),MyOrderDetailActivity.class);
        intent.putExtra("orderId",orderId);
        startActivity(intent);
    }
    @Override
    public void cancelOrder(final String orderId) {
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0, UiUtils.dipToPx(getActivity(),295));
        TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
        textContent.setText("您确定要取消该订单吗？");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.cancel();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null){
                    url = Constants.cancelOrder;
                    cancelable = presenter.cancelOrder(orderId,0);
                }
                if (dialog != null) dialog.cancel();
            }
        });
    }

    /**
     * 取消退款申请
     * @param orderId
     */
    @Override
    public void cancelApply(final String orderId) {
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0, UiUtils.dipToPx(getActivity(),295));
        TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
        textContent.setText("您确定要取消退款申请吗？");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.cancel();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    url = Constants.cancelApply;
                    cancelable = presenter.cancelOrder(orderId, 2);
                }
                if (dialog != null) dialog.cancel();
            }
        });
    }

    @Override
    public void sureTakeGood(final String goodId) {
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0, UiUtils.dipToPx(getActivity(),295));
        TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
        textContent.setText("您确定要确认收货吗？");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.cancel();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    url = Constants.sureTakeGood;
                    cancelable = presenter.cancelOrder(goodId ,3);
                }
                if (dialog != null) dialog.cancel();
            }
        });
    }

    /**
     * 发起订单支付
     * @param orderNumber
     */
    @Override
    public void paySure(String orderNumber) {
        url = Constants.orderManagePaySure;
        if (presenter != null)presenter = new MainPresenter(getActivity().getApplicationContext(),this);
        showLoading(getActivity());
        cancelable = presenter.rightBuy(orderNumber);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length==2){
            if (args[0] instanceof MyOrderBean && args[1] instanceof Integer){
                MyOrderBean bean = (MyOrderBean) args[0];
                int flag = (int) args[1];
                if (bean !=null){
                    setData(bean.getOrderses(),flag);
                }
            }else if (args[0] instanceof String && args[0].equals("cancel")){
                if (args[1] instanceof Integer){
                    int flag = (int) args[1];
                    if (flag == 0) {
                        /**取消订单*/
                        getToastdialog(getActivity(), "订单取消成功！");
                    }else if (flag == 2){
                        /**取消退款申请成功*/
                        getToastdialog(getActivity(),"申请成功");
                    }else if (flag == 3){
                        /**确认收获成功*/
                        getToastdialog(getActivity(),"确认收货成功");
                    }
                    onRefresh(refreshLayout);
                }
            }
        }else if (args != null && args.length ==1){
            if (args[0] instanceof PaySureBean){
                PaySureBean bean = (PaySureBean) args[0];
                payBuyWeiChat(bean);
                hideLoading();
            }
        }
        hideLoading();
    }
    private void payBuyWeiChat(PaySureBean bean) {
        if (bean == null)return;
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepayid();
        request.packageValue = bean.getPackageType();
        request.nonceStr = bean.getNoncestr();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        if (MainApplication.weiChatApi != null) {
            MainApplication.weiChatApi.sendReq(request);
        }
    }
    private void setData(List<MyOrderBean.Orders> data, int flag) {
        if (flag == 0 || flag ==1)dataAll = data;
        if (dataAll != null && dataAll.size()>0){
            refreshLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            if (flag == 2 && data != null && data.size() > 0){
                dataAll.addAll(data);
            }else if (flag == 2 && data == null ||flag == 2 && data != null &&data.size() == 0){
                refreshLayout.finishLoadmoreWithNoMoreData();
            }
            if (adapter == null){
                adapter = new MyOrderAdapter(dataAll,getActivity(),this);
                recyclerView.setAdapter(adapter);
            }else {
                adapter.reflashData(dataAll);
            }
            if (flag == 1)refreshLayout.finishRefresh(300,true);
            if (flag == 2 && data != null&&data.size() > 0) refreshLayout.finishLoadmore(200);
        }else {
            refreshLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
//        hideLoading();
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
    public void onPermissionGranted() {
    }

    @Override
    public void onPermissionDenied() {
        getToastdialog(getActivity(),"拨打电话需要相关权限，请检查权限设置");
    }
}
