package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.ShopBean;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class ShopTotalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ShopAdapter.DeleteShopCarGood,
        ShopCarInvaidAdapter.ClearInvaildGoodListener{
    private Context context;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private LayoutInflater inflater;
    private ShopBean bean;
    private OnViewClickListener onViewClickListener;

    public ShopTotalAdapter(ShopBean bean, Context context,OnViewClickListener onViewClickListener) {
        this.context = context;
        this.bean = bean;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void reflashData(ShopBean bean){
        this.bean = bean;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.recycler_view_layout,parent,false));
            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.shop_overtime_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            ShopCarInvaidAdapter adapter = new ShopCarInvaidAdapter(bean !=null?bean.getOverTimeList():null,context,this);
            ((Type2ViewHolder) holder).recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void clearInvaildGoods(String invaildGoods) {
        if (onViewClickListener != null) onViewClickListener.clearGoods(invaildGoods);
    }

    public interface OnViewClickListener{
        void deleteGood(String autoId);
        void minusOrPlus(String goodId,String minusOrPlus,int position);
        void totalPrice(double price);
        void clearGoods(String ids);
        void checkChange(int flag);
        void onDetailClick(int goodId);
    }
    @Override
    public int getItemCount() {
        return 2;
    }
    private ShopAdapter adapter;
    private void bindType1(Type1ViewHolder holder){
        if (adapter == null) {
            adapter = new ShopAdapter(bean!=null?bean.getShopList():null,context,this);
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setFocusable(false);
        }else {
            adapter.reflashData(bean!=null?bean.getShopList():new ArrayList<ShopBean.Shop>());
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else if (position == 1){
            return ITEM_TYPE2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void deleteGood(String autoId) {
        if (onViewClickListener != null) onViewClickListener.deleteGood(autoId);
    }

    @Override
    public void minusOrPlus(String goodId, String minusOrPlus, int position) {
        if (onViewClickListener != null) onViewClickListener.minusOrPlus(goodId,minusOrPlus,position);
    }

    @Override
    public void totalPrice(double price) {
        if (onViewClickListener != null) onViewClickListener.totalPrice(price);
    }

    @Override
    public void notifyCheckChange(int flag) {
        if (onViewClickListener != null) onViewClickListener.checkChange(flag);
    }

    @Override
    public void onDetailClick(int goodId) {
        if (onViewClickListener != null) onViewClickListener.onDetailClick(goodId);
    }

    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new MyDecoration(context,R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(context,15)));
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
