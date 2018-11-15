package com.wytianxiatuan.wytianxia.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class QualitySecondFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quality_first,container,false);
        return view;
    }
}
