package com.wytianxiatuan.wytianxia.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by liuju on 2018/1/13.
 */

public class CommonUtil {
    /**
     * 获取导航栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    /**
     * 获取当前应用的版本号
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        PackageInfo packages = manager.getPackageInfo(context.getPackageName(),0);
        return packages.versionCode;
    }
    public static int[] getScreenInfo(Activity activity) {
        int[] info;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        info = new int[]{width, height};
        return info;
    }
    public static void goToSamsungappsMarket(Context context) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + context.getPackageName());
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void shareAppShop(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (context != null) context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "您没有安装应用市场", Toast.LENGTH_SHORT).show();
        }
    }

    public static String formatTosepara(double data) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(data);
    }
    public static String getVersonName(Context context){
        PackageManager manager = context.getPackageManager();
        String name;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName() , 0);
            name = info.versionName;
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /***
     * 加载asset资源文件夹的资源文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取当前位置信息
     * @param context
     * @return
     */
    public static String Location(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> locationList = geocoder.getFromLocation(latitude,longitude,1);
                Address address = locationList.get(0);
                String city = address.getLocality();
                return city;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String urlDecode(String url){
        try {
            return URLDecoder.decode(url,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
