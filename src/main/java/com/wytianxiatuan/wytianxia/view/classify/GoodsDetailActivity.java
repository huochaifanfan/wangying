package com.wytianxiatuan.wytianxia.view.classify;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.CommentAdapter;
import com.wytianxiatuan.wytianxia.adapter.DetailDialogAdapter;
import com.wytianxiatuan.wytianxia.adapter.GoodsDetailAdapter;
import com.wytianxiatuan.wytianxia.adapter.ShopNoticeAdapter;
import com.wytianxiatuan.wytianxia.bean.CommentBean;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.overrideview.banner.Banner;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.BannerImageLoader;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.base.PermissionsResultListener;
import com.wytianxiatuan.wytianxia.view.login.LoginActivity;
import com.wytianxiatuan.wytianxia.view.shop.MerchantShops;
import com.wytianxiatuan.wytianxia.view.shop.RightBuyActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/26 0026.
 * 商品详情
 */

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener,IMainView,
        GoodsDetailAdapter.OnPhoneClickListener,PermissionsResultListener,DetailDialogAdapter.OnDialogClickListener{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.linear_top) LinearLayout linearTop;
    @BindView(R.id.image_collect) ImageView imageCollect;
    @BindView(R.id.tv_add_shopCar) TextView tvAddShopCar;
    @BindView(R.id.tv_right_buy) TextView tvRightBuy;
    @BindView(R.id.rel_top_header) RelativeLayout relHeader;
    @BindView(R.id.rel_bottom) RelativeLayout relBottom;
    @BindView(R.id.tv_pingjia) TextView tvJudge;
    @BindView(R.id.tv_order_know) TextView tvOrderKnow;
    @BindView(R.id.tv_detail) TextView tvDetail;

    @BindView(R.id.bannerLayout) Banner bannerLayout;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_market_price)TextView tvMarketPrice;
    @BindView(R.id.tv_current_price) TextView tvCurrentPrice;
    @BindView(R.id.tv_des) TextView tvDes;
    @BindView(R.id.shop_image_icon) ImageView shopImageView;
    @BindView(R.id.tv_shopName) TextView tvShopName;
    @BindView(R.id.tv_goodsCount) TextView tvGoodsCount;
    @BindView(R.id.btn_goShop) Button btnGoShop;
    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;
    @BindView(R.id.tv_pingjia_bottom) TextView tvJudgeBottom;
    @BindView(R.id.tv_order_know_bottom) TextView tvOrderKnowBottom;
    @BindView(R.id.tv_detail_bottom) TextView tvDetailBottom;
    @BindView(R.id.empty_view) LinearLayout linearEmptyView;
    @BindView(R.id.linear_tag) LinearLayout linearTag;
    @BindView(R.id.btn_isCash) Button btnIsCash;
    @BindView(R.id.btn_isQuYang) Button btnIsQuYang;
    @BindView(R.id.btn_isPromote) Button btnIsPromote;

    private int autoId;
    private GoodsDetailAdapter adapter;
    private MainPresenter presenter;
    private int distance;
    private boolean isCollect;
    private String url;
    private GoodDetailBean bean;
    private int page=1;
    private CommentAdapter commentAdapter;
    private Handler handler = new Handler();
    private int redColor;
    private int blackColor;
    private List<CommentBean> commentData;
    private View emptyView;
    private String shopNotice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        autoId = getIntent().getIntExtra("id",0);/**标的的id*/
        init();
    }
    private void init() {
        presenter = new MainPresenter(getApplicationContext(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) bannerLayout.getLayoutParams();
        layoutParams.height = CommonUtil.getScreenInfo(this)[0];
        bannerLayout.setLayoutParams(layoutParams);
//        distance = UiUtils.dipToPx(this,306)- CommonUtil.getStatusBarHeight(this);
//        recyclerView.addOnScrollListener(new RecyclerViewScrollListener());
        handler.post(runnable);
        emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty,null,false);
        redColor = ContextCompat.getColor(this,R.color.main_red);
        blackColor = ContextCompat.getColor(this,R.color.black_color);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] viewLocation = new int[2];
                relBottom.getLocationInWindow(viewLocation);
                int viewY = viewLocation[1];
//                Log.i("adapter","adapterViewX:"+viewX+"adapterViewY:"+viewY);
                if (viewY<=scrollYy){
                    if (relHeader.getVisibility() == View.GONE){
                        relHeader.setVisibility(View.VISIBLE);
                    }
                }else {
                    if (relHeader.getVisibility() == View.VISIBLE){
                        relHeader.setVisibility(View.GONE);
                    }
                }
            }
        });
        getData();
    }
    private int scrollYy;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            View v = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            if (v!= null && v.getWidth()>0&&v.getHeight()>0){
                int[] viewLocation = new int[2];
                relHeader.getLocationInWindow(viewLocation);
                int viewX = viewLocation[0];
                int viewY = viewLocation[1];
                scrollYy = viewY;
                relHeader.setVisibility(View.GONE);
                Log.i("adapter","adapterViewX:"+viewX+"adapterViewY:"+viewY);
                handler.removeCallbacks(this);
            }else {
                handler.postDelayed(this,100);
            }
        }
    };
    private void getData(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            getToastdialog(this, Constants.NETWORK_ERROR);
            return;
        }
        showLoading(this);
        url = Constants.goodsDetail+"?id="+autoId;
        cancelable = presenter.getGoodDetail();
    }
    @OnClick({R.id.imageView_back,R.id.linear_collect,R.id.linear_shop,R.id.tv_add_shopCar,R.id.tv_right_buy,R.id.tv_pingjia,R.id.tv_order_know,
            R.id.tv_detail,R.id.tv_pingjia_bottom,R.id.tv_order_know_bottom,R.id.tv_detail_bottom,R.id.btn_goShop})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.linear_collect:
                if (!"0".equals(Constants.userId)){
                    /**调用收藏或者取消收藏的接口*/
                    if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                    showLoading(this);
                    if (!isCollect){
                        url = Constants.collectGood;
                        cancelable = presenter.collectGood(1,autoId+"");
                    }else {
                        url = Constants.cancelCollect;
                        cancelable = presenter.collectGood(2,autoId+"");
                    }
                }else {
                    Intent intent = new Intent(this , LoginActivity.class);
                    intent.putExtra("notmain","notmain");
                    startActivity(intent);
                }
                break;

            case R.id.linear_shop:
                if (bean != null){
                    Intent intent = new Intent(this, MerchantShops.class);
                    intent.putExtra("id",bean.getShopId()+"");
                    intent.putExtra("name",bean.getShopName());
                    intent.putExtra("url",bean.getShopUrl());
                    startActivity(intent);
                }
                break;

            case R.id.tv_add_shopCar:
                /**加入购物车*/
                if (!"0".equals(Constants.userId)){
                    showCountDialog(0);
                }else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                break;

            case R.id.tv_right_buy:
                /**立即购买 首先执行选择规格的操作*/
                if (!"0".equals(Constants.userId)){
                    showCountDialog(1);
                }else {
                    Intent intent  = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                break;

            case R.id.tv_pingjia:
                judge();
                break;
            case R.id.tv_order_know:
                orderNotce();
                break;
            case R.id.tv_detail:
                orderDetail();
                break;
            case R.id.tv_pingjia_bottom:
                judge();
                break;
            case R.id.tv_order_know_bottom:
                orderNotce();
                break;
            case R.id.tv_detail_bottom:
                orderDetail();
                break;
            case R.id.btn_goShop:
                /**拨打电话*/
                performRequestPermissions("网赢天下需要你的拨打电话权限",new String[]{Manifest.permission.CALL_PHONE},200,this);
                break;
        }
    }

    /**
     * 评价功能
     */
    private void judge(){
        setTopHeaderBackground(tvJudge);
        setBottomBackGround(tvJudgeBottom);
        if (!isPingJia){
            isPingJia = true;
            url = Constants.commentGood+"?goods_id="+this.autoId+"&page="+page;
            cancelable = presenter.commentGood();
        }else {
            if (commentData != null && commentData.size()>0){
                if (commentAdapter != null) recyclerView.setAdapter(commentAdapter);
            }else {
                recyclerView.setVisibility(View.GONE);
                linearEmptyView.setVisibility(View.VISIBLE);
            }
        }
    }
    private ShopNoticeAdapter noticeAdapter;
    /**
     * 下单须知
     */
    private void orderNotce(){
        setTopHeaderBackground(tvOrderKnow);
        setBottomBackGround(tvOrderKnowBottom);
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
            linearEmptyView.setVisibility(View.GONE);
        }
        if (noticeAdapter == null){
            noticeAdapter = new ShopNoticeAdapter(shopNotice,this);
        }
        recyclerView.setAdapter(noticeAdapter);
    }
    /**
     * 商品详情
     */
    private void orderDetail(){
        setTopHeaderBackground(tvDetail);
        setBottomBackGround(tvDetailBottom);
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
            linearEmptyView.setVisibility(View.GONE);
        }
        if (adapter != null){
            recyclerView.setAdapter(adapter);
//            adapter.reflash(bean);
        }
    }
    private void setTopHeaderBackground(View view){
        tvJudge.setBackgroundResource(0);
        tvJudge.setTextColor(blackColor);
        tvOrderKnow.setBackgroundResource(0);
        tvOrderKnow.setTextColor(blackColor);
        tvDetail.setBackgroundResource(0);
        tvDetail.setTextColor(blackColor);
        if (view instanceof TextView){
            view.setBackgroundResource(R.drawable.top_bar_text_backgrouond);
            ((TextView) view).setTextColor(redColor);
        }
    }
    private void setBottomBackGround(View view){
        tvJudgeBottom.setBackgroundResource(0);
        tvJudgeBottom.setTextColor(blackColor);
        tvOrderKnowBottom.setBackgroundResource(0);
        tvOrderKnowBottom.setTextColor(blackColor);
        tvDetailBottom.setBackgroundResource(0);
        tvDetailBottom.setTextColor(blackColor);
        if (view instanceof TextView){
            view.setBackgroundResource(R.drawable.top_bar_text_backgrouond);
            ((TextView) view).setTextColor(redColor);
        }
    }
    private boolean isPingJia;
    private TextView tvSure;
    private TextView tvSkuPrice;
    private TextView tvSkuLeft;
    private int maxGoodCount;
    private DetailDialogAdapter detailDialogAdapter;
    private void showCountDialog(final int flag){
        if (bean == null) return;
        List<GoodDetailBean.SpecItem> data = bean.getSpecItems();
        final Dialog dialog = showDialogFrame(R.layout.detail_dialog, Gravity.BOTTOM,0, CommonUtil.getScreenInfo(this)[0]);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) dialog.cancel();
            }
        });
        ImageView imageIcon = (ImageView) dialog.findViewById(R.id.image_icon);
        tvSkuPrice = (TextView) dialog.findViewById(R.id.tv_price);
        tvSkuLeft = (TextView) dialog.findViewById(R.id.tv_left);
        tvSure = (TextView) dialog.findViewById(R.id.tv_sure);
        LinearLayout linearPlus = (LinearLayout) dialog.findViewById(R.id.linear_plus);
        LinearLayout linearPlus1 = (LinearLayout) dialog.findViewById(R.id.linear_left);
        if (bean.getSpecItems()!=null && bean.getSpecItems().size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            linearPlus1.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            detailDialogAdapter = new DetailDialogAdapter(data ,this,this);
            recyclerView.setAdapter(detailDialogAdapter);
            tvSkuLeft.setText("请选择商品属性");
            tvSure.setEnabled(false);
            tvSure.setBackgroundColor(ContextCompat.getColor(this ,R.color.main_grey));
        }else {
            TextView  tvMinus = (TextView) dialog.findViewById(R.id.tv_minus);
            TextView tvPlus = (TextView) dialog.findViewById(R.id.tv_plus);
            final TextView tvAmount = (TextView) dialog.findViewById(R.id.tv_amount);
            recyclerView.setVisibility(View.GONE);
            linearPlus1.setVisibility(View.VISIBLE);
            linearPlus.setVisibility(View.VISIBLE);
            tvSkuLeft.setText("库存"+bean.getGoodCount()+"件");
            tvSure.setEnabled(true);
            tvSure.setBackgroundColor(ContextCompat.getColor(this ,R.color.main_red));
            maxGoodCount = bean.getGoodCount();
            skuId = "";
            tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(tvAmount.getText().toString());
                    if (amount>1){
                        amount--;
                        tvAmount.setText(amount+"");
                    }
                    goodCount = amount;
                }
            });
            tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(tvAmount.getText().toString());
                    if (amount <maxGoodCount){
                        amount++;
                    }
                    tvAmount.setText(amount+"");
                    goodCount = amount;
                }
            });
            goodCount = Integer.valueOf(tvAmount.getText().toString());
        }
        Glide.with(this).load(bean.getMainImage()).centerCrop().into(imageIcon);
        tvSkuPrice.setText(bean.getCurrentPrice());
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**执行加入购物车的操作*/
                if (flag == 0){
                    addToShopCar(skuId,goodCount);
                }else {
                    /**立即购买*/
                    rightBuy(autoId ,skuId,goodCount);
                }
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        });
    }

    /**
     * 立即购买
     * @param autoId
     * @param skuId
     * @param goodCount
     */
    private void rightBuy(int autoId, String skuId, int goodCount) {
        String url = "goods_id="+autoId+"&sku_id="+skuId+"&count="+goodCount;
        Intent intent = new Intent(this , RightBuyActivity.class);
        intent.putExtra("params",url);
        intent.putExtra("goodId",autoId+"");
        intent.putExtra("skuId",skuId);
        intent.putExtra("goodCount",goodCount);
        startActivity(intent);

    }
    /**
     * 添加商品到购物车
     * @param skuId
     * @param goodCount
     */
    private void addToShopCar(String skuId, int goodCount) {
        url = Constants.addToShopCar;
        if (presenter != null) cancelable = presenter.addGoodToShopCar(this.autoId+"",skuId,goodCount);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof GoodDetailBean){
                bean = (GoodDetailBean) args[0];
                setData(bean);
            }else if (args[0] instanceof Integer){
                int flag = (int) args[0];
                if (flag ==1){
                    imageCollect.setImageResource(R.drawable.have_collect);
                    isCollect = true;
                }else {
                    isCollect = false;
                    imageCollect.setImageResource(R.drawable.icon_dilan_shoucang);
                }
            }else if (args[0] instanceof List){
                /**评论*/
                commentData = (List<CommentBean>) args[0];
                if (commentData != null && commentData.size()>0){
                    if (commentAdapter == null){
                        commentAdapter = new CommentAdapter(commentData,this);
                        recyclerView.setAdapter(commentAdapter);
                    }
                }else {
                    recyclerView.setVisibility(View.GONE);
                    linearEmptyView.setVisibility(View.VISIBLE);
                }
            }else if (args[0] instanceof String && "add".equals(args[0])){
                /**添加购物车成功*/
                getToastdialog(this,"添加成功!");
                /**发送广播 更新购物车数据*/
                Intent intent = new Intent("com.wytianxia.shopCar");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
        }
        hideLoading();
    }
    private List<String> bannerList;
    private void setData(GoodDetailBean bean) {
        if (bean == null) return;
        shopNotice = bean.getShopNotice();
        bannerList = bean.getImagePicture();
        if (bannerList != null && bannerList.size()>0){
            bannerLayout.setImageLoader(new BannerImageLoader());
            bannerLayout.setImages(bannerList);
            bannerLayout.start();
        }
        tvName.setText(bean.getGoodName());
        tvMarketPrice.setText(bean.getCurrentPrice());
        tvDes.setText(bean.getNotice());
        Glide.with(this).load(bean.getShopUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(shopImageView);
        tvShopName.setText(bean.getShopName());
        tvGoodsCount.setText("商品数目："+bean.getGoodCount());
        adapter = new GoodsDetailAdapter(this,bean,this);
        recyclerView.setAdapter(adapter);
        if (!bean.getIsCollect()){
            /**还没有收藏*/
            imageCollect.setImageResource(R.drawable.icon_dilan_shoucang);
        }else {
            isCollect = true;
            imageCollect.setImageResource(R.drawable.have_collect);
        }
        if ("0".equals(bean.getIsCash())&& "0".equals(bean.getIsQuYang())&& "0".equals(bean.getIsPromote())){
            linearTag.setVisibility(View.GONE);
        }else {
            linearTag.setVisibility(View.VISIBLE);
            if ("1".equals(bean.getIsCash()))btnIsCash.setVisibility(View.VISIBLE);
            if ("1".equals(bean.getIsQuYang())) btnIsQuYang.setVisibility(View.VISIBLE);
            if ("1".equals(bean.getIsPromote())) btnIsPromote.setVisibility(View.VISIBLE);
        }
    }
    private String phone;
    @Override
    public void onPhoneClick(String phone) {
        this.phone = phone;
        performRequestPermissions("网赢天下需要你的拨打电话权限",new String[]{Manifest.permission.CALL_PHONE},200,this);
    }

    @Override
    public void onDetailClick(int autoId) {
        Intent intent = new Intent(this , GoodsDetailActivity.class);
        intent.putExtra("id",autoId);
        startActivity(intent);
    }
    @Override
    public void onPermissionGranted() {
        if (bean != null) call(bean.getContractPhone());
    }
    @Override
    public void onPermissionDenied() {
        getToastdialog(this,"拨打电话需要相关权限，请检查权限设置");
    }
    private String skuId;
    @Override
    public void onDialogClick(String[] clickIds, int size) {
        if (clickIds != null&& clickIds.length>0){
            for (int i =0;i<size;i++){
                if (clickIds[i] == null){
                    tvSure.setEnabled(false);
                    tvSure.setBackgroundColor(ContextCompat.getColor(this ,R.color.main_grey));
                    return;
                }
            }
            tvSure.setEnabled(true);
            tvSure.setBackgroundColor(ContextCompat.getColor(this ,R.color.main_red));
            int[] ids = new int[size];
            for (int j =0;j<size;j++){
                ids[j] = Integer.valueOf(clickIds[j]);
            }
            Arrays.sort(ids);
            String string = "";
            for (int intIds:ids){
                String str = String.valueOf(intIds);
                string+=str;
            }
            /**显示匹配以后的价格和库存数量*/
            /**对比匹配的sku*/
            List<GoodDetailBean.SpecSku> specSkus = bean.getSpecSkus();
            if (specSkus != null && specSkus.size()>0){
                for (int i = 0;i<specSkus.size();i++){
                    String specKey = specSkus.get(i).getSpecKey();
                    String price = specSkus.get(i).getPrice();
                    int left = specSkus.get(i).getGoodCount();
                    if (string.trim().equals(specKey.replace("_","").trim())){
                        /**如果匹配了*/
                        skuId = specSkus.get(i).getAutoId();
                        tvSkuPrice.setText(price);
                        tvSkuLeft.setText("库存"+left+"件");
                        maxGoodCount = left;
                        if (detailDialogAdapter != null) detailDialogAdapter.maxGoodCount(maxGoodCount);
                    }
                }
            }
        }else {
            tvSure.setEnabled(false);
            tvSure.setBackgroundColor(ContextCompat.getColor(this ,R.color.main_grey));
        }
    }
    private int goodCount;
    /**
     * 添加购物车商品数量
     * @param count
     */
    @Override
    public void goodCountClick(int count) {
        goodCount = count;
    }
}
