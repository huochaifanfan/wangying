package com.wytianxiatuan.wytianxia.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.adapter.SelectAddressAdapter;
import com.wytianxiatuan.wytianxia.bean.AddressBean;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.MyDecoration;
import com.wytianxiatuan.wytianxia.util.NetworkUtil;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import java.util.List;

/**
 * Created by liuju on 2018/1/16.
 * 选择 管理 收货地址
 */

public class SelectAddressActivity extends BaseActivity implements View.OnClickListener,AddAddressView,
        SelectAddressAdapter.OnViewClickListener{
    private ImageView imageBack;
    private TextView tvManage;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Button btnAddAddress;
    private SelectAddressAdapter adapter;
    private MainPresenter presenter;
    private List<AddressBean> data;
    private LinearLayout linearLayout;
    private String from;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        from = getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        imageBack = (ImageView) findViewById(R.id.imageView_back);
        tvManage = (TextView) findViewById(R.id.tv_manage);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnAddAddress = (Button) findViewById(R.id.btn_add_address);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDecoration(this,R.color.background,LinearLayoutManager.HORIZONTAL, UiUtils.dipToPx(this,1)));
        imageBack.setOnClickListener(this);
        tvManage.setOnClickListener(this);
        btnAddAddress.setOnClickListener(this);
        linearLayout = (LinearLayout) findViewById(R.id.empty_view);
        presenter = new MainPresenter(getApplicationContext() ,this);
        getData();
    }
    private void getData(){
        if (!NetworkUtil.isNetWorkConnected(this)){
            getToastdialog(this,Constants.NETWORK_ERROR);
            return;
        }
        showLoading(this);
        url = Constants.assressList;
        cancelable = presenter.getAddress();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.tv_manage:
                break;

            case R.id.btn_add_address:
                Intent intent = new Intent(this , AddAddressActivity.class);
                intent.putExtra("flag","add");
                startActivityForResult(intent ,200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300){
            if (data != null){
                if ("add".equals(data.getStringExtra("flag")) ||
                        "edit".equals(data.getStringExtra("flag"))||
                        "delete".equals(data.getStringExtra("flag"))){
                    getData();
                }
            }
        }
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return addressBean.getName();
    }

    @Override
    public String getPhone() {
        return addressBean.getTelephone();
    }

    @Override
    public String getArea() {
        return addressBean.getArea();
    }

    @Override
    public String getDetail() {
        return addressBean.getDetail();
    }

    @Override
    public String isDefault() {
        return "1";
    }

    @Override
    public int flag() {
        return 1;
    }

    @Override
    public String autoId() {
        return addressBean.getAutoId();
    }

    @Override
    public void getDataSuccess(Object[] args) {
        hideLoading();
        if (args != null && args.length>0){
            if (args[0] instanceof List){
                List<AddressBean> data = (List<AddressBean>) args[0];
                this.data = data;
                setData(data);
            }else if (args[0] instanceof Integer){
                Intent intent = new Intent();
                this.setResult(300,intent);
                this.finish();
            }
        }
    }

    private void setData(List<AddressBean> data) {
        if (data != null && data.size()>0) {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            if (adapter == null) {
                adapter = new SelectAddressAdapter(data, this, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.update(data);
            }
        }else {
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewClick(int position) {
        if (data == null) return;
        Intent intent = new Intent(this , AddAddressActivity.class);
        intent.putExtra("entity",data.get(position));
        startActivityForResult(intent ,200);
    }
    private AddressBean addressBean;
    /**
     * 选择收货地址
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        /**首先检查改地址是不是默认 不是默认设为默认*/
        if ("rightBuy".equals(from)){
            /**从购买页面过来的*/
            if (data == null) return;
            addressBean = data.get(position);
            if (addressBean == null) return;
            if ("0".equals(addressBean.getIsDefault())){
                /**不是默认地址 先设置为默认地址 调用修改收货地址接口*/
                url = Constants.editAddress;
                cancelable = presenter.addAddress();
            }else {
                Intent intent = new Intent();
                this.setResult(300,intent);
                this.finish();
            }
        }
    }
}
