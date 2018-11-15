package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class GoodListTotalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<GoodsListBean.GoodsList> data;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;

    public GoodListTotalAdapter(Context context, List<GoodsListBean.GoodsList> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    public void update(List<GoodsListBean.GoodsList> data){
        this.data =data;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.recycler_view_layout,parent,false));

            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.no_data_layout,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            GoodsListAdapter adapter = new GoodsListAdapter(context,data,null);
            ((Type1ViewHolder) holder).recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (data != null && data.size()>0){
            return ITEM_TYPE1;
        }
        return ITEM_TYPE2;
    }
    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        public Type2ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
