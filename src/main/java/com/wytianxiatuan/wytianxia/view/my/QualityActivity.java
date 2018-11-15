package com.wytianxiatuan.wytianxia.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.QualityAdapter;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.JudgeOrderParams;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.main.MainActivity;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17 0017.
 * 评价
 */

public class QualityActivity extends BaseActivity implements QualityAdapter.RatingScoreListener,IMainView{
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private List<MyOrderBean.OrderList> data;
    private String orderId;
    private MainPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        data = (List<MyOrderBean.OrderList>) getIntent().getSerializableExtra("data");
        orderId = getIntent().getStringExtra("orderId");
        tvTitle.setText("评价");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyDecoration(this,R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(this ,10)));
        recyclerView.setAdapter(new QualityAdapter(data,this,this));
    }
    @OnClick({R.id.imageView_back})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                if (Constants.startDesMap != null && Constants.startDesMap.size()>0){
                    Constants.startDesMap = null;
                    Constants.startDesMap = new HashMap<>();
                }
                this.finish();
                break;
        }
    }
    @Override
    public void ratingScore(List<JudgeOrderParams.Comment> comments) {
        if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
        JudgeOrderParams params = new JudgeOrderParams();
        params.setId(orderId);
        params.setComment(comments);
        Gson gson = new Gson();
        String json = gson.toJson(params);
        cancelable = presenter.rateOrder(json);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Constants.startDesMap != null && Constants.startDesMap.size()>0){
            Constants.startDesMap = null;
            Constants.startDesMap = new HashMap<>();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public String getUrl() {
        return Constants.ratingOrder;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof String && args[0].equals("comment")){
                getToastdialog(this,"评价成功");
                Intent intent = new Intent(this , MainActivity.class);
                MainApplication.isMy = true;
                startActivity(intent);
                this.finish();
            }
        }
    }
}
