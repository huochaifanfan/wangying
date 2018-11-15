package com.wytianxiatuan.wytianxia.module;

import android.content.Context;

import com.wytianxiatuan.wytianxia.bean.AddressBean;
import com.wytianxiatuan.wytianxia.bean.ClassifyBean;
import com.wytianxiatuan.wytianxia.bean.ClassifyRightSecondBean;
import com.wytianxiatuan.wytianxia.bean.CommentBean;
import com.wytianxiatuan.wytianxia.bean.DynaMicBean;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;
import com.wytianxiatuan.wytianxia.bean.MainBean;
import com.wytianxiatuan.wytianxia.bean.MyCenterBean;
import com.wytianxiatuan.wytianxia.bean.MyGoodsCollectBean;
import com.wytianxiatuan.wytianxia.bean.MyOrderBean;
import com.wytianxiatuan.wytianxia.bean.MyShopCollectBean;
import com.wytianxiatuan.wytianxia.bean.MyTicketBean;
import com.wytianxiatuan.wytianxia.bean.OrderDetailBean;
import com.wytianxiatuan.wytianxia.bean.PaySureBean;
import com.wytianxiatuan.wytianxia.bean.RightBuyBean;
import com.wytianxiatuan.wytianxia.bean.ShopBean;
import com.wytianxiatuan.wytianxia.bean.WelcomeBean;
import com.wytianxiatuan.wytianxia.util.CallBack;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.CookieUtil;
import com.wytianxiatuan.wytianxia.util.HttpUtils;
import com.wytianxiatuan.wytianxia.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class MainInterface extends BaseBiz implements IMainInterface{
    private volatile static MainInterface INSTANCE;
    private MainInterface(){}
    public static MainInterface getINSTANCE(){
        if (INSTANCE == null){
            synchronized (MainInterface.class){
                if (INSTANCE == null) INSTANCE = new MainInterface();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取公共配置 app更新等
     * @param url
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getConfig(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        WelcomeBean bean = JsonUtils.getConfig(object);
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 首页顶部数据
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getMainData(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        MainBean mainBean = JsonUtils.getMainTopData(object);
                        if (listener != null) listener.getDataSuccess(mainBean);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 首页底部数据
     * @param url
     * @param context
     * @param flag
     * @param page
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getMainBottom(String url, Context context, final int flag, String page, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("page",page);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        List<MainBean.Recommand> data = JsonUtils.getMainBottom(jsonObject);
                        if (listener != null) listener.getDataSuccess(data , flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 获取所有的商品分类
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getClassifyAll(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<ClassifyBean> data = JsonUtils.getClassify(object);
                        if (listener != null) listener.getDataSuccess(data);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 获取手机验证码
     * @param url
     * @param telephone
     * @param captacha 当有图形验证码的时候需要此参数
     * @param isVoice 是否语音验证码
     * @param flag 控制是否需要验证码的参数
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getCaptchaMsg(String url, String telephone, String captacha, String isVoice, int flag, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("telephone",telephone);
        params.addBodyParameter("is_voice",isVoice);
        if (flag == 1){
            params.addBodyParameter("captcha",captacha);
        }
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(status,result);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }

    /**
     *获取物品二级分类
     * @param url
     * @param autoId
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getClassify(String url, String autoId, String page,final int flag,Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("cat_id",autoId);
        params.addBodyParameter("is_get_cat","0");/**传这个参数不返回分类的信息*/
        params.addBodyParameter("page",page);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<ClassifyRightSecondBean> data = JsonUtils.getClassifySecond(object);
                        if (listener != null) listener.getDataSuccess(data,"second",flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    @Override
    public Callback.Cancelable login(String url, String phone, String msg, final Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("telephone",phone);
        params.addBodyParameter("captcha",msg);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        CookieUtil.saveCookie(context);
                        JSONObject user = jsonObject.getJSONObject("data").getJSONObject("user");
                        Constants.userId = user.getString("user_id");
                        if (listener != null) listener.getDataSuccess("success",result);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }

    /**
     * 收货地址列表
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable addressList(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable callBack = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<AddressBean> data = JsonUtils.getAddress(object);
                        if (listener != null) listener.getDataSuccess(data);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return callBack;
    }

    /**
     * 添加收货地址
     * @param url
     * @param name
     * @param phone
     * @param area
     * @param detail
     * @param isDeault
     * @return
     */
    @Override
    public Callback.Cancelable addAddress(String autoId,String url, String name, String phone, String area, String detail, String isDeault, final int flag, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("name",name);
        params.addBodyParameter("telephone",phone);
        params.addBodyParameter("area" ,area);
        params.addBodyParameter("detail",detail);
        params.addBodyParameter("is_def",isDeault);
        if (flag ==1) params.addBodyParameter("id",autoId);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 删除收货地址
     * @param autoId
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable deleteAddress(String autoId, String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",autoId);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("delete");
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     *
     * @param url
     * @param wd 搜索关键字
     * @param order 排序id
     * @param cateId 分类id
     * @param page 分页
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getGoodList(String url, String wd, String order, String cateId,
                                           String page, final int flag, int isMain,final boolean isOrder , Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        if (isMain == 1){
            params.addBodyParameter("is_cash","1");
        }else if (isMain == 2){
            params.addBodyParameter("is_promote","1");
        }else if (isMain == 3){
            params.addBodyParameter("is_quyang","1");
        }else {
            params.addBodyParameter("wd",wd);
            params.addBodyParameter("order",order);
            params.addBodyParameter("cat_id",cateId);
        }
        params.addBodyParameter("page",page);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context, params,new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        GoodsListBean bean = JsonUtils.getGoodsList(object,isOrder);
                        if (listener != null) listener.getDataSuccess(bean ,flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 商品详情
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable goodsDetail(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        GoodDetailBean bean = JsonUtils.getGoodDetail(object);
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 收藏或者取消收藏商品
     * @param url
     * @param id
     * @param flag
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable collectGood(String url, String id, final int flag, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("res_id",id);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 我的店铺搜藏
     * @param url
     * @param context
     * @param flag
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable shopCollect(String url, Context context, final int flag, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<MyShopCollectBean> data = JsonUtils.shopCollect(object);
                        if (listener != null) listener.getDataSuccess(data,flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 我的商品收藏
     * @param url
     * @param context
     * @param flag
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable goodsCollect(String url, Context context, final int flag, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<MyGoodsCollectBean> data = JsonUtils.goodCollect(object);
                        if (listener != null) listener.getDataSuccess(data,flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 优惠券列表
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable getTicket(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<MyTicketBean> data = JsonUtils.getTicket(object);
                        if (listener != null) listener.getDataSuccess(data);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    @Override
    public Callback.Cancelable getDynamic(String url, final int flag , Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<DynaMicBean> data = JsonUtils.getDyanmic(object);
                        if (listener != null) listener.getDataSuccess(data,flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 申请成为商户
     * @param url
     * @param maps
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable toBeSeller(String url, Map<String, String> maps, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        if (maps != null && maps.size()>0){
            for (String key:maps.keySet()){
                params.addBodyParameter(key,maps.get(key));
            }
        }
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess(status);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    @Override
    public Callback.Cancelable merchantShops(String url, String shopId, String isNew, String wd, final int flag, String page, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("shop_id",shopId);
        params.addBodyParameter("is_new",isNew);
        params.addBodyParameter("wd",wd);
        params.addBodyParameter("page",page);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        List<MainBean.Recommand> data = JsonUtils.getShops(object);
                        if (listener != null) listener.getDataSuccess(data,flag);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }
    /**
     * 购物车数据列表
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable shopCarList(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        ShopBean data = JsonUtils.shopCarList(object);
                        if (listener != null) listener.getDataSuccess(data);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 删除购物车商品
     * @param url
     * @param goodId
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable deleteShopCarGood(String url, String goodId, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("ids",goodId);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("delete");
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 加购或者减购商品
     * @param url
     * @param goodId
     * @param minusOrPlus
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable minusOrPlusGood(String url, String goodId, String minusOrPlus, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",goodId);
        params.addBodyParameter("is_inc",minusOrPlus);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        int count = object.getInt("count");
                        if (listener != null) listener.getDataSuccess("minusOrPlus",count);
                    }else {
                        getFailer(status , jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 购物车结算
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable shopCarAccount(String url,Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        RightBuyBean bean = JsonUtils.shopCarAccount(object);
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 清空购物车失效的物品
     * @param url
     * @param context
     * @return
     */
    @Override
    public Callback.Cancelable clearShopCarGood(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("clear");
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 个人中心
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable myCenter(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        MyCenterBean bean = JsonUtils.myCenter(jsonObject.getJSONObject("data"));
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 订单管理
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable orderManage(String url,final int flag ,Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        MyOrderBean bean = JsonUtils.myOrder(jsonObject.getJSONObject("data"));
                        if (listener != null) listener.getDataSuccess(bean,flag);
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 订单详情
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable orderDetail(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        OrderDetailBean bean = JsonUtils.orderDetail(jsonObject.getJSONObject("data"));
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 评价订单
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable rateOrder(String url, String json,Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(json);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener !=null) listener.getDataSuccess("comment");
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 取消订单
     * @param url
     * @param orderId
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable cancelOrder(String url, String orderId, final int flag, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",orderId);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("cancel",flag);
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 申请退款
     * @param url
     * @param orderId
     * @param telephone
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable refundOrder(String url, String orderId, String telephone, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",orderId);
        params.addBodyParameter("telephone",telephone);
        Callback.Cancelable cancelable =HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("refund");
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 商品评价
     * @param url
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable commentGood(String url, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.GET,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        List<CommentBean> data = JsonUtils.getComment(jsonObject.getJSONObject("data"));
                        if (listener!=null) listener.getDataSuccess(data,"comment");
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 添加商品到购物车
     * @param url
     * @param goodId
     * @param sku_id
     * @param count
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable addGoodToShopCar(String url, String goodId, String sku_id, int count, Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("goods_id",goodId);
        params.addBodyParameter("sku_id",sku_id);
        params.addBodyParameter("count",count+"");
       Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener !=null) listener.getDataSuccess("add");
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    /**
     * 微信登录
     * @param url
     * @param code
     * @param context
     * @param listener
     * @return
     */
    @Override
    public Callback.Cancelable weiChatLogin(String url, String code, final Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("code",code);
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
//                    Toast.makeText(context,result,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        if (listener != null) listener.getDataSuccess("success");
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (JSONException e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }

    @Override
    public Callback.Cancelable rightBuy(final String url, final String jsonParams, final Context context, final BaseListener listener) {
        RequestParams params = new RequestParams(url);
        if (jsonParams != null && jsonParams.startsWith("{")&&jsonParams.endsWith("}")){
            params.setAsJsonContent(true);
            params.setBodyContent(jsonParams);
        }else {
            params.addBodyParameter("trade_num",jsonParams);
        }
        Callback.Cancelable cancelable = HttpUtils.send(HttpMethod.POST,context,params, new CallBack<String>(listener) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("200".equals(status)){
                        JSONObject object = jsonObject.getJSONObject("data");
                        PaySureBean bean = JsonUtils.getPayParams(object);
                        if (listener != null) listener.getDataSuccess(bean);
                    }else {
                        getFailer(status,jsonObject.getString("info"),listener);
                    }
                } catch (Exception e) {
                    analysisFailer(listener,result,e.toString());
                }
            }
        });
        return cancelable;
    }
}
