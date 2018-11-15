package com.wytianxiatuan.wytianxia.present;

import android.content.Context;

import com.wytianxiatuan.wytianxia.module.MainInterface;
import com.wytianxiatuan.wytianxia.util.CallBack;
import com.wytianxiatuan.wytianxia.view.classify.IGoodListView;
import com.wytianxiatuan.wytianxia.view.my.AddAddressView;

import org.xutils.common.Callback;

import java.util.Map;

/**
 * Created by liujun on 2018/1/10 0010.
 */

public class MainPresenter {
    private Context context;
    private MainInterface mainInterface;
    private IMainView iMainView;
    private AddAddressView addressView;
    private IGoodListView goodListView;

    public MainPresenter(Context context, IMainView iMainView) {
        this.context = context;
        this.iMainView = iMainView;
        mainInterface = MainInterface.getINSTANCE();
    }
    public MainPresenter(Context context, AddAddressView addressView) {
        this.context = context;
        this.addressView = addressView;
        mainInterface = MainInterface.getINSTANCE();
    }
    public MainPresenter(Context context, IGoodListView goodListView) {
        this.context = context;
        this.goodListView = goodListView;
        mainInterface = MainInterface.getINSTANCE();
    }
    /**
     * 获取公共配置
     */
    public Callback.Cancelable getConfig(){
        return  mainInterface.getConfig(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 获取首页顶部数据
     * @return
     */
    public CallBack.Cancelable getMainTopData(){
        return mainInterface.getMainData(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 获取首页底部数据
     * @return
     */
    public Callback.Cancelable getMainBottom(int flag ,String page){
        return mainInterface.getMainBottom(iMainView.getUrl(),context,flag,page,new MainListener(iMainView));
    }

    /**
     * 获取所有的分类
     * @return
     */
    public Callback.Cancelable getAllClassify(){
        return mainInterface.getClassifyAll(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 获取二级分类
     * @param autoId
     * @return
     */
    public Callback.Cancelable getClassify(String autoId,String page,int flag){
        return mainInterface.getClassify(iMainView.getUrl(),autoId,page,flag,context,new MainListener(iMainView));
    }

    /**
     * 获取登录验证码
     * @param phone
     * @param captcha
     * @param isVoice
     * @param flag
     * @return
     */
    public Callback.Cancelable getMsg(String phone,String captcha,String isVoice,int flag){
        return mainInterface.getCaptchaMsg(iMainView.getUrl(),phone,captcha,isVoice,flag,context,new MainListener(iMainView));
    }

    /**
     * 登录
     * @param phone
     * @param msg
     * @return
     */
    public Callback.Cancelable login(String phone ,String msg){
        return mainInterface.login(iMainView.getUrl(),phone,msg,context,new MainListener(iMainView));
    }

    /**
     * 获取收货地址
     * @return
     */
    public Callback.Cancelable getAddress(){
        return mainInterface.addressList(addressView.getUrl(),context,new MainListener(addressView));
    }

    /**0
     * 添加收货地址
     * @return
     */
    public Callback.Cancelable addAddress(){
        return mainInterface.addAddress(addressView.autoId(),addressView.getUrl(),addressView.getName(),
                addressView.getPhone(),addressView.getArea(),addressView.getDetail(),
                addressView.isDefault(),addressView.flag(),context,new MainListener(addressView));
    }

    /**
     * 删除收货地址
     * @return
     */
    public Callback.Cancelable deleteAddress(){
        return mainInterface.deleteAddress(addressView.autoId(),addressView.getUrl(),context,new MainListener(addressView));
    }

    /**
     * 商品列表
     * @param flag
     * @return
     */
    public Callback.Cancelable getGoodList(int flag,int isMain){
        return mainInterface.getGoodList(goodListView.getUrl(),goodListView.getWd(),goodListView.getOrderId(),
                goodListView.getCatId(),goodListView.page(),flag,isMain,goodListView.isOrder(),context,new MainListener(goodListView));
    }

    /**
     * 商品详情
     * @return
     */
    public Callback.Cancelable getGoodDetail(){
        return mainInterface.goodsDetail(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 收藏商品获取取消商品
     * @param flag
     * @param id
     * @return
     */
    public Callback.Cancelable collectGood(int flag,String id){
        return mainInterface.collectGood(iMainView.getUrl(),id,flag,context,new MainListener(iMainView));
    }

    /**
     * 我的店铺收藏
     * @param flag
     * @return
     */
    public Callback.Cancelable shopCollect(int flag){
        return mainInterface.shopCollect(iMainView.getUrl(),context,flag,new MainListener(iMainView));
    }

    /**
     * 我的商品收藏
     * @param flag
     * @return
     */
    public Callback.Cancelable goodCollect(int flag){
        return mainInterface.goodsCollect(iMainView.getUrl(),context,flag,new MainListener(iMainView));
    }

    /**
     * 优惠券列表
     * @return
     */
    public Callback.Cancelable getTicket(){
        return mainInterface.getTicket(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 动态
     * @param flag
     * @return
     */
    public Callback.Cancelable getDyanmic(int flag){
        return mainInterface.getDynamic(iMainView.getUrl(),flag,context,new MainListener(iMainView));
    }

    /**
     * 申请成为商家
     * @param maps
     * @return
     */
    public Callback.Cancelable toBeSeller(Map<String,String> maps){
        return mainInterface.toBeSeller(iMainView.getUrl(),maps,context,new MainListener(iMainView));
    }

    /**
     * 店铺
     * @param shopId
     * @param isNew
     * @param wd
     * @param page
     * @param flag
     * @return
     */
    public Callback.Cancelable merchantShops(String shopId,String isNew,String wd,String page,int flag){
        return mainInterface.merchantShops(iMainView.getUrl(),shopId,isNew,wd,flag,page,context,new MainListener(iMainView));
    }

    /**
     *购物车
     * @return
     */
    public Callback.Cancelable shopCarList(){
        return mainInterface.shopCarList(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 删除购物车商品
     * @param goodId
     * @return
     */
    public Callback.Cancelable deleteShopCarGood(String goodId){
        return mainInterface.deleteShopCarGood(iMainView.getUrl(),goodId,context,new MainListener(iMainView));
    }

    /**
     * 加购或者减购商品
     * @param goodId
     * @param minusOrPlus
     * @return
     */
    public Callback.Cancelable minusOrPlusGood(String goodId,String minusOrPlus){
        return mainInterface.minusOrPlusGood(iMainView.getUrl(),goodId,minusOrPlus,context,new MainListener(iMainView));
    }

    /**
     * 购物车结算
     * @return
     */
    public Callback.Cancelable shopCarAccount(){
        return mainInterface.shopCarAccount(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 清空购物车失效商品
     * @return
     */
    public Callback.Cancelable clearShopCar(){
        return mainInterface.clearShopCarGood(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 个人中心
     * @return
     */
    public Callback.Cancelable myCenter(){
        return mainInterface.myCenter(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 订单管理
     * @param flag
     * @return
     */
    public Callback.Cancelable orderManage(int flag){
        return mainInterface.orderManage(iMainView.getUrl(),flag,context,new MainListener(iMainView));
    }

    /**
     * 订单详情
     * @return
     */
    public Callback.Cancelable orderDetail(){
        return mainInterface.orderDetail(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 订单评价
     * @param json
     * @return
     */
    public Callback.Cancelable rateOrder(String json){
        return mainInterface.rateOrder(iMainView.getUrl(),json,context,new MainListener(iMainView));
    }

    /**
     * 取消订单
     * @param orderId  订单id
     * @return
     */
    public Callback.Cancelable cancelOrder(String orderId,int flag){
        return mainInterface.cancelOrder(iMainView.getUrl(),orderId,flag,context,new MainListener(iMainView));
    }

    /**
     * 申请退款售后
     * @param orderId
     * @param telephone
     * @return
     */
    public Callback.Cancelable refundOrder(String orderId,String telephone){
        return mainInterface.refundOrder(iMainView.getUrl(),orderId,telephone,context,new MainListener(iMainView));
    }

    /**
     * 商品评论内容
     * @return
     */
    public Callback.Cancelable commentGood(){
        return mainInterface.commentGood(iMainView.getUrl(),context,new MainListener(iMainView));
    }

    /**
     * 添加商品到购物车
     * @param goodId
     * @param skuId
     * @param count
     * @return
     */
    public Callback.Cancelable addGoodToShopCar(String goodId,String skuId,int count){
        return mainInterface.addGoodToShopCar(iMainView.getUrl(),goodId,skuId,count,context,new MainListener(iMainView));
    }

    /**
     * 微信登录
     * @param code
     * @return
     */
    public Callback.Cancelable weiChatLogin(String code){
        return mainInterface.weiChatLogin(iMainView.getUrl(),code,context,new MainListener(iMainView));
    }

    /**
     * 提交订单 发起支付
     * @param jsonParams
     * @return
     */
    public Callback.Cancelable rightBuy(String jsonParams){
        return mainInterface.rightBuy(iMainView.getUrl(),jsonParams,context,new MainListener(iMainView));
    }
}
