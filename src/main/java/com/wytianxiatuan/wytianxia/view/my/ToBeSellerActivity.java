package com.wytianxiatuan.wytianxia.view.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.present.IMainView;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/17.
 * 申请成为商家
 */

public class ToBeSellerActivity extends BaseActivity implements TextWatcher,IMainView{
    @BindView(R.id.tv_top_title) TextView tvTitle;
    @BindView(R.id.et_compain_name) EditText etCompanyName;
    @BindView(R.id.et_phone_number) EditText etPhoneNum;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_say) EditText etSay;
    @BindView(R.id.btn_sure) Button btnSure;
    private MainPresenter presenter;
    private Map<String,String> maps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tobe_seller);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvTitle.setText("申请成为商家");
        btnSure.setEnabled(false);
        btnSure.setBackgroundResource(R.drawable.button_login_unenable);
        etCompanyName.addTextChangedListener(this);
        etPhoneNum.addTextChangedListener(this);
        etName.addTextChangedListener(this);
        maps = new HashMap<>();
    }

    @OnClick({R.id.imageView_back,R.id.btn_sure})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.btn_sure:
                if (presenter == null) presenter = new MainPresenter(getApplicationContext(),this);
                getData();
                break;
        }
    }
    private void getData(){
        maps.put("name",etCompanyName.getText().toString());
        maps.put("telephone",etPhoneNum.getText().toString());
        maps.put("nick",etName.getText().toString());
        if (!TextUtils.isEmpty(etSay.getText())){
            maps.put("des",etSay.getText().toString());
        }
      cancelable = presenter.toBeSeller(maps);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etCompanyName.getText()) && !TextUtils.isEmpty(etPhoneNum.getText())&&!TextUtils.isEmpty(etName.getText())){
            btnSure.setEnabled(true);
            btnSure.setBackgroundResource(R.drawable.button_login);
        }else {
            btnSure.setEnabled(false);
            btnSure.setBackgroundResource(R.drawable.button_login_unenable);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public String getUrl() {
        return Constants.toBeSeller;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length >0){
            if (args[0] instanceof String){
                getToastdialog(this ,"申请成功！");
                this.finish();
            }
        }
    }
}
