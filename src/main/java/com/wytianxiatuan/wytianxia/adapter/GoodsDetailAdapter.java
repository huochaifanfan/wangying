package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.CommentBean;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.overrideview.banner.Banner;
import com.wytianxiatuan.wytianxia.util.BannerImageLoader;
import com.wytianxiatuan.wytianxia.util.GridDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/30.
 * 商品详情
 */

public class GoodsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener,MainType6Adapter.OnDetailClickListener{
    private LayoutInflater inflater;
    private Context context;
    private GoodDetailBean detailBean;
    private static final int ITEM_TYPE1 =1;
    private static final int ITEM_TYPE2 =2;
    private static final int ITEM_TYPE3 =3;
    private OnPhoneClickListener onPhoneClickListener;

    public GoodsDetailAdapter(Context context, GoodDetailBean detailBean,OnPhoneClickListener onPhoneClickListener) {
        this.context = context;
        this.detailBean = detailBean;
        this.onPhoneClickListener = onPhoneClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.good_detail_type1,parent,false));

            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.good_detail_type2,parent,false));

            case ITEM_TYPE3:
                return new Type3ViewHolder(inflater.inflate(R.layout.good_detail_type3,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            bindType2((Type2ViewHolder) holder);
        }else if (holder instanceof Type3ViewHolder){
            bindType3((Type3ViewHolder) holder);
        }
    }
    public void reflash(GoodDetailBean bean){
        this.detailBean = bean;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE2;
        }else if (position == 1){
            return ITEM_TYPE3;
        }else if (position ==2){
            return ITEM_TYPE3;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onDetailClick(int autoId) {
        if (onPhoneClickListener != null) onPhoneClickListener.onDetailClick(autoId);
    }

    public interface OnPhoneClickListener{
        void onPhoneClick(String phone);
        void onDetailClick(int autoId);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_goShop:
                if (onPhoneClickListener != null) onPhoneClickListener.onPhoneClick(detailBean.getContractPhone());
                break;

        }
    }

    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bannerLayout) Banner bannerLayout;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_market_price)TextView tvMarketPrice;
        @BindView(R.id.tv_current_price) TextView tvCurrentPrice;
        @BindView(R.id.tv_des) TextView tvDes;
        @BindView(R.id.shop_image_icon) ImageView shopImageView;
        @BindView(R.id.tv_shopName) TextView tvShopName;
        @BindView(R.id.tv_goodsCount) TextView tvGoodsCount;
        @BindView(R.id.btn_goShop) Button btnGoShop;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
    public class Type3ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type3ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.white_color,
                    LinearLayoutManager.HORIZONTAL , UiUtils.dipToPx(context,15)));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.white_color,
                    LinearLayoutManager.VERTICAL , UiUtils.dipToPx(context,15)));
        }
    }
    private List<String> bannerList;
    private void bindType1(Type1ViewHolder holder){
        if (detailBean == null) return;
        bannerList = detailBean.getImagePicture();
        if (bannerList != null && bannerList.size()>0){
            holder.bannerLayout.setImageLoader(new BannerImageLoader());
            holder.bannerLayout.setImages(bannerList);
            holder.bannerLayout.start();
        }
        holder.tvName.setText(detailBean.getGoodName());
        holder.tvMarketPrice.setText(detailBean.getCurrentPrice());
        TextPaint paint = holder.tvCurrentPrice.getPaint();
        paint.setColor(ContextCompat.getColor(context,R.color.main_red));
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        paint.setAntiAlias(true);
        holder.tvDes.setText(detailBean.getNotice());
        Glide.with(context).load(detailBean.getShopUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.shopImageView);
        holder.tvShopName.setText(detailBean.getShopName());
        holder.tvGoodsCount.setText("商品数目："+detailBean.getGoodCount());
        holder.btnGoShop.setOnClickListener(this);
    }
    private void bindType2(final Type2ViewHolder holder){
        if (detailBean == null) return;
        GoodDetailType2Adapter adapter = new GoodDetailType2Adapter(context,detailBean.getDetailPic(),detailBean.getGoodsDes());
        holder.recyclerView.setAdapter(adapter);
    }
    private void bindType3(Type3ViewHolder holder){
        if (detailBean == null) return;
        MainType6Adapter adapter = new MainType6Adapter(null,context,2,detailBean.getRecommondData(),this);
        holder.recyclerView.setAdapter(adapter);
    }
}
