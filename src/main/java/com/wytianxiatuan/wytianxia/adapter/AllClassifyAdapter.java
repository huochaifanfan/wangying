package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.ClassifyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuju on 2018/1/24.
 */

public class AllClassifyAdapter extends RecyclerView.Adapter<AllClassifyAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ClassifyBean> data;
    private LayoutInflater inflater;
    private Context context;
    private OnAllClassifyClickListener onAllClassifyClickListener;

    public AllClassifyAdapter(List<ClassifyBean> data, Context context,OnAllClassifyClickListener onAllClassifyClickListener) {
        this.data = data;
        this.context = context;
        this.onAllClassifyClickListener = onAllClassifyClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<ClassifyBean> data){
        this.data =data;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.all_classify_right_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ClassifyBean bean = data.get(position+1);
        Glide.with(context).load(bean.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvName.setText(bean.getName());
        holder.linearContent.setOnClickListener(this);
        holder.linearContent.setTag(R.id.classify_right, bean.getAutoId());
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size()-1;
    }
    public interface OnAllClassifyClickListener{
        void onAllClassifyClick(String autoId);
    }
    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.classify_right);
        if (onAllClassifyClickListener != null) onAllClassifyClickListener.onAllClassifyClick(tag+"");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView) CircleImageView imageView;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.linear_content) LinearLayout linearContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
