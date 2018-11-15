package com.wytianxiatuan.wytianxia.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wytianxiatuan.wytianxia.bean.LocationBean;

/**
 * Created by liuju on 2018/3/26.
 */

public class LocationUtils implements AMapLocationListener{
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private Context context;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private LocationBean locationBean;
    public static LocationUtils instance;
    private LocationResultListener locationResultListener;
    private LocationUtils(Context context,LocationResultListener listener){
        this.context = context;
        this.locationResultListener = listener;
    }
    public static LocationUtils getInstance(Context context,LocationResultListener listener){
        if (instance == null){
            synchronized (LocationUtils.class){
                if (instance == null) instance = new LocationUtils(context,listener);
            }
        }
        return instance;
    }
    public void getLocation(){
        locationBean = new LocationBean();
        mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(this);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        int code = aMapLocation.getErrorCode();
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0){
            locationBean.setLocationType(aMapLocation.getLocationType());
            locationBean.setLongitude(aMapLocation.getLongitude());
            locationBean.setLatitude(aMapLocation.getLatitude());
            locationBean.setAddress(aMapLocation.getAddress());
            locationBean.setCountry(aMapLocation.getCountry());
            locationBean.setProvice(aMapLocation.getProvince());
            locationBean.setCity(aMapLocation.getCity());
            locationBean.setCityCode(aMapLocation.getCityCode());
            locationBean.setDistrict(aMapLocation.getDistrict());
            locationBean.setStreet(aMapLocation.getStreet());
            locationBean.setStreetNum(aMapLocation.getStreetNum());
            locationBean.setCityCode(aMapLocation.getCityCode());
            if (locationResultListener != null) locationResultListener.locationResult(locationBean);
        }
    }
    public interface LocationResultListener{
        void locationResult(LocationBean locationBean);
    }
    public void stopLocation(){
        if (mLocationClient != null){
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
}
