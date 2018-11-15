package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/19.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> implements ShopListAdapter.DeleteGoodListener {
    private List<ShopBean.Shop> data;
    private Context context;
    private LayoutInflater inflater;
    private DeleteShopCarGood deleteShopCarGood;
    private int checkSize = 0;

    public ShopAdapter(List<ShopBean.Shop> data, Context context, DeleteShopCarGood deleteShopCarGood) {
        this.data = data;
        this.context = context;
        this.deleteShopCarGood = deleteShopCarGood;
        inflater = LayoutInflater.from(context);
    }
    int flag = 0;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.shop_list_item, parent, false));
    }
    public void reflashData(List<ShopBean.Shop> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ShopBean.Shop bean = data.get(position);
        holder.tvShopName.setText(bean.getShopName());
        if (bean.getShopListses() != null && bean.getShopListses().size() > 0) {
            int size = 0;
            for (int i = 0; i < bean.getShopListses().size(); i++) {
                if (bean.getShopListses().get(i).isSelect()) {
                    size++;
                }
            }
            if (size != 0 && size == bean.getShopListses().size()) {
                bean.setSelect(true);
            } else {
                bean.setSelect(false);
            }
        }
        if (bean.isSelect()) {
            holder.imageSelect.setChecked(true);
        } else {
            holder.imageSelect.setChecked(false);
        }
            final List<ShopBean.ShopLists> shopLists = bean.getShopListses();
            final ShopListAdapter adapter = new ShopListAdapter(context, shopLists, this);
            holder.recyclerView.setAdapter(adapter);

        holder.imageSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkSize++;
                    bean.setSelect(true);
                } else {
                    checkSize--;
                    bean.setSelect(false);
                }
                if (checkSize == data.size()) {
                    /**通知所有的选中*/
                    if (deleteShopCarGood != null) deleteShopCarGood.notifyCheckChange(1);
                } else {
                    if (deleteShopCarGood != null) deleteShopCarGood.notifyCheckChange(0);
                }
            }
        });
        holder.imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.imageSelect.isChecked()) {
                    bean.setSelect(false);
                    /**让它的子view所有的都选中*/
                    if (bean.getShopListses() != null && bean.getShopListses().size() > 0) {
                        for (int i = 0; i < bean.getShopListses().size(); i++) {
                            bean.getShopListses().get(i).setSelect(false);
                        }
                    }
                } else {
                    bean.setSelect(true);
                    /**让它的子view所有的都选中*/
                    if (bean.getShopListses() != null && bean.getShopListses().size() > 0) {
                        for (int i = 0; i < bean.getShopListses().size(); i++) {
                            bean.getShopListses().get(i).setSelect(true);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public interface DeleteShopCarGood {
        void deleteGood(String autoId);
        void minusOrPlus(String goodId, String minusOrPlus, int position);
        void totalPrice(double price);
        void notifyCheckChange(int flag);
        void onDetailClick(int goodId);
    }

    @Override
    public void onDeleteClick(String autoId) {
        if (deleteShopCarGood != null) deleteShopCarGood.deleteGood(autoId);
    }

    @Override
    public void minusOrPlusGood(String goodId, String minusOrPlus, int position) {
        if (deleteShopCarGood != null) deleteShopCarGood.minusOrPlus(goodId, minusOrPlus, position);
    }

    @Override
    public void totalPrice(double totalPrick) {
        if (deleteShopCarGood != null) deleteShopCarGood.totalPrice(totalPrick);
    }

    @Override
    public void notifyAdapter(int flag) {
        this.flag = flag;
        notifyDataSetChanged();
    }

    @Override
    public void onDetailClick(int autoId) {
        if (deleteShopCarGood != null) deleteShopCarGood.onDetailClick(autoId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        @BindView(R.id.good_total_names) TextView tvShopName;
        @BindView(R.id.image_select_goods) CheckBox imageSelect;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setFocusable(false);
        }
    }
}
