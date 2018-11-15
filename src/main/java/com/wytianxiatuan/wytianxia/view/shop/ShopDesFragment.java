package com.wytianxiatuan.wytianxia.view.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class ShopDesFragment extends BaseFragment {
    private TextView textView;
    private String des;
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_notice,container,false);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }
    public void setShopDes(String des){
        this.des = des;
    }

    @Override
    protected void initData() {
        textView.setText(des);
    }
}
