package com.wytianxiatuan.wytianxia.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class QualityFirstFragment extends BaseFragment {
//    @BindView(R.id.image_icon) CircleImageView circleImageView;
//    @BindView(R.id.tv_shopsName) TextView tvShopName;
//    @BindView(R.id.et_say) EditText etSay;
//    @BindView(R.id.btn_conmit) Button btnConmit;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_quality ,container,false);
        ButterKnife.bind(this , view);
        return view;
    }
}
