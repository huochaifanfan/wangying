package com.wytianxiatuan.wytianxia.util;


import com.wytianxiatuan.wytianxia.module.BaseListener;

import org.xutils.common.Callback;

/**
 * xutils请求工具类
 * @author Administrator
 * @param <T>
 */
public abstract class CallBack<T> implements Callback.CommonCallback<T>{
	private BaseListener listener;
	public CallBack(BaseListener listener){
		this.listener = listener;
	}
	@Override
	public void onCancelled(CancelledException arg0) {
	}
	@Override
	public void onFinished() {
	}
	@Override
	public void onError(Throwable throwable, boolean b) {
		if (listener != null) listener.httpError(throwable.toString());
	}
}
