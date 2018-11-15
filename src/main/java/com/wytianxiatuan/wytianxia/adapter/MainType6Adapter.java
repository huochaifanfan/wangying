package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.bean.MainBean;
import com.wytianxiatuan.wytianxia.util.CommonUtil;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/1/20.
 */

public class MainType6Adapter extends RecyclerView.Adapter<MainType6Adapter.MyViewHolder> implements View.OnClickListener{
    private List<MainBean.Recommand> data;
    private Context context;
    private LayoutInflater inflater;
    private int flag;
    private List<GoodDetailBean.Recommond> detailData;
    private OnDetailClickListener onDetailClickListener;

    public MainType6Adapter(List<MainBean.Recommand> data, Context context,int flag , List<GoodDetailBean.Recommond> detailData,OnDetailClickListener onDetailClickListener) {
        this.data = data;
        this.context = context;
        this.flag = flag;
        this.detailData = detailData;
        this.onDetailClickListener = onDetailClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.main_type6_item,parent,false));
    }
    public void update(List<MainBean.Recommand> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (flag == 1){
            MainBean.Recommand entity = data.get(position);
            Glide.with(context).load(entity.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                    into(holder.imageView);
            holder.tvTitle.setText(entity.getName());
            holder.tvPrice.setText("￥"+entity.getPrice());
            holder.linearLayout.setOnClickListener(this);
            holder.linearLayout.setTag(R.id.main_page_id,entity.getAutoId());
        }else if (flag == 2){
            GoodDetailBean.Recommond entity = detailData.get(position);
            Glide.with(context).load(entity.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                    into(holder.imageView);
            holder.tvTitle.setText(entity.getName());
            holder.tvPrice.setText("￥"+entity.getCurrentPrice());
            holder.linearLayout.setOnClickListener(this);
            holder.linearLayout.setTag(R.id.main_page_id,entity.getAutoId());
        }
    }

    @Override
    public int getItemCount() {
        if (data == null && flag ==1) data = new ArrayList<>();
        if (detailData == null && flag == 2) detailData = new ArrayList<>();
        if (flag == 1){
            return data.size();
        }else {
            return detailData.size();
        }
    }
    public interface OnDetailClickListener{
        void onDetailClick(int autoId);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.main_page_id);
        if (onDetailClickListener != null) onDetailClickListener.onDetailClick(tag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.linear_content) LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
