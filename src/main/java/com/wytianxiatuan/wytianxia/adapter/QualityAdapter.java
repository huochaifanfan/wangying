package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.JudgeOrderParams;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.MyViewHolder> implements
        RateStarAdapter.OnScoreChangeListener,View.OnClickListener{
    private List<MyOrderBean.OrderList> data;
    private Context context;
    private LayoutInflater inflater;
    private Map<String,String> contentMap = new HashMap<>();
    private RatingScoreListener ratingScoreListener;

    public QualityAdapter(List<MyOrderBean.OrderList> data, Context context,RatingScoreListener ratingScoreListener) {
        this.data = data;
        this.context = context;
        this.ratingScoreListener = ratingScoreListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.rating_bar_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MyOrderBean.OrderList bean = data.get(position);
        holder.btnSure.setEnabled(false);
        holder.btnSure.setBackgroundResource(R.drawable.button_login_unenable);
        Glide.with(context).load(bean.getOrderImage()).centerCrop().into(holder.imageIcon);
        holder.tvTitle.setText(bean.getOrderTitle());
        holder.tvPrice.setText("￥"+bean.getOrderPrice());
        holder.recyclerViewDes.setAdapter(new RateStarAdapter(context ,4,this,1,bean.getGoodId()));
        if (position == data.size()-1){
            holder.btnSure.setVisibility(View.VISIBLE);
            holder.btnSure.setOnClickListener(this);
        }else {
            holder.btnSure.setVisibility(View.GONE);
        }
        holder.etSay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(holder.etSay.getText())){
                    holder.btnSure.setEnabled(true);
                    holder.btnSure.setBackgroundResource(R.drawable.button_login);
                }else {
                    holder.btnSure.setEnabled(false);
                    holder.btnSure.setBackgroundResource(R.drawable.button_login_unenable);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                contentMap.put(data.get(position).getGoodId(),holder.etSay.getText().toString());
            }
        });
    }
    public interface RatingScoreListener{
        void ratingScore(List<JudgeOrderParams.Comment> comments);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    private Map<String,String> desScore;
    private int quaniltyScore = 5;
    @Override
    public void onScoreChange(Map<String,String> desScore, int quaniltyScore,int flag) {
        if (flag == 1)this.desScore = desScore;
        if (flag == 2)this.quaniltyScore = quaniltyScore;
    }
    @Override
    public void onClick(View v) {
        if (data != null && data.size()>0){
            List<String> maps = new ArrayList<>();
            String score;
            if (desScore != null){
                for (String key:desScore.keySet()){
                    maps.add(desScore.get(key));
                }
            }
            List<JudgeOrderParams.Comment> comments = new ArrayList<>();
            for (int i =0;i<data.size();i++){
                JudgeOrderParams.Comment commentBean = new JudgeOrderParams.Comment();
                if (data.size()==1) {
                    score=desScore ==null?"5":maps.get(0);
                }else {
                    score = desScore == null?"5":maps.get(i);
                }
                commentBean.setAuto_id(data.get(i).getGoodId());
                commentBean.setContent(contentMap.get(data.get(i).getGoodId()));
                commentBean.setScore(score);
                comments.add(commentBean);
            }
            if (ratingScoreListener != null) ratingScoreListener.ratingScore(comments);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_icon) ImageView imageIcon;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_price) TextView  tvPrice;
        @BindView(R.id.recyclerView_des) RecyclerView recyclerViewDes;
        @BindView(R.id.et_say) EditText etSay;
        @BindView(R.id.btn_sure) Button btnSure;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
            recyclerViewDes.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }
    }
}
