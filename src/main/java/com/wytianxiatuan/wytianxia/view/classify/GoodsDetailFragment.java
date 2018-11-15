package com.wytianxiatuan.wytianxia.view.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.GoodsDetailAdapter;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.overrideview.banner.Banner;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.BannerImageLoader;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/16 0016.
 * 商品详情
 */

public class GoodsDetailFragment extends BaseFragment implements IMainView{
    private int autoId;
    @BindView(R.id.bannerLayout) Banner bannerLayout;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_market_price)TextView tvMarketPrice;
    @BindView(R.id.tv_current_price) TextView tvCurrentPrice;
    @BindView(R.id.tv_des) TextView tvDes;
    @BindView(R.id.shop_image_icon) ImageView shopImageView;
    @BindView(R.id.tv_shopName) TextView tvShopName;
    @BindView(R.id.tv_goodsCount) TextView tvGoodsCount;
    @BindView(R.id.btn_goShop) Button btnGoShop;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private MainPresenter presenter;
    private List<String> bannerList;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_detail,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {
        Bundle bundle = getArguments();
        autoId = bundle != null?bundle.getInt("autoId"):0;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new MainPresenter(getActivity().getApplicationContext(),this);
        cancelable = presenter.getGoodDetail();
    }

    @Override
    public String getUrl() {
        return Constants.goodsDetail+"?id="+autoId;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof GoodDetailBean){
               GoodDetailBean bean = (GoodDetailBean) args[0];
                setData(bean);
            }
        }
    }

    private void setData(GoodDetailBean bean) {
        if (bean == null) return;
        bannerList = bean.getImagePicture();
        if (bannerList != null && bannerList.size()>0){
            bannerLayout.setImageLoader(new BannerImageLoader());
            bannerLayout.setImages(bannerList);
            bannerLayout.start();
        }
        tvName.setText(bean.getGoodName());
        tvMarketPrice.setText(bean.getCurrentPrice());
        tvDes.setText(bean.getNotice());
        Glide.with(getActivity()).load(bean.getShopUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(shopImageView);
        tvShopName.setText(bean.getShopName());
        tvGoodsCount.setText("商品数目："+bean.getGoodCount());
        GoodsDetailAdapter adapter = new GoodsDetailAdapter(getActivity(),bean,null);
        recyclerView.setAdapter(adapter);
    }
}
