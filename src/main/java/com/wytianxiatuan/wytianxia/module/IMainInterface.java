package com.wytianxiatuan.wytianxia.module;

import android.content.Context;

import org.xutils.common.Callback;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public interface IMainInterface {
    /**获取首页公共配置 app更新*/
    Callback.Cancelable getConfig(String url, Context context, BaseListener listener);
    /**首页顶部数据*/
    Callback.Cancelable getMainData(String url, Context context, BaseListener listener);
    /**首页底部数据*/
    Callback.Cancelable getMainBottom(String url, Context context, int flag, String page, BaseListener listener);
    /**所有分类数据*/
    Callback.Cancelable getClassifyAll(String url ,Context context ,BaseListener listener);
    /**获取手机验证码*/
    Callback.Cancelable getCaptchaMsg(String url ,String telephone,String captacha ,String isVoice,int flag ,Context context,BaseListener listener);
    /**获取物品二级分类*/
    Callback.Cancelable getClassify(String url ,String autoId,String page,int flag,Context context,BaseListener listener);
    /**登录*/
    Callback.Cancelable login(String url ,String phone,String msg,Context context,BaseListener listener);
    /**收货地址列表*/
    Callback.Cancelable addressList(String url,Context context,BaseListener listener);
    /**添加收货地址*/
    Callback.Cancelable addAddress(String autoId,String url ,String name,String phone,String area,String detail ,String isDeault,int flag,Context context,BaseListener listener);
    /**删除收货地址*/
    Callback.Cancelable deleteAddress(String autoId,String url,Context context,BaseListener listener);
    /**商品列表*/
    Callback.Cancelable getGoodList(String url ,String wd,String order,String cateId,String page,int flag,int isMain,boolean isOrder,Context context,BaseListener listener);
    /**商品详情*/
    Callback.Cancelable goodsDetail(String url,Context context,BaseListener listener);
    /**收藏或者取消商品收藏*/
    Callback.Cancelable collectGood(String url,String id,int flag,Context context,BaseListener listener);
    /**我的店铺收藏*/
    Callback.Cancelable shopCollect(String url,Context context,int flag,BaseListener listener);
    /***商品收藏*/
    Callback.Cancelable goodsCollect(String url,Context context,int flag,BaseListener listener);
    /**优惠券列表*/
    Callback.Cancelable getTicket(String url,Context context,BaseListener listener);
    /**动态列表*/
    Callback.Cancelable getDynamic(String url ,int flag ,Context context ,BaseListener listener);
    /**申请成为商户*/
    Callback.Cancelable toBeSeller(String url , Map<String,String> maps,Context context,BaseListener listener);
    /**商户店铺*/
    Callback.Cancelable merchantShops(String url,String shopId,String isNew,String wd,int flag,String page,Context context,BaseListener listener);
    /**购物车数据列表*/
    Callback.Cancelable shopCarList(String url , Context context,BaseListener listener);
    /**删除购物车商品*/
    Callback.Cancelable deleteShopCarGood(String url , String goodId,Context context,BaseListener listener);
    /**增加或者减少购车商品*/
    Callback.Cancelable minusOrPlusGood(String url ,String goodId,String minusOrPlus,Context context,BaseListener listener);
    /**购物车结算*/
    Callback.Cancelable shopCarAccount(String url,Context context,BaseListener listener);
    /**清空购物车的失效物品*/
    Callback.Cancelable clearShopCarGood(String url,Context context,BaseListener listener);
    /**个人中心*/
    Callback.Cancelable myCenter(String url ,Context context,BaseListener listener);
    /**订单管理*/
    Callback.Cancelable orderManage(String url,int flag,Context context,BaseListener listener);
    /**订单详情*/
    Callback.Cancelable orderDetail(String url,Context context,BaseListener listener);
    /**评价订单*/
    Callback.Cancelable rateOrder(String url,String json ,Context context,BaseListener listener);
    /**取消订单*/
    Callback.Cancelable cancelOrder(String url,String orderId,int flag ,Context context,BaseListener listener);
    /**申请退款*/
    Callback.Cancelable refundOrder(String url,String orderId,String telephone,Context context,BaseListener listener);
    /**商品评价功能*/
    Callback.Cancelable commentGood(String url,Context context,BaseListener listener);
    /**添加商品到购物车*/
    Callback.Cancelable addGoodToShopCar(String url ,String goodId,String sku_id,int count,Context context,BaseListener listener);
    /**微信登录*/
    Callback.Cancelable weiChatLogin(String url,String code,Context context,BaseListener listener);
    /**确认支付*/
    Callback.Cancelable rightBuy(String url ,String jsonParams,Context context,BaseListener listener);
}
