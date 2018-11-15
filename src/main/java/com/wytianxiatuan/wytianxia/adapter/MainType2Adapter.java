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
import com.wytianxiatuan.wytianxia.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuju on 2018/1/20.
 */

public class MainType2Adapter extends RecyclerView.Adapter<MainType2Adapter.MyViewHolder> implements View.OnClickListener{
    private Context context;
    private LayoutInflater inflater;
    private List<MainBean.CateGory> data;
    private OnClassifyClickListener onClassifyClickListener;

    public MainType2Adapter(Context context, List<MainBean.CateGory> data,OnClassifyClickListener onClassifyClickListener) {
        this.context = context;
        this.data = data;
        this.onClassifyClickListener = onClassifyClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.main_type2_item , parent , false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainBean.CateGory entity = data.get(position);
        Glide.with(context).load(entity.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.tvTitle.setText(entity.getName());
        holder.linearContent.setOnClickListener(this);
        holder.linearContent.setTag(R.id.main_classify_id ,entity.getAutoId());
    }
    public interface OnClassifyClickListener{
        void onClassifyClick(int autoId);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.main_classify_id);
        if (onClassifyClickListener != null) onClassifyClickListener.onClassifyClick(tag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) CircleImageView imageView;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.linear_content) LinearLayout linearContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
