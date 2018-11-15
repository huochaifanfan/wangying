package com.wytianxiatuan.wytianxia.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.SearchAdapter;
import com.wytianxiatuan.wytianxia.bean.History;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.classify.GoodListActivity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/22 0022.
 * 搜索类
 */

public class SearchActivity extends BaseActivity implements View.OnFocusChangeListener,
        SearchAdapter.OnItemClickListener{
    @BindView(R.id.text_top) TextView textTop;
    @BindView(R.id.et_search) EditText etSearch;
    @BindView(R.id.image_search) ImageView imageSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyDecoration myDecoration;
    private SearchAdapter adapter;
    private SimpleDateFormat sdf;
    private DbManager db;
    private String where;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setTransluent(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        UiUtils.setParams(this,textTop,1);
        layoutManager = new LinearLayoutManager(this);
        myDecoration = new MyDecoration(this,R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(this,1));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(myDecoration);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        etSearch.setOnFocusChangeListener(this);
        initDataBase();
        where = getIntent().getStringExtra("where");
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();
    }

    @OnClick({R.id.imageView_back,R.id.image_search})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.image_search:
                insertData();
                break;
        }
    }
    private void deleteData(){
        try {
            db.delete(History.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    private void insertData(){
        if (!TextUtils.isEmpty(etSearch.getText())){
            /**如果不为空执行数据库的插入炒作*/
            try{
                String content = etSearch.getText().toString().trim();
                String time = sdf.format(new Date(System.currentTimeMillis()));
                List<History> data = new ArrayList<>();
                data.add(new History(content,time));
                db.save(data);
                Intent intent = new Intent(this , GoodListActivity.class);
                intent.putExtra("content",content);
                startActivity(intent);
                this.finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Intent intent = new Intent(this , GoodListActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
    private List<String> historyData;
    private void query(){
        try{
            List<History> data = db.selector(History.class).where("id",">=",0).findAll();
            if (data != null && data.size()>0){
                historyData = new ArrayList<>();
                for (int i =0;i<data.size();i++){
                    historyData.add(data.get(i).getContent());
                }
                if (historyData.size()>20) deleteData();
                historyData.add(0,"全部");
                if (adapter == null){
                    adapter = new SearchAdapter(historyData,this,this);
                    recyclerView.setAdapter(adapter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            query();
        }
    }
    private void initDataBase(){
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName("history.db").
                setDbVersion(1).setAllowTransaction(true);
        db = x.getDb(daoConfig);
    }

    @Override
    public void onItemClick(String content) {
        if ("list".equals(where)){
            Intent intent = new Intent();
//            intent.setAction("finish");
//            sendBroadcast(intent);
            intent.putExtra("content",content);
            this.setResult(300,intent);
            this.finish();
//            intent.setClass(this , GoodListActivity.class);
//            intent.putExtra("content",content);
//            startActivity(intent);
//            this.finish();
        }else {
            Intent intent = new Intent(this , GoodListActivity.class);
            if (!"全部".equals(content))intent.putExtra("content",content);
            startActivity(intent);
            this.finish();
        }
    }
}
