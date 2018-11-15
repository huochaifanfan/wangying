package com.wytianxiatuan.wytianxia.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断网络连接状态的工具类
 * @author Administrator
 *
 */
public class NetworkUtil {
	/**
	 * 判断网络是否连接
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context){
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context.
					getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netWorkInfo = manager.getActiveNetworkInfo();
			if (netWorkInfo != null) {
				if (netWorkInfo.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断wifi是否连接
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context){
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (info.isConnected()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否移动网络连接
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context){
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (info.isConnected()) {
				return true;
			}
		}
		return false;
	}
}
