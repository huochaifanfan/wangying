package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.ShopBean;
import com.wytianxiatuan.wytianxia.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/19.
 * 购物车商品
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder>{
    private Context context;
    private List<ShopBean.ShopLists> data;
    private LayoutInflater inflater;
    private DeleteGoodListener deleteGoodListener;

    public ShopListAdapter(Context context, List<ShopBean.ShopLists> data,DeleteGoodListener deleteGoodListener) {
        this.context = context;
        this.data = data;
        this.deleteGoodListener = deleteGoodListener;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.shop_list_adapter_item,parent,false));
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ShopBean.ShopLists bean = data.get(position);
        Glide.with(context).load(bean.getShopImage()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageIcon);
        holder.tvGoodName.setText(bean.getShopTitle());
        holder.tvGuiGe.setText(bean.getShopGuiGe());
        holder.tvPrice.setText("￥"+bean.getShopPrice());
        holder.tvAmount.setText(bean.getShopAmount()+"");
        if (bean.isSelect()){
            holder.imageSelect.setChecked(true);
            if (!Constants.selectIds.contains(bean.getShopGoodId())) Constants.selectIds.add(bean.getShopGoodId());
        }else {
            holder.imageSelect.setChecked(false);
            if (Constants.selectIds.contains(bean.getShopGoodId())) Constants.selectIds.remove(bean.getShopGoodId());
        }
        if (deleteGoodListener != null)deleteGoodListener.totalPrice(0);
        /**购物车减购*/
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteGoodListener != null){
                    deleteGoodListener.minusOrPlusGood(bean.getShopGoodId(),"0",position);
                    if (holder.imageSelect.isChecked()) {
                        if (Integer.valueOf(holder.tvAmount.getText().toString()) > 1) {
                            if (deleteGoodListener != null)
                                deleteGoodListener.totalPrice(-bean.getShopPrice());
                        }
                    }
                }
            }
        });
        /**购物车加购*/
        holder.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteGoodListener != null){
                    deleteGoodListener.minusOrPlusGood(bean.getShopGoodId(),"1",position);
                    if (holder.imageSelect.isChecked()) {
                        if (Integer.valueOf(holder.tvAmount.getText().toString()) < bean.getTotalAmount()) {
                            if (deleteGoodListener != null)
                                deleteGoodListener.totalPrice(bean.getShopPrice());
                        }
                    }
                }
            }
        });
        /**商品删除*/
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteGoodListener != null){
                    deleteGoodListener.onDeleteClick(bean.getShopGoodId());
                }
            }
        });
        holder.imageSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    /**这里检查购物车的商品是不是都已经选中*/
                    bean.setSelect(true);
                }else {
                    bean.setSelect(false);
                }
                if (deleteGoodListener != null) deleteGoodListener.notifyAdapter(1);
            }
        });
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteGoodListener != null) {
                    deleteGoodListener.onDetailClick(bean.getGoodAutoId());
                }
            }
        });
    }
    public interface DeleteGoodListener{
        void onDeleteClick(String autoId);
        void minusOrPlusGood(String goodId,String minusOrPlus,int position);
        void totalPrice(double totalPrick);
        void notifyAdapter(int flag);
        void onDetailClick(int autoId);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_select) CheckBox imageSelect;
        @BindView(R.id.image_icon) ImageView imageIcon;
        @BindView(R.id.tv_goodsName) TextView tvGoodName;
        @BindView(R.id.tv_guige) TextView tvGuiGe;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_amount) TextView tvAmount;
        @BindView(R.id.image_minus) ImageView imageMinus;
        @BindView(R.id.image_plus) ImageView imagePlus;
        @BindView(R.id.image_delete) ImageView imageDelete;
        @BindView(R.id.rel_content) RelativeLayout relContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
