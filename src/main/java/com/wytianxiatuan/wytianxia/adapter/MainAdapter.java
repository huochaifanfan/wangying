package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.MainBean;
import com.wytianxiatuan.wytianxia.overrideview.banner.Banner;
import com.wytianxiatuan.wytianxia.overrideview.banner.listener.OnBannerListener;
import com.wytianxiatuan.wytianxia.util.BannerImageLoader;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.GridDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/12.
 * 首页
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener,OnBannerListener,MainType2Adapter.OnClassifyClickListener,
        MainType6Adapter.OnDetailClickListener,MainType5Adapter.OnShopClickListener{
    private Context context;
    private LayoutInflater inflater;
    private MainBean mainBean;
    private List<MainBean.Recommand> recommand;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private static final int ITEM_TYPE3 = 3;
    private static final int ITEM_TYPE4 = 4;
    private static final int ITEM_TYPE5 = 5;
    private static final int ITEM_TYPE6 = 6;
    private OnViewClickListener onViewClickListener;

    public MainAdapter(Context context, MainBean mainBean,OnViewClickListener onViewClickListener) {
        this.context = context;
        this.mainBean = mainBean;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void setBottom(List<MainBean.Recommand> recommand){
        this.recommand = recommand;
        notifyDataSetChanged();
    }
    public void reflashLocation(){
        if (type1ViewHolder !=null) type1ViewHolder.tvLocation.setText(Constants.currentCity);
    }
    private Type1ViewHolder type1ViewHolder;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                type1ViewHolder = new Type1ViewHolder(inflater.inflate(R.layout.main_type1,parent,false));
                return type1ViewHolder;
            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.main_type2 , parent , false));
            case ITEM_TYPE3:
                return new Type3ViewHolder(inflater.inflate(R.layout.main_type3 , parent , false));
            case ITEM_TYPE4:
                return new Type4ViewHolder(inflater.inflate(R.layout.main_type4 , parent , false));
            case ITEM_TYPE5:
                return new Type5ViewHolder(inflater.inflate(R.layout.main_type5 , parent , false));
            case ITEM_TYPE6:
                return new Type6ViewHolder(inflater.inflate(R.layout.main_type6 , parent , false));
        }
        return null;
    }
    public void reflash(MainBean bean){
        this.mainBean = bean;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            bindType2((Type2ViewHolder) holder);
        }else if (holder instanceof Type3ViewHolder){
            bindType3((Type3ViewHolder) holder);
        }else if (holder instanceof Type4ViewHolder){
            bindType4((Type4ViewHolder) holder);
        }else if (holder instanceof Type5ViewHolder){
            bindType5((Type5ViewHolder) holder);
        }else if (holder instanceof Type6ViewHolder){
            bindType6((Type6ViewHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else if (position == 1){
            return ITEM_TYPE2;
        }else if (position == 2){
            return ITEM_TYPE3;
        }else if (position == 3){
            return ITEM_TYPE4;
        }else if (position == 4){
            return ITEM_TYPE5;
        }else if (position == 5){
            return ITEM_TYPE6;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag(R.id.main_page_id);
        if ("fazhan".equals(tag)){
            if (onViewClickListener != null) onViewClickListener.onDevelopClick(mainBean.getDevelopLink());
        }else if ("dyna".equals(tag)){
            if (onViewClickListener != null) onViewClickListener.onDynamicClick(mainBean.getMainDynamicLink());
        } else if ("middle".equals(tag)){
            if (onViewClickListener != null && mainBean != null){
                onViewClickListener.onBannerClick(mainBean.getMiddleTitle(),mainBean.getMiddleLink(),mainBean.getMiddleLinkType(),
                        mainBean.getMiddleRedirectType(),mainBean.getMiddleRedirectId());
            }
        }else {
            if (onViewClickListener!= null) onViewClickListener.onViewClick(tag);
        }
    }
    /**
     * banner点击链接
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        if (bannerList != null && onViewClickListener != null){
            MainBean.Banner banner = bannerList.get(position);
            onViewClickListener.onBannerClick(banner.getTitle(),banner.getImageLink(),banner.getLinkType(),banner.getRedirectType(),banner.getRedirectId());
        }
    }

    @Override
    public void onClassifyClick(int position) {
        if (onViewClickListener != null) onViewClickListener.onClassifyClick(position);
    }

    @Override
    public void onDetailClick(int autoId) {
        if (onViewClickListener != null) onViewClickListener.onDetailClick(autoId);
    }

    @Override
    public void onShopClick(int autoId,String name,String shopUrl) {
        if (onViewClickListener != null) {
            onViewClickListener.onShopClick(autoId,name,shopUrl);
        }
    }

    public interface OnViewClickListener{
        void onBannerClick(String name,String link,String type,String redirectType,String redirectId);
        void onViewClick(String tag);
        void onClassifyClick(int position);
        void onDevelopClick(String link);
        void onDynamicClick(String linnk);
        void onDetailClick(int autoId);
        void onShopClick(int autoId,String name,String shopUrl);
    }
    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bannerLayout) Banner bannerLayout;
        @BindView(R.id.tv_location) TextView tvLocation;
        @BindView(R.id.rel_search) RelativeLayout relSearch;
        @BindView(R.id.image_msg) ImageView imageMsg;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context,4));
        }
    }
    public class Type3ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_more) TextView tvMore;
        @BindView(R.id.image_middle) ImageView imageMiddle;
        @BindView(R.id.tv_develop_title) TextView tvDevelopTitle;
        @BindView(R.id.tv_develop_more) TextView tvDevelopMore;
        @BindView(R.id.linear_develop) LinearLayout linearDevelop;
        public Type3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public class Type4ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.linear_siwang) LinearLayout linearSiWang;
        @BindView(R.id.linear_temai) LinearLayout linearTeMai;
        @BindView(R.id.linear_peijian) LinearLayout linearPeiJian;
        @BindView(R.id.linear_jiagong) LinearLayout linearJiaGong;
        @BindView(R.id.linear_zhixiao) LinearLayout linearZhiXiao;
        @BindView(R.id.linear_quyang) LinearLayout linearQuYang;
        @BindView(R.id.linear_zhaopin) LinearLayout linearZhaoPin;
        public Type4ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public class Type5ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_more) TextView tvMore;
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        public Type5ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public class Type6ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type6ViewHolder(View itemView) {
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
    private List<MainBean.Banner> bannerList;
    private void bindType1(Type1ViewHolder holder){
        if (mainBean == null) return;
        bannerList = mainBean.getBannerData();
        if (bannerList != null && bannerList.size()>0){
            List<String> banners = new ArrayList<>();
            for (int i =0;i<bannerList.size();i++){
                banners.add(bannerList.get(i).getImage());
            }
            holder.bannerLayout.setImageLoader(new BannerImageLoader());
            holder.bannerLayout.setImages(banners);
            holder.bannerLayout.setOnBannerListener(this);
            holder.bannerLayout.start();
        }
        holder.tvLocation.setText(Constants.currentCity);
        holder.tvLocation.setOnClickListener(this);
        holder.tvLocation.setTag(R.id.main_page_id,"location");
        holder.relSearch.setOnClickListener(this);
        holder.relSearch.setTag(R.id.main_page_id,"search");
        holder.imageMsg.setOnClickListener(this);
        holder.imageMsg.setTag(R.id.main_page_id,"msg");
    }
    private void bindType2(Type2ViewHolder holder){
        if (mainBean == null) return;
        MainType2Adapter adapter = new MainType2Adapter(context , mainBean.getCateGoryData(),this);
        holder.recyclerView.setAdapter(adapter);
    }
    private void bindType3(Type3ViewHolder holder){
        if (mainBean == null) return;
        holder.tvTitle.setText(mainBean.getMainDynamicTitle());
        holder.tvTitle.setOnClickListener(this);
        holder.tvTitle.setTag(R.id.main_page_id , "dyna");
        holder.tvMore.setOnClickListener(this);
        holder.tvMore.setTag(R.id.main_page_id,"dymore");
        Glide.with(context).load(mainBean.getMiddleImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageMiddle);
        holder.imageMiddle.setOnClickListener(this);
        holder.imageMiddle.setTag(R.id.main_page_id,"middle");
        if ("".equals(mainBean.getDevelopTitle()) || mainBean.getDevelopTitle() == null){
            holder.linearDevelop.setVisibility(View.GONE);
        }else {
            holder.linearDevelop.setVisibility(View.VISIBLE);
            holder.tvDevelopTitle.setText(mainBean.getDevelopTitle());
            holder.linearDevelop.setOnClickListener(this);
            holder.linearDevelop.setTag(R.id.main_page_id,"fazhan");
//            holder.tvDevelopMore.setOnClickListener(this);
//            holder.tvDevelopMore.setTag(R.id.main_page_id,"develop");
        }
    }
    private void bindType4(Type4ViewHolder holder){
        holder.linearZhaoPin.setOnClickListener(this);
        holder.linearZhaoPin.setTag(R.id.main_page_id,"zhaopin");
        holder.linearSiWang.setOnClickListener(this);
        holder.linearSiWang.setTag(R.id.main_page_id,"siwang");
        holder.linearTeMai.setOnClickListener(this);
        holder.linearTeMai.setTag(R.id.main_page_id,"temai");
        holder.linearPeiJian.setOnClickListener(this);
        holder.linearPeiJian.setTag(R.id.main_page_id,"peijian");
        holder.linearJiaGong.setOnClickListener(this);
        holder.linearJiaGong.setTag(R.id.main_page_id,"jiagong");
        holder.linearZhiXiao.setOnClickListener(this);
        holder.linearZhiXiao.setTag(R.id.main_page_id,"siwang");
        holder.linearQuYang.setOnClickListener(this);
        holder.linearQuYang.setTag(R.id.main_page_id ,"quyang");
    }
    private void bindType5(Type5ViewHolder holder){
        if (mainBean == null) return;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,4);
        holder.recyclerView.setLayoutManager(layoutManager);
        MainType5Adapter adapter = new MainType5Adapter(mainBean.getMainShopData(),context,this);
        holder.recyclerView.setAdapter(adapter);
    }
    private void bindType6(Type6ViewHolder holder){
        if (recommand == null) return;
        MainType6Adapter adapter = new MainType6Adapter(recommand , context,1,null,this);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setFocusable(false);
    }
}
