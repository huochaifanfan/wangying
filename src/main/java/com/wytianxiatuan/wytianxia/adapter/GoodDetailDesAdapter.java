package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class GoodDetailDesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> picture;
    private String description;
    private List<GoodDetailBean.Recommond> recommondData;
    private Context context;
    private LayoutInflater inflater;
    private static final int ITEM_TYPE1=1;
    private static final int ITEM_TYPE2=2;

    public GoodDetailDesAdapter(List<String> picture, String description, List<GoodDetailBean.Recommond> recommondData, Context context) {
        this.picture = picture;
        this.description = description;
        this.recommondData = recommondData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.recycler_view_layout,parent,false));
            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.good_detail_type3,parent,false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else if (position ==1){
            return ITEM_TYPE2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    private class Type1ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
    private class Type2ViewHolder extends RecyclerView.ViewHolder{
        public Type2ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
