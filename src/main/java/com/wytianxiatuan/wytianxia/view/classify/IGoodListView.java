package com.wytianxiatuan.wytianxia.view.classify;

import com.wytianxiatuan.wytianxia.module.BaseListener;

/**
 * Created by liuju on 2018/1/28.
 */

public interface IGoodListView extends BaseListener{
    String getUrl();
    String getWd();
    String getOrderId();
    String getCatId();
    String page();
    boolean isOrder();
}
