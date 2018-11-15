package com.wytianxiatuan.wytianxia.module;


import com.wytianxiatuan.wytianxia.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class BaseBiz {
    protected void getFailer(String status,String info, BaseListener listener) throws JSONException {
        BaseBean bean = new BaseBean();
        bean.setStatus(status);
        bean.setInfo(info);
        if (listener != null) listener.getDataFailer(bean);
    }
    protected void analysisFailer(BaseListener listener,String result,String exception){
        if (listener != null) listener.analysisFailer("result："+result+"错误原因："+exception);
    }
}
