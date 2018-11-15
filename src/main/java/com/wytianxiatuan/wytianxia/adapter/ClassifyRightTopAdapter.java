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
import com.wytianxiatuan.wytianxia.bean.ClassifyRightSecondBean;
import com.wytianxiatuan.wytianxia.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuju on 2018/2/3.
 */

public class ClassifyRightTopAdapter extends RecyclerView.Adapter<ClassifyRightTopAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ClassifyRightSecondBean> data;
    private LayoutInflater inflater;
    private Context context;
    private OnClick onClick;

    public ClassifyRightTopAdapter(List<ClassifyRightSecondBean> data, Context context,OnClick onClick) {
        this.data = data;
        this.context = context;
        this.onClick = onClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.classify_right_second_type1_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position<3){
            ClassifyRightSecondBean bean = data.get(position);
            Glide.with(context).load(bean.getImageUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
            holder.tvPrice.setText("￥" + bean.getPrice());
            holder.linearLayout.setOnClickListener(this);
            holder.linearLayout.setTag(R.id.fragment_classify_item, bean.getAutoId());
            holder.textTitle.setText(bean.getName());
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.fragment_classify_item);
        if (onClick != null)onClick.onClick(tag);
    }

    public interface OnClick{
        void onClick(int autoId);
    }
    private int size;
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        if (data.size()>3){
            size =3;
        }else {
            size = data.size();
        }
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.linear_image1) LinearLayout linearLayout;
        @BindView(R.id.imageview) ImageView imageView;
        @BindView(R.id.tv_price)TextView tvPrice;
        @BindView(R.id.tv_name) TextView textTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
