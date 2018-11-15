package com.wytianxiatuan.wytianxia.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import org.xutils.http.cookie.DbCookieStore;

import java.net.HttpCookie;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class CookieUtil {
    /**
     * 清空本地数据
     * @param context
     */
    public static void logout(Context context){
        CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();
        DbCookieStore.INSTANCE.removeAll();
    }
    /**
     * 保存cookie
     * @param context
     */
    @SuppressLint("CommitPrefEdits")
    public static void saveCookie(Context context){
        DbCookieStore instance = DbCookieStore.INSTANCE;
        List<HttpCookie> cookies = instance.getCookies();
        if (cookies != null && cookies.size()>0) {
            Set<String> myCookies = new HashSet<>();
            for (HttpCookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                String domain = cookie.getDomain();
                String path = cookie.getPath();
                String str = name + "=" + value + ";domain="+domain+";path="+path;
//				String str = name + "=" + value;
                myCookies.add(str);
            }
            SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("cookie", myCookies);
            boolean a = editor.commit();
            Log.i("cook",myCookies.size()+myCookies.toString()+a);
        }
    }
    public static void clearCache(Context context){
        SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();
        DbCookieStore.INSTANCE.removeAll();
    }
    /**
     * 设置cookie
     * @param context
     * @param url
     */
    public static void getCookie(Context context ,String url){
        SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        Set<String> cookies = sp.getStringSet("cookie",null);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        //加载url之前先设置cookie
        if (cookies != null){
            for (String cookie:cookies){
                cookieManager.setCookie(url, cookie);
            }
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        }
    }
}
