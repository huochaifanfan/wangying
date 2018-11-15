package com.wytianxiatuan.wytianxia.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.CitySelectBean;
import com.wytianxiatuan.wytianxia.util.GridDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class CitySelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CitySelectBean> data;
    private Context context;
    private LayoutInflater inflater;
    private static final int ITEM_TYPE1 = 1;
    private static final int ITEM_TYPE2 = 2;
    private String[] hotCitys;
    private String currentCity;

    public CitySelectAdapter(List<CitySelectBean> data,String[] hotCitys ,String currentCity,Context context) {
        this.data = data;
        this.context = context;
        this.hotCitys = hotCitys;
        this.currentCity = currentCity;
        inflater = LayoutInflater.from(context);
    }
    private Type2ViewHolder type2ViewHolder;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE1:
                return new Type1ViewHolder(inflater.inflate(R.layout.city_select_type1,parent,false));
            case ITEM_TYPE2:
                type2ViewHolder = new Type2ViewHolder(inflater.inflate(R.layout.city_list_item,parent,false));
                return type2ViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1ViewHolder){
            bindType1((Type1ViewHolder) holder);
        }else if (holder instanceof Type2ViewHolder){
            bindType2((Type2ViewHolder) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE1;
        }else{
            return ITEM_TYPE2;
        }
    }
    public class Type1ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCurrentCity;
        public RecyclerView recyclerView;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            tvCurrentCity = (TextView) itemView.findViewById(R.id.currentCity);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.background, LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(context,15)));
            recyclerView.addItemDecoration(new GridDecoration(context,R.color.background, LinearLayoutManager.VERTICAL, UiUtils.dipToPx(context,15)));
        }
    }
    public class Type2ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
    private void bindType1(Type1ViewHolder holder){
        holder.tvCurrentCity.setText(currentCity);
        HotCityAdapter adapter = new HotCityAdapter(Arrays.asList(hotCitys),context);
        holder.recyclerView.setAdapter(adapter);
    }
    private void bindType2(Type2ViewHolder holder,int position){
        if (position >0){
            if (data.get(position).getCityPinyin().equals(data.get(position-1).getCityPinyin())){
                holder.tvTitle.setVisibility(View.GONE);
            }else {
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(data.get(position).getCityPinyin());
            }
            holder.tvContent.setText(data.get(position).getCityName());
        }else {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(data.get(position).getCityPinyin());
            holder.tvContent.setText(data.get(position).getCityName());
        }
    }
}
