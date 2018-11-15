package com.wytianxiatuan.wytianxia.present;

import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.module.BaseListener;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class MainListener implements BaseListener {
    private BaseListener listener;
    public MainListener(BaseListener listener) {
        this.listener = listener;
    }

    @Override
    public void analysisFailer(String info) {
        if (listener != null) listener.analysisFailer(info);
    }

    @Override
    public void httpError(String error) {
        if (listener != null) listener.httpError(error);
    }

    @Override
    public void getDataFailer(BaseBean bean) {
        if (listener != null) listener.getDataFailer(bean);
    }

    @Override
    public void getDataSuccess(Object[] args) {
        if (listener != null) listener.getDataSuccess(args);
    }
}
