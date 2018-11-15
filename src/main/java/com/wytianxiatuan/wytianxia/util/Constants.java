package com.wytianxiatuan.wytianxia.util;

import android.os.Build;

import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.WelcomeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class Constants {
    public static String NETWORK_ERROR = "网络出问题了，请稍后重试！";
    public static String serverError = "服务器繁忙，请稍后再试！";
    public static String timeOut = "网络不给力，连接超时，请稍后再试";
    public static String userAgent = "#my-app-android-"+ MainApplication.version+"-"+ Build.MODEL+"-"+Build.VERSION.RELEASE+"#";
    public static String versionCode;
    public static Map<String,String> startDesMap = new HashMap<>();
    public static List<String> selectIds = new ArrayList<>();
    /**线上*/
    public static String webUrl = "http://m.wangyingtianxiatuan.com/";
    public static String url = "http://www.wangyingtianxiatuan.com/";
    public static String cookieUrl = "wangyingtianxiatuan.com";

    /**测试*/
//    public static String webUrl = "http://zm.qqyxiang.com/wytx/public/index.php/";
//    public static String url = "http://z.qqyxiang.com/wytx/public/index.php/";
//    public static String cookieUrl = "qqyxiang.com";

    public static String captcha = webUrl + "authtoken/image?width=100&height=40&text=1&mt=";
    public static String userId = "0";
    public static String userPhone=null;
    public static String currentCity = "";
    public static WelcomeBean welcomeBean;
    public static ExecutorService pool = Executors.newFixedThreadPool(20);

    /**以下是api地址*/
    public static String splashPage = url + "v1/config/index";
    /**首页顶部数据*/
    public static String mainTop = url + "v1/index/index";
    /**首页底部数据*/
    public static String mainBottom = url + "v1/index/get_goods";
    /**所有分类数据*/
    public static String allClassify = url + "v1/goods/category/index";
    /**获取验证码*/
    public static String captchaMsg = url + "v1/login/send_captcha";
    /**二级分类数据*/
    public static String classify = url + "v1/goods/list";
    /**登录*/
    public static String login = url + "v1/login/captcha_login";
    /**收货地址列表*/
    public static String assressList = url + "v1/user/address/index";
    /**添加收货地址*/
    public static String addAddress = url + "v1/user/address/add";
    /**编辑收货地址*/
    public static String editAddress = url + "v1/user/address/edit";
    /**删除收货地址*/
    public static String deleteAddress = url +"v1/user/address/delete";
    /**商品详情*/
    public static String goodsDetail = url + "v1/goods/detail";
    /**收藏商品*/
    public static String collectGood = url + "v1/user/collect/add_goods";
    /**取消收藏商品*/
    public static String cancelCollect = url + "v1/user/collect/delete_goods";
    /**我的店铺收藏*/
    public static String shopCollect = url + "v1/user/collect/shop";
    /**我的商品收藏*/
    public static String goodCollect = url +"v1/user/collect/index";
    /**可用优惠券*/
    public static String canUseTicket = url + "v1/user/coupon/index";
    /**不可用优惠券*/
    public static String notUseTicket = url + "v1/user/coupon/invalid";
    /**店铺*/
    public static String shop = webUrl +"shop";
    /**动态*/
    public static String dynamic = url +"v1/news/action";
    /**动态详情*/
    public static String dynamicDetail = webUrl +"news/detail";
    /**申请成为商户*/
    public static String toBeSeller = url +"v1/user/shop/apply";
    /**店铺商品数据*/
    public static String merchantShops = url + "v1/shop/get_goods";
    /**店铺的数据*/
    public static String shopIsCollect = url + "v1/shop/index";
    /**收藏店铺*/
    public static String collectShop = url + "v1/user/collect/add_shop";
    /**取消收藏店铺*/
    public static String cancelShopCollect = url + "v1/user/collect/delete_shop";
    /**购物车数据列表*/
    public static String shopCarList = url + "v1/user/cart/index";
    /**删除购物车商品*/
    public static String deleteShopCarGood = url + "v1/user/cart/clean_invalid_goods";
    /**加购或者减购商品*/
    public static String minusOrPlusGood = url + "v1/user/cart/update_cart_goods_count";
    /**购物车结算*/
    public static String shopCarBuy = url + "v1/user/trade/confirm";
    /**清空购物车失效的宝贝*/
    public static String clearInvaildGoods = url + "v1/user/cart/clean_invalid_goods";
    /**确认支付*/
    public static String paySure = url + "v1/user/trade/buy";
    /**个人中心*/
    public static String myCenter = url + "v1/user/home/index";
    /**订单管理*/
    public static String orderManage = url + "v1/user/order/index";
    /**订单详情*/
    public static String orderDetail = url + "v1/user/order/detail";
    /**评价订单*/
    public static String ratingOrder = url + "v1/user/order/comment";
    /**取消订单*/
    public static String cancelOrder = url + "v1/user/trade/cancel";
    /**申请退款*/
    public static String refundOrder = url + "v1/user/order/after";
    /**删除订单*/
    public static String deleteOrder = url + "v1/user/order/delete";
    /**取消申请退款*/
    public static String cancelApply = url + "v1/user/order/after_cancel";
    /**商品评价*/
    public static String commentGood = url + "v1/goods/get_comment";
    /**添加商品到购物车*/
    public static String addToShopCar = url + "v1/user/cart/add";
    /**微信登录*/
    public static String weichatLogin = url + "v1/login/weixin/app";
    /**确认收货*/
    public static String sureTakeGood = url + "v1/user/order/confirm";
    /**订单管理 订单支付*/
    public static String orderManagePaySure = url +"v1/user/trade/pay";
    /**注册协议*/
    public static String registerNotice = webUrl + "help/agr.html";
}
