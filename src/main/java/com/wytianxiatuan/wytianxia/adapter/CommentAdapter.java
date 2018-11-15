package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    private List<CommentBean> data;
    private Context context;
    private LayoutInflater inflater;

    public CommentAdapter(List<CommentBean> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.comment_item,parent,false));
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentBean bean = data.get(position);
        Glide.with(context).load(bean.getPersonImage()).into(holder.circleImageView);
        holder.tvName.setText(bean.getUserNick());
        holder.tvTime.setText(bean.getTime());
        holder.tvContent.setText(bean.getContent());
    }

    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_person) CircleImageView circleImageView;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_content) TextView tvContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
