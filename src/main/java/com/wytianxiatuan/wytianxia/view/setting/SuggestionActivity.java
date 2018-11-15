package com.wytianxiatuan.wytianxia.view.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;

/**
 * Created by liuju on 2018/2/3.
 */

public class SuggestionActivity extends BaseActivity implements TextWatcher,View.OnClickListener{
    private ImageView imageBack;
    private EditText etContent;
    private EditText etContact;
    private Button btnSubmit;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        init();
    }
    private void init() {
        imageBack = (ImageView) findViewById(R.id.imageView_back);
        etContent = (EditText) findViewById(R.id.et_content);
        etContact = (EditText) findViewById(R.id.et_contact);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        imageBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setEnabled(false);
        btnSubmit.setBackgroundResource(R.drawable.button_login_grey);
        etContent.addTextChangedListener(this);
        etContact.addTextChangedListener(this);
        textView = (TextView) findViewById(R.id.tv_top_title);
        textView.setText("意见反馈");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_back:
                this.finish();
                break;

            case R.id.btn_submit:
                getToastdialog(SuggestionActivity.this , "反馈成功，感谢您对网赢天下的关注!");
                this.finish();
                break;
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(etContent.getText()) && !TextUtils.isEmpty(etContact.getText())){
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundResource(R.drawable.button_login);
        }else {
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundResource(R.drawable.button_login_grey);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}
