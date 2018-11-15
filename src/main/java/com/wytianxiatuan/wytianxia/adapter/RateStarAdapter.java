package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.util.Constants;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class RateStarAdapter extends RecyclerView.Adapter<RateStarAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private int count;
    private OnScoreChangeListener onScoreChangeListener;
    private int flag;
    private String goodId;

    public RateStarAdapter(Context context,int count,OnScoreChangeListener onScoreChangeListener,int flag,String goodId) {
        this.context = context;
        this.count = count;
        this.onScoreChangeListener = onScoreChangeListener;
        this.flag = flag;
        this.goodId = goodId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.rate_star_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (position <=count){
            Glide.with(context).load(R.drawable.star_select).into(holder.imageView);
        }else {
            Glide.with(context).load(R.drawable.star_unselet).into(holder.imageView);
        }
        Constants.startDesMap.put(goodId,(count+1)+"");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = position;
                notifyDataSetChanged();
                if (flag ==1){
                    /**描述评分*/
                    Constants.startDesMap.put(goodId,(count+1)+"");
                    if (onScoreChangeListener != null) onScoreChangeListener.onScoreChange(Constants.startDesMap,0,flag);
                }else if (flag == 2){
                    if (onScoreChangeListener != null) onScoreChangeListener.onScoreChange(null,count+1,flag);
                }
            }
        });
    }
    public interface OnScoreChangeListener{
        void onScoreChange(Map<String,String> desScore,int quaniltyScore,int flag);
    }
    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_star);
        }
    }
}
