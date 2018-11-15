package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class GoodDetailType2Adapter extends RecyclerView.Adapter<GoodDetailType2Adapter.MyViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private List<String> data;
    private String description;
    public GoodDetailType2Adapter(Context context, List<String> data, String description) {
        this.context = context;
        this.data = data;
        this.description = description;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.good_detail_type2_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == 0){
            holder.tvDes.setVisibility(View.VISIBLE);
            holder.tvDes.setText(description);
        }else {
            holder.tvDes.setVisibility(View.GONE);
        }
        Glide.with(context).load(data.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_des) TextView tvDes;
        @BindView(R.id.imageview) ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
