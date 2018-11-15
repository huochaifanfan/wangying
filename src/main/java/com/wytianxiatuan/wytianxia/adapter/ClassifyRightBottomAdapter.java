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
 * Created by Administrator on 2018/1/25 0025.
 */

public class ClassifyRightBottomAdapter extends RecyclerView.Adapter<ClassifyRightBottomAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ClassifyRightSecondBean> data;
    private Context context;
    private LayoutInflater inflater;
    private OnClassifyItemClickListener onClassifyItemClickListener;

    public ClassifyRightBottomAdapter(List<ClassifyRightSecondBean> data, Context context,OnClassifyItemClickListener onClassifyItemClickListener) {
        this.data = data;
        this.context = context;
        this.onClassifyItemClickListener = onClassifyItemClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.classify_right_second_bottom,parent,false));
    }
    public void update(List<ClassifyRightSecondBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ClassifyRightSecondBean bean = data.get(position);
        Glide.with(context).load(bean.getImageUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText("￥" + bean.getPrice());
        holder.linearContent.setOnClickListener(this);
        holder.linearContent.setTag(R.id.classify_bottom, bean.getAutoId());
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnClassifyItemClickListener{
        void onClassifyItemClick(int autoId);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.classify_bottom);
        if (onClassifyItemClickListener != null) onClassifyItemClickListener.onClassifyItemClick(tag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageview) ImageView imageView;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.linear_content) LinearLayout linearContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
