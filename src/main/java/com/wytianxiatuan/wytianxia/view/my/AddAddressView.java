package com.wytianxiatuan.wytianxia.view.my;

import com.wytianxiatuan.wytianxia.module.BaseListener;

/**
 * Created by liuju on 2018/1/25.
 */

public interface AddAddressView extends BaseListener {
    String getUrl();
    String getName();
    String getPhone();
    String getArea();
    String getDetail();
    String isDefault();
    int flag();
    String autoId();
}
