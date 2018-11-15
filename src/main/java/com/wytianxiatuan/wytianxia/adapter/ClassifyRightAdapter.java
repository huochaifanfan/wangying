package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.wytianxiatuan.wytianxia.util.GridDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class ClassifyRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener,ClassifyRightBottomAdapter.OnClassifyItemClickListener,ClassifyRightTopAdapter.OnClick{
    private List<ClassifyRightSecondBean> data;
    private LayoutInflater inflater;
    private Context context;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private OnViewClickListener onViewClickListener;
    public ClassifyRightAdapter(List<ClassifyRightSecondBean> data, Context context,OnViewClickListener onViewClickListener) {
        this.data = data;
        this.context = context;
        this.onViewClickListener = onViewClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new MyViewHolder(inflater.inflate(R.layout.classify_right_second_type1,parent,false));
            case ITEM_TYPE2:
                return new Type2ViewHolder(inflater.inflate(R.layout.classify_right_type2,parent,false));
        }
        return null;
    }
    public void update(List<ClassifyRightSecondBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            bindType1((MyViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            if (data.size()>=3){
                data.remove(0);
                data.remove(0);
                data.remove(0);
                ((Type2ViewHolder) holder).recyclerView.setVisibility(View.VISIBLE);
                ((Type2ViewHolder) holder).linearLayout.setVisibility(View.GONE);
            }else{
                data = new ArrayList<>();
                ((Type2ViewHolder) holder).recyclerView.setVisibility(View.GONE);
                ((Type2ViewHolder) holder).linearLayout.setVisibility(View.VISIBLE);
            }
            if (adapter == null){
                adapter = new ClassifyRightBottomAdapter(data,context,this);
                ((Type2ViewHolder) holder).recyclerView.setAdapter(adapter);
            }else {
                adapter.update(data);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag(R.id.fragment_classify_item);
        if (onViewClickListener != null) onViewClickListener.onItemViewClick(tag);
    }

    @Override
    public void onClassifyItemClick(int autoId) {
        if (onViewClickListener != null) onViewClickListener.onItemViewClick(autoId);
    }

    @Override
    public void onClick(int autoId) {
        if (onViewClickListener != null) onViewClickListener.onItemViewClick(autoId);
    }

    public interface OnViewClickListener{
        void onItemViewClick(int autoId);

    }
    private void bindType1(MyViewHolder holder){
        ClassifyRightTopAdapter adapter = new ClassifyRightTopAdapter(data,context,this);
        holder.recyclerView.setAdapter(adapter);
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else {
            return ITEM_TYPE2;
        }
    }
    private ClassifyRightBottomAdapter adapter;
    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            recyclerView.setLayoutFrozen(true);
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.background,LinearLayoutManager.VERTICAL,UiUtils.dipToPx(context,7)));
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public LinearLayout linearLayout;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context ,2));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.background,LinearLayoutManager.HORIZONTAL,UiUtils.dipToPx(context,10)));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.background,LinearLayoutManager.VERTICAL,UiUtils.dipToPx(context,5)));
            linearLayout = (LinearLayout) itemView.findViewById(R.id.empty_view);
            linearLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.background));
        }
    }
}
