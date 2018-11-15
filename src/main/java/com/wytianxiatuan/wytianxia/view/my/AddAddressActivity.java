package com.wytianxiatuan.wytianxia.view.my;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.AddressBean;
import com.wytianxiatuan.wytianxia.bean.JsonBean;
import com.wytianxiatuan.wytianxia.present.MainPresenter;
import com.wytianxiatuan.wytianxia.util.CommonUtil;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.JsonUtils;
import com.wytianxiatuan.wytianxia.util.UiUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuju on 2018/1/17.
 * 添加收货地址   编辑收货地址
 */

public class AddAddressActivity extends BaseActivity implements AddAddressView,TextWatcher{
    @BindView(R.id.tv_top_title) TextView textTitle;
    @BindView(R.id.et_name) EditText edName;
    @BindView(R.id.et_phone) EditText edPhone;
    @BindView(R.id.tv_location) TextView tvLocation;
    @BindView(R.id.et_detail_address) EditText etDetailAddress;
    @BindView(R.id.image_default) ImageView imageDefault;
    @BindView(R.id.btn_save) Button btnSure;
    @BindView(R.id.btn_cancel) Button btnCancel;
    private String flag;
    private MainPresenter presenter;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private int isAdd;
    private String url;
    private String isDefault = "0";
    private AddressBean entity;
    private String autoId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        flag = getIntent().getStringExtra("flag");
        entity = (AddressBean) getIntent().getSerializableExtra("entity");
        init();
    }

    private void init() {
        if ("add".equals(flag)){
            textTitle.setText("添加收货地址");
            btnSure.setEnabled(false);
            isAdd = 0;
            isDefault = "0";
            btnCancel.setText("取消");
            url = Constants.addAddress;
            btnSure.setBackgroundColor(ContextCompat.getColor(this,R.color.main_grey));
        }else {
            isAdd = 1;/**代表编辑收货地址*/
            btnCancel.setText("删除");
            if (entity != null) {
                edName.setText(entity.getName());
                edName.setSelection(entity.getName().length());
                edPhone.setText(entity.getTelephone());
                tvLocation.setText(entity.getArea());
                etDetailAddress.setText(entity.getDetail());
                isDefault = entity.getIsDefault();
                autoId = entity.getAutoId();
                if ("0".equals(isDefault)){
                    imageDefault.setImageResource(R.drawable.off);
                }else {
                    imageDefault.setImageResource(R.drawable.on);
                }
                textTitle.setText("编辑收货地址");
            }
        }
        edName.addTextChangedListener(this);
        edPhone.addTextChangedListener(this);
        tvLocation.addTextChangedListener(this);
        etDetailAddress.addTextChangedListener(this);
    }
    @OnClick({R.id.imageView_back,R.id.linear_location,R.id.btn_save,R.id.image_default,R.id.btn_cancel})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.linear_location:
                /**选择地区*/
                cancelKeybord();
                initJsonData();
                break;

            case R.id.btn_save:
                if (presenter == null) presenter = new MainPresenter(getApplicationContext() ,this);
                if (isAdd == 1)url = Constants.editAddress;
                cancelable = presenter.addAddress();
                break;

            case R.id.image_default:
                if ("0".equals(isDefault)){
                    isDefault = "1";
                    imageDefault.setImageResource(R.drawable.on);
                }else {
                    isDefault = "0";
                    imageDefault.setImageResource(R.drawable.off);
                }
                break;
            case R.id.btn_cancel:
                if (isAdd == 0){
                    this.finish();
                }else {
                    /**删除收货地址*/
                    final Dialog dialog = showDialogFrame(R.layout.normal_dialog, Gravity.CENTER,0, UiUtils.dipToPx(this,295));
                    TextView textContent = (TextView) dialog.findViewById(R.id.tv_content);
                    textContent.setText("您确定要删除该收货地址吗？");
                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                    Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog != null && dialog.isShowing()) dialog.cancel();
                        }
                    });
                    btnSure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAddress();
                            if (dialog != null) dialog.cancel();
                        }
                    });
                }
                break;
        }
    }
    private void deleteAddress(){
        url = Constants.deleteAddress;
        if (presenter == null) presenter = new MainPresenter(getApplicationContext() ,this);
        cancelable = presenter.deleteAddress();
    }

    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+ " "+options2Items.get(options1).get(options2)+
                        " "+ options3Items.get(options1).get(options2).get(options3);
                tvLocation.setText(tx);
            }
        }).setTitleText("城市选择").setDividerColor(ContextCompat.getColor(AddAddressActivity.this , R.color.background))
                .setTextColorCenter(ContextCompat.getColor(AddAddressActivity.this , R.color.title_color)) //设置选中项文字颜色
                .setContentTextSize(20).build();
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }
    private void cancelKeybord() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void initJsonData() {
        String jsonData = CommonUtil.getJson(this,"province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = JsonUtils.getProvince(jsonData);
        options1Items = jsonBean;
        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {
                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            options2Items.add(CityList);
            options3Items.add(Province_AreaList);
        }
        ShowPickerView();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return edName.getText().toString();
    }

    @Override
    public String getPhone() {
        return edPhone.getText().toString();
    }

    @Override
    public String getArea() {
        return tvLocation.getText().toString();
    }

    @Override
    public String getDetail() {
        return etDetailAddress.getText().toString();
    }

    @Override
    public String isDefault() {
        return isDefault;
    }

    @Override
    public int flag() {
        return isAdd;
    }

    @Override
    public String autoId() {
        return autoId;
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (args != null && args.length>0){
            if (args[0] instanceof Integer){
                int flag = (int) args[0];
                if (flag == 0){
                    getToastdialog(this ,"添加成功");
                    Intent intent = new Intent();
                    intent.putExtra("flag","add");
                    this.setResult(300,intent);
                    this.finish();
                }else {
                    getToastdialog(this ,"编辑成功");
                    Intent intent = new Intent();
                    intent.putExtra("flag","edit");
                    this.setResult(300,intent);
                    this.finish();
                }
            }else if (args[0] instanceof String){
                getToastdialog(this ,"删除成功");
                Intent intent = new Intent();
                intent.putExtra("flag","delete");
                this.setResult(300,intent);
                this.finish();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(edName.getText()) && !TextUtils.isEmpty(edPhone.getText()) &&
                !TextUtils.isEmpty(tvLocation.getText())&&!TextUtils.isEmpty(etDetailAddress.getText())){
            btnSure.setEnabled(true);
            btnSure.setBackgroundColor(ContextCompat.getColor(this,R.color.main_red));
        }else {
            btnSure.setEnabled(false);
            btnSure.setBackgroundColor(ContextCompat.getColor(this,R.color.main_grey));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
