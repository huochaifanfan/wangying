package com.wytianxiatuan.wytianxia.view.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.CitySelectAdapter;
import com.wytianxiatuan.wytianxia.adapter.SortAdapter;
import com.wytianxiatuan.wytianxia.bean.CitySelectBean;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23 0023.
 * 城市选择
 */

public class CitySelectActivity extends BaseActivity implements View.OnClickListener,SortAdapter.SortClickListener{
    private TextView tvTopTitle;
    private RecyclerView recyclerView;
    private ImageView imageBack;
    private CitySelectAdapter adapter;
    private RecyclerView listView;
    private String[] sortData = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","W","X","Y","Z"};
    private String[] hotCitys = {"北京市","杭州市","广州市","上海市","南京市","深圳市"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_select);
        init();
    }

    private void init() {
        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        tvTopTitle.setText("城市选择");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageBack = (ImageView) findViewById(R.id.imageView_back);
        imageBack.setOnClickListener(this);
        List<CitySelectBean> data = getCity();
        CitySelectBean bean = new CitySelectBean();
        bean.setCityPinyin("A");
        bean.setCityName("鞍山市");
        data.add(0,bean);
        adapter = new CitySelectAdapter(data,hotCitys,"北京市",this);
        recyclerView.setAdapter(adapter);
        listView = (RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new SortAdapter(sortData,this,this));
    }
    private List<CitySelectBean> getCity(){
        try {
            String json = CommonUtil.getJson(this,"citylistData.json");
            JSONArray array = new JSONArray(json);
            List<CitySelectBean> listData = new ArrayList<>();
            for (int i =0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                String tag = jsonObject.getString("tag");
                JSONArray jsonArray = jsonObject.getJSONArray("cities");
                for (int j = 0;j<jsonArray.length();j++){
                    CitySelectBean entity = new CitySelectBean();
                    JSONObject obj = jsonArray.getJSONObject(j);
                    entity.setCityName(obj.getString("name"));
                    entity.setCityPinyin(tag);
                    listData.add(entity);
                }
            }
            return listData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_back:
                this.finish();
                break;
        }
    }
    @Override
    public void onSortClick(int position) {
        recyclerView.scrollToPosition(position);
    }
}
