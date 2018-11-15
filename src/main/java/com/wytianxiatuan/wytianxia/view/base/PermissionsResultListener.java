package com.wytianxiatuan.wytianxia.view.base;

/**
 * Created by Administrator on 2017/3/13 0013.
 * 封装全局权限处理的接口
 */
public interface PermissionsResultListener {
    /**权限被授予*/
    void onPermissionGranted();
    /**权限被拒绝*/
    void onPermissionDenied();
}
