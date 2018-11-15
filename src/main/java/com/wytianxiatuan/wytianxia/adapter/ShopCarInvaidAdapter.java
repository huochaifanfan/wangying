package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class ShopCarInvaidAdapter extends RecyclerView.Adapter<ShopCarInvaidAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ShopBean.OverTimeList> data;
    private Context context;
    private LayoutInflater inflater;
    private ClearInvaildGoodListener clearInvaildGoodListener;
    private String ids="";

    public ShopCarInvaidAdapter(List<ShopBean.OverTimeList> data, Context context,ClearInvaildGoodListener clearInvaildGoodListener) {
        this.data = data;
        this.context = context;
        getInvalidIds();
        this.clearInvaildGoodListener = clearInvaildGoodListener;
        inflater = LayoutInflater.from(context);
    }
    private void getInvalidIds(){
        if (data != null &&data.size()>0){
            for (int i =0;i<data.size();i++){
                ids+= "ids="+data.get(i).getGoodId()+"&";
            }
            ids = ids.substring(0,ids.length()-1).trim();
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.over_time_shop_car_goods,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShopBean.OverTimeList bean = data.get(position);
        Glide.with(context).load(bean.getImagePic()).centerCrop().into(holder.imageIcon);
        holder.tvGoodName.setText(bean.getTitle());
        holder.tvGuiGe.setText(bean.getDimen());
        holder.tvOverTimeGood.setOnClickListener(this);
    }
    public interface ClearInvaildGoodListener{
        void clearInvaildGoods(String invaildGoods);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (clearInvaildGoodListener != null) clearInvaildGoodListener.clearInvaildGoods(ids);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_clear) TextView tvOverTimeGood;
        @BindView(R.id.image_icon) ImageView imageIcon;
        @BindView(R.id.tv_goodsName) TextView tvGoodName;
        @BindView(R.id.tv_guige) TextView tvGuiGe;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
