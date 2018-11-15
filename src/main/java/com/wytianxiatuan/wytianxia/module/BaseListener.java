package com.wytianxiatuan.wytianxia.module;


import com.wytianxiatuan.wytianxia.bean.BaseBean;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public interface BaseListener<T> {
    void analysisFailer(String info);
    void httpError(String error);
    void getDataFailer(BaseBean bean);
    void getDataSuccess(T... args);
}
