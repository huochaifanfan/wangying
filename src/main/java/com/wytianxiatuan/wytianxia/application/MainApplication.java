package com.wytianxiatuan.wytianxia.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wytianxiatuan.wytianxia.util.CrashHandler;

import org.xutils.x;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class MainApplication extends MultiDexApplication {
    public static int version;
    public static LinkedList<Activity> activityLinkedList;
    public static String weiChatAppId ="wx9054c6812c7f05b6";
    public static IWXAPI weiChatApi;
    public static boolean isMain = false;
    public static boolean isMy = false;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        CrashHandler.getInstance().init(this);
        activityLinkedList = new LinkedList<>();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityLinkedList.add(activity);
            }
            @Override
            public void onActivityStarted(Activity activity) {}
            @Override
            public void onActivityResumed(Activity activity) {}
            @Override
            public void onActivityPaused(Activity activity) {}
            @Override
            public void onActivityStopped(Activity activity) {}
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override
            public void onActivityDestroyed(Activity activity) {
                activityLinkedList.remove(activity);
            }
        });
        /**注册应用到微信*/
        registToWeiChat();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void exitApp(){
        for (Activity activity:activityLinkedList){
            activity.finish();
        }
        System.exit(0);
    }
    private void registToWeiChat(){
        weiChatApi = WXAPIFactory.createWXAPI(this,weiChatAppId,true);
        weiChatApi.registerApp(weiChatAppId);
    }
}
