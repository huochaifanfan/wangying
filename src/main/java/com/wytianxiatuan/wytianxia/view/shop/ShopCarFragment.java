package com.wytianxiatuan.wytianxia.view.shop;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.ShopTotalAdapter;
import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.bean.ShopBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;
import com.wytianxiatuan.wytianxia.view.classify.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class ShopCarFragment extends BaseFragment implements IMainView ,
        ShopTotalAdapter.OnViewClickListener{
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.tv_top_title) TextView tvTopTitle;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.image_selectAll) CheckBox imageSeclectAll;
    @BindView(R.id.tv_sure) TextView tvSure;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_delete) TextView tvDelete;
    @BindView(R.id.shop_empty) LinearLayout linearEmptyView;
    private LinearLayoutManager layoutManager;
    private MyDecoration myDecoration;
    private ShopTotalAdapter adapter;
    private MainPresenter presenter;
    private String url;
    private double totalPrice;
    private List<String> selectIdsAll;
    private ShopCarLocalBroadCastReceiver shopCarLocalBroadCastReceiver;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop , container ,false);
        ButterKnife.bind(this , view);
        init();
        return view;
    }
    private void getData(){
        showLoading(getActivity());
        url = Constants.shopCarList;
        cancelable = presenter.shopCarList();
    }
    private void init() {
        UiUtils.setParams(getActivity(),textTop,1);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            textTop.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white_color));
        }
        layoutManager = new LinearLayoutManager(getActivity());
        myDecoration = new MyDecoration(getActivity(),R.color.background,LinearLayoutManager.HORIZONTAL,UiUtils.dipToPx(getActivity(),15));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        /**注册广播 接收到广播 刷新数据*/
        IntentFilter intentFilter = new IntentFilter("com.wytianxia.shopCar");
        shopCarLocalBroadCastReceiver = new ShopCarLocalBroadCastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(shopCarLocalBroadCastReceiver,intentFilter);
        selectIdsAll = new ArrayList<>();
        if (presenter == null) presenter = new MainPresenter(getActivity().getApplicationContext(),this);
    }
    private class ShopCarLocalBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
//            getData();
        }
    }
    @Override
    public void onDestroy() {
        /**取消广播注册*/
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(shopCarLocalBroadCastReceiver);
        super.onDestroy();
    }

    @OnClick({R.id.image_msg,R.id.image_selectAll,R.id.tv_sure})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.image_msg:
                break;

            case R.id.image_selectAll:
                /**全选*/
                if (shopBean != null && shopBean.getShopList() != null && shopBean.getShopList().size()>0){
                    List<ShopBean.Shop> shopList = shopBean.getShopList();
                    if (imageSeclectAll.isChecked()){
                        for (int i =0;i<shopList.size();i++){
                            shopList.get(i).setSelect(true);
                            for (int j =0;j<shopList.get(i).getShopListses().size();j++){
                                shopList.get(i).getShopListses().get(j).setSelect(true);
                            }
                        }
                    }else {
                        for (int i =0;i<shopList.size();i++){
                            shopList.get(i).setSelect(false);
                            for (int j =0;j<shopList.get(i).getShopListses().size();j++){
                                shopList.get(i).getShopListses().get(j).setSelect(false);
                            }
                        }
                    }
                    if (adapter != null) adapter.reflashData(shopBean);
                }
                break;

            case R.id.tv_sure:
                /**结算或者保存*/
                if (totalPrice >0 && Constants.selectIds != null && Constants.selectIds.size()>0){
                    String str="";
                    for (int i=0;i<Constants.selectIds.size();i++){
                        str += "carts[]="+Constants.selectIds.get(i)+"&";
                    }
                    str = str.substring(0,str.length()-1).trim();
                    Intent intent = new Intent(getActivity(),RightBuyActivity.class);
                    intent.putExtra("params",str);
                    intent.putStringArrayListExtra("carts", (ArrayList<String>) Constants.selectIds);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public String getUrl() {
        return url;
    }
    private ShopBean shopBean;
    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof ShopBean){
                ShopBean data = (ShopBean) args[0];
                this.shopBean = data;
                setData(data);
            }else if (args[0] instanceof String && "delete".equals(args[0])){
                /**删除购物车商品*/
                getData();
                getToastdialog(getActivity(),"删除成功!");
            }else if (args.length == 2){
                if ("minusOrPlus".equals(args[0])&& args[1] instanceof Integer){
                    getData();
                }
            }else if (args[0] instanceof String && "clear".equals(args[0])){
                getData();
            }
        }
    }
    private OnShopCarGoodCountChangeListener onShopCarGoodCountChangeListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onShopCarGoodCountChangeListener = (OnShopCarGoodCountChangeListener) context;
    }

    public interface OnShopCarGoodCountChangeListener{
        void onShopCarGoodCountChange(int count);
    }
    private int goodCount;
    private void setData(ShopBean bean){
        goodCount = bean != null?bean.getGoodsCount():0;
        totalPrice = 0.0;
        Constants.selectIds = new ArrayList<>();
        tvTotal.setText("合计：￥"+(totalPrice));
        if (bean == null) return;
        if (bean.getShopList()!=null && bean.getShopList().size()>0||
                bean.getOverTimeList() != null && bean.getOverTimeList().size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            linearEmptyView.setVisibility(View.GONE);
            if (adapter == null){
                adapter = new ShopTotalAdapter(bean ,getActivity(),this);
                recyclerView.setAdapter(adapter);
            }else {
                adapter.reflashData(bean);
            }
        }else {
            recyclerView.setVisibility(View.GONE);
            linearEmptyView.setVisibility(View.VISIBLE);
        }

//        if (onShopCarGoodCountChangeListener != null) onShopCarGoodCountChangeListener.onShopCarGoodCountChange(goodCount);
        hideLoading();
    }
    @Override
    public void deleteGood(final String autoId) {
        final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0,UiUtils.dipToPx(getActivity(),295));
        TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
        textContent.setText("您确定要删除该商品吗？");
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
                url = Constants.deleteShopCarGood;
                cancelable = presenter.deleteShopCarGood(autoId);
                if (dialog != null) dialog.cancel();
            }
        });
    }
    @Override
    public void minusOrPlus(String goodId, String minusOrPlus,int position) {
        url = Constants.minusOrPlusGood;
        cancelable = presenter.minusOrPlusGood(goodId,minusOrPlus);
    }

    @Override
    public void totalPrice(double price) {
        if (Constants.selectIds!= null && Constants.selectIds.size()>0){
            totalPrice = 0;
            for (int i=0;i<shopBean.getShopList().size();i++){
                List<ShopBean.ShopLists> shopListses = shopBean.getShopList().get(i).getShopListses();
                for (int j=0;j<shopListses.size();j++){
                    if (Constants.selectIds.contains(shopListses.get(j).getShopGoodId())){
                        totalPrice+=shopListses.get(j).getShopPrice()*shopListses.get(j).getShopAmount();
                    }
                }
            }
        }else {
            totalPrice =0;
        }
        tvTotal.setText("合计：￥"+(totalPrice));
    }

    @Override
    public void clearGoods(String ids) {
        url = Constants.clearInvaildGoods+"?"+ids;
        if (presenter != null) cancelable = presenter.clearShopCar();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void getDataFailer(BaseBean bean) {
    }

    @Override
    public void checkChange(int flag) {
        if (flag == 1){
            imageSeclectAll.setChecked(true);
        }else {
            imageSeclectAll.setChecked(false);
        }
    }

    @Override
    public void onDetailClick(int goodId) {
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("id",goodId);
        startActivity(intent);
    }
}
