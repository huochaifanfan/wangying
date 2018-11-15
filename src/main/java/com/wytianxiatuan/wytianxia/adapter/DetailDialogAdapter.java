package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.util.GridDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class DetailDialogAdapter extends RecyclerView.Adapter<DetailDialogAdapter.MyViewHolder> implements DetailDialogContentAdapter.OnDialogClickListener{
    private List<GoodDetailBean.SpecItem> data;
    private LayoutInflater inflater;
    private Context context;
    private OnDialogClickListener onDialogClickListener;
    private String[] ids;
    private int maxGoodCount;

    public DetailDialogAdapter(List<GoodDetailBean.SpecItem> data, Context context,OnDialogClickListener onDialogClickListener) {
        this.data = data;
        this.context = context;
        ids = new String[data!=null?data.size():0];
        this.onDialogClickListener = onDialogClickListener;
        inflater = LayoutInflater.from(context);
    }
    public void maxGoodCount(int maxGoodCount){
        this.maxGoodCount = maxGoodCount;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.detail_dialog_layout,parent,false));
    }
    private int amount = 1;
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (position == data.size()-1){
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(holder.tvAmount.getText().toString());
                    if (amount>1){
                        amount--;
                        holder.tvAmount.setText(amount+"");
                    }
                    if (onDialogClickListener != null) onDialogClickListener.goodCountClick(amount);
                }
            });
            holder.tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(holder.tvAmount.getText().toString());
                    if (amount <maxGoodCount){
                        amount++;
                    }
                    holder.tvAmount.setText(amount+"");
                    if (onDialogClickListener != null) onDialogClickListener.goodCountClick(amount);
                }
            });
            if (onDialogClickListener != null) onDialogClickListener.goodCountClick(amount);
        }else {
            holder.linearLayout.setVisibility(View.GONE);
        }
        GoodDetailBean.SpecItem bean = data.get(position);
        holder.tvTitle.setText(bean.getName());
        DetailDialogContentAdapter adapter = new DetailDialogContentAdapter(bean.getSpecGuiGe() ,context,this,data.size(),position);
        holder.recyclerView.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        if (data == null) data = new ArrayList<>();
        return data.size();
    }
    public interface OnDialogClickListener{
        void onDialogClick(String[] clickIds , int size);
        void goodCountClick(int count);
    }
    @Override
    public void onDialogClick(String clickIds, int size,int clickPosition) {
        ids[clickPosition] = clickIds;
        if (ids != null && ids.length>0){
            for (int i =0;i<ids.length;i++){
                if (ids[i] != null){
                    if (onDialogClickListener != null) onDialogClickListener.onDialogClick(ids,size);
                }
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        @BindView(R.id.linear_plus) LinearLayout linearLayout;
        @BindView(R.id.tv_minus) TextView tvMinus;
        @BindView(R.id.tv_plus) TextView tvPlus;
        @BindView(R.id.tv_amount) TextView tvAmount;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            recyclerView.setLayoutManager(new GridLayoutManager(context , 5));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.white_color, LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(context,9)));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.white_color, LinearLayoutManager.VERTICAL, UiUtils.dipToPx(context,7)));
        }
    }
}
