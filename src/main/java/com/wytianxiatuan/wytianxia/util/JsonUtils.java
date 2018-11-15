package com.wytianxiatuan.wytianxia.util;

import com.wytianxiatuan.wytianxia.bean.AddressBean;
import com.wytianxiatuan.wytianxia.bean.ClassifyBean;
import com.wytianxiatuan.wytianxia.bean.ClassifyRightSecondBean;
import com.wytianxiatuan.wytianxia.bean.CommentBean;
import com.wytianxiatuan.wytianxia.bean.DynaMicBean;
import com.wytianxiatuan.wytianxia.bean.GoodDetailBean;
import com.wytianxiatuan.wytianxia.bean.GoodsListBean;
import com.wytianxiatuan.wytianxia.bean.JsonBean;
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
import com.wytianxiatuan.wytianxia.bean.WeiChatBean;
import com.wytianxiatuan.wytianxia.bean.WelcomeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuju on 2018/1/13.
 */

public class JsonUtils {
    /**
     * 获取公共配置
     * @param object
     * @return
     */
    public static WelcomeBean getConfig(JSONObject object) throws JSONException {
        getUserInfo(object);
        WelcomeBean bean = new WelcomeBean();
        if (object.get("splash") instanceof JSONObject){
            JSONObject jsonObject = object.getJSONObject("splash");
            bean.setTitle(jsonObject.getString("title"));
            bean.setImage(jsonObject.getString("image_url"));
            bean.setLink(jsonObject.getString("href"));
            bean.setLinkType(jsonObject.getString("link_type"));
            bean.setRedirectType(jsonObject.getString("redirect_type"));
            bean.setRedirectId(jsonObject.getString("redirect_id"));
        }
        if (object.get("version") instanceof JSONObject) {
            JSONObject obj = object.getJSONObject("version");
            bean.setVersionCode(obj.getInt("version_code"));
            bean.setVersionName(obj.getString("version_name"));
            bean.setAddress(obj.getString("address"));
            bean.setSize(obj.getString("content"));
            bean.setUpdateContent(obj.getString("content"));
            bean.setIsForce(obj.getString("is_force"));
        }
        return bean;
    }

    /**
     * 首页上部数据
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static MainBean getMainTopData(JSONObject jsonObject) throws JSONException {
        getUserInfo(jsonObject);
        MainBean mainBean = new MainBean();
        /**banner*/
        JSONArray bannerArray = jsonObject.getJSONArray("banner");
        if (bannerArray != null && bannerArray.length()>0){
            List<MainBean.Banner> data = new ArrayList<>();
            for (int i=0;i<bannerArray.length();i++){
                MainBean.Banner entity = new MainBean.Banner();
                JSONObject object = bannerArray.getJSONObject(i);
                entity.setTitle(object.getString("title"));
                entity.setImage(object.getString("image_url"));
                entity.setImageLink(object.getString("href"));
                entity.setLinkType(object.getString("link_type"));
                entity.setRedirectType(object.getString("redirect_type"));
                entity.setRedirectId(object.getString("redirect_id"));
                data.add(entity);
            }
            mainBean.setBannerData(data);
        }
        /**分类*/
        JSONArray categoryArray = jsonObject.getJSONArray("category");
        if (categoryArray != null && categoryArray.length()>0){
            List<MainBean.CateGory> data = new ArrayList<>();
            for (int i =0;i<categoryArray.length();i++){
                MainBean.CateGory entity = new MainBean.CateGory();
                JSONObject object = categoryArray.getJSONObject(i);
                entity.setAutoId(object.getInt("auto_id"));
                entity.setImageUrl(object.getString("image_url"));
                entity.setName(object.getString("name"));
                data.add(entity);
            }
            mainBean.setCateGoryData(data);
        }
        /**发展*/
        JSONObject attr = jsonObject.getJSONObject("attr");
        mainBean.setRequireMentLink(attr.getString("zhaopin"));
        if (attr.has("fazhan")){
            JSONObject develop = attr.getJSONObject("fazhan");
            mainBean.setDevelopTitle(develop.getString("title"));
            mainBean.setDevelopLink(develop.getString("link"));
        }
        mainBean.setPeijianId(attr.getString("peijian_id"));
        mainBean.setJiagongId(attr.getString("jiagong_id"));
        /**动态*/
        JSONObject object = jsonObject.getJSONObject("news");
        mainBean.setMainDynamicId(object.getString("auto_id"));
        mainBean.setMainDynamicTitle(object.getString("title"));
        mainBean.setMainDynamicLink(object.getString("href"));
        JSONObject middle = jsonObject.getJSONObject("midAdd");
        mainBean.setMiddleAutoId(middle.getInt("auto_id"));
        mainBean.setMiddleTitle(middle.getString("title"));
        mainBean.setMiddleImageUrl(middle.getString("image_url"));
        mainBean.setMiddleLink(middle.getString("href"));
        mainBean.setMiddleLinkType(middle.getString("link_type"));
        mainBean.setMiddleRedirectType(middle.getString("redirect_type"));
        mainBean.setMiddleRedirectId(middle.getString("redirect_id"));
        /**星级厂家*/
        JSONArray shopArray = jsonObject.getJSONArray("shop");
        if (shopArray != null && shopArray.length() >0){
            List<MainBean.MainShops> data = new ArrayList<>();
            for (int i =0;i<shopArray.length();i++){
                MainBean.MainShops entity = new MainBean.MainShops();
                JSONObject obj = shopArray.getJSONObject(i);
                entity.setUserId(obj.getInt("user_id"));
                entity.setName(obj.getString("name"));
                entity.setLogo(obj.getString("logo_url"));
                data.add(entity);
            }
            mainBean.setMainShopData(data);
        }
        return mainBean;
    }

    /**
     * 首页底部数据
     * @param jsonObject
     * @return
     */
    public static List<MainBean.Recommand> getMainBottom(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<MainBean.Recommand> data = new ArrayList<>();
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                MainBean.Recommand entity = new MainBean.Recommand();
                JSONObject object = jsonArray.getJSONObject(i);
                entity.setName(object.getString("name"));
                entity.setAutoId(object.getInt("auto_id"));
                entity.setGoodsCount(object.getInt("goods_count"));
                entity.setImageUrl(object.getString("image_url"));
                entity.setIsCash(object.getString("is_cash"));
                entity.setMarketPrice(object.getInt("price_market"));
                entity.setPrice(object.getString("price_fm"));
                data.add(entity);
            }
        }
        return data;
    }
    public static ArrayList<JsonBean> getProvince(String result){
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(result);
            if (array != null && array.length()>0) {
                for (int i = 0; i < array.length(); i++) {
                    JsonBean jsonBean = new JsonBean();
                    JSONObject jsonObject = array.getJSONObject(i);
                    jsonBean.setName(jsonObject.getString("name"));
                    JSONArray jsonArray = jsonObject.getJSONArray("city");
                    List<JsonBean.CityBean> cityData = new ArrayList<>();
                    if (jsonArray != null && jsonArray.length()>0){
                        for (int j =0;j< jsonArray.length();j++){
                            JsonBean.CityBean cityBean = new JsonBean.CityBean();
                            JSONObject object = jsonArray.getJSONObject(j);
                            cityBean.setName(object.getString("name"));
                            JSONArray array1 = object.getJSONArray("area");
                            List<String> area = new ArrayList<>();
                            for (int k = 0; k<array1.length();k++){
                                area.add(array1.getString(k));
                            }
                            cityBean.setArea(area);
                            cityData.add(cityBean);
                        }
                        jsonBean.setCityList(cityData);
                    }
                    detail.add(jsonBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 获取全部分类
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static List<ClassifyBean> getClassify(JSONObject jsonObject) throws JSONException {
        getUserInfo(jsonObject);
        List<ClassifyBean> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("category");
        if (jsonArray != null && jsonArray.length()>0){
            ClassifyBean classifyBean = new ClassifyBean();
            classifyBean.setName("全部分类");
            classifyBean.setAutoId(0);
            data.add(classifyBean);
            for (int i= 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                ClassifyBean bean = new ClassifyBean();
                bean.setAutoId(object.getInt("auto_id"));
                bean.setImageUrl(object.getString("image_url"));
                bean.setName(object.getString("name"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 获取二级分类
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static List<ClassifyRightSecondBean> getClassifySecond(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("goods");
        List<ClassifyRightSecondBean> data = new ArrayList<>();
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                ClassifyRightSecondBean bean = new ClassifyRightSecondBean();
                bean.setImageUrl(object.getString("image_url"));
                bean.setAutoId(object.getInt("auto_id"));
                bean.setGoodStatus(object.getString("status_text"));
                bean.setMarketPrice(object.getInt("price_market"));
                bean.setName(object.getString("name"));
                bean.setPrice(object.getString("price_fm"));
                data.add(bean);
            }
        }
        return data;
    }
    public static void getUserInfo(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("_user");
        if (object.has("telephone")){
            Constants.userPhone = object.getString("telephone");
            Constants.userId = object.getString("user_id");
        }
    }

    /**
     * 获取收货地址
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static List<AddressBean> getAddress(JSONObject jsonObject) throws JSONException {
        List<AddressBean> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("address");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                AddressBean bean = new AddressBean();
                bean.setName(object.getString("name"));
                bean.setArea(object.getString("area"));
                bean.setAutoId(object.getString("auto_id"));
                bean.setDetail(object.getString("detail"));
                bean.setIsDefault(object.getString("is_def"));
                bean.setTelephone(object.getString("telephone"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 获取商品列表分类
     * @param jsonObject
     * @param isOrder true 只获取商品分类和排序 false 获取商品列表 不在获取商品分类
     * @return
     * @throws JSONException
     */
    public static GoodsListBean getGoodsList(JSONObject jsonObject,boolean isOrder) throws JSONException {
        GoodsListBean bean = new GoodsListBean();
        getUserInfo(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("goods");
        if (jsonArray != null && jsonArray.length() > 0) {
            List<GoodsListBean.GoodsList> data = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                GoodsListBean.GoodsList entity = new GoodsListBean.GoodsList();
                entity.setAutoId(object.getInt("auto_id"));
                entity.setCurrentPrice(object.getString("price_fm"));
                entity.setGoodStatus(object.getString("status"));
                entity.setImageUrl(object.getString("image_url"));
                entity.setMarketPrice(object.getInt("price_market"));
                entity.setName(object.getString("name"));
                data.add(entity);
            }
            bean.setGoodsLists(data);
        }
        JSONArray jsonArray1 = jsonObject.getJSONArray("category");
        if (jsonArray1 != null && jsonArray1.length()>0){
            List<GoodsListBean.Category> data = new ArrayList<>();
            for (int i =0;i<jsonArray1.length();i++){
                JSONObject object = jsonArray1.getJSONObject(i);
                GoodsListBean.Category entity = new GoodsListBean.Category();
                entity.setName(object.getString("name"));
                entity.setAutoId(object.getString("auto_id"));
                data.add(entity);
            }
            bean.setCategories(data);
        }
        JSONArray array = jsonObject.getJSONArray("orderList");
        if (array != null && array.length()>0){
            List<GoodsListBean.OrderList> data = new ArrayList<>();
            for (int j=0;j<array.length();j++){
                GoodsListBean.OrderList entity = new GoodsListBean.OrderList();
                JSONObject obj = array.getJSONObject(j);
                entity.setName(obj.getString("name"));
                entity.setOrderId(obj.getString("id"));
                data.add(entity);
            }
            bean.setOrderLists(data);
        }
        return bean;
    }

    /**
     * 商品详情
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static GoodDetailBean getGoodDetail(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.getJSONObject("goods");
        GoodDetailBean bean = new GoodDetailBean();
        bean.setShopNotice(jsonObject.getString("notice"));
        bean.setGoodName(object.getString("name"));
        bean.setMarketPrice(object.getString("price_market_fm"));
        bean.setCurrentPrice(object.getString("price_fm"));
        bean.setNotice(object.getString("notice"));
        bean.setGoodsDes(object.getString("content"));
        bean.setMainImage(object.getString("image_url"));
        bean.setIsCash(object.getString("is_cash"));
        bean.setIsPromote(object.getString("is_promote"));
        bean.setIsQuYang(object.getString("is_quyang"));
        JSONArray jsonArray = object.getJSONArray("image_sub");/**banner*/
        if (jsonArray != null && jsonArray.length()>0){
            List<String> bannerData = new ArrayList<>();
            for (int i =0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                bannerData.add(obj.getString("url"));
            }
            bean.setImagePicture(bannerData);
        }
        /**详情图片*/
        JSONArray array = object.getJSONArray("image_desc");
        if (array != null && array.length()>0){
            List<String> detailPic = new ArrayList<>();
            for (int i =0;i<array.length();i++){
                JSONObject object1 = array.getJSONObject(i);
                detailPic.add(object1.getString("url"));
            }
            bean.setDetailPic(detailPic);
        }
        JSONObject shopObj = jsonObject.getJSONObject("shop");
        bean.setShopName(shopObj.getString("name"));
        bean.setShopUrl(shopObj.getString("logo_url"));
        bean.setGoodCount(shopObj.getInt("goods_count"));
        bean.setShopId(shopObj.getInt("user_id"));
        bean.setContractPhone(shopObj.getString("contact_telephone"));
        JSONArray recommodArray = jsonObject.getJSONArray("recommend");
        if (recommodArray != null && recommodArray.length()>0){
            List<GoodDetailBean.Recommond> data = new ArrayList<>();
            for (int i=0;i<recommodArray.length();i++){
                JSONObject ob = recommodArray.getJSONObject(i);
                GoodDetailBean.Recommond entity = new GoodDetailBean.Recommond();
                entity.setImageUrl(ob.getString("image_url"));
                entity.setCurrentPrice(ob.getString("price_fm"));
                entity.setMarketPrice(ob.getString("price_market_fm"));
                entity.setName(ob.getString("name"));
                entity.setAutoId(ob.getInt("auto_id"));
                data.add(entity);
            }
            bean.setRecommondData(data);
        }
        bean.setIsCollect(jsonObject.getBoolean("isCollect"));
        /**sku数据*/
        JSONArray specItem = object.getJSONArray("spec_item");
        if (specItem != null && specItem.length()>0){
            List<GoodDetailBean.SpecItem> specData = new ArrayList<>();
            for (int i = 0;i<specItem.length();i++){
                JSONObject specObject = specItem.getJSONObject(i);
                GoodDetailBean.SpecItem specItemEntity = new GoodDetailBean.SpecItem();
                specItemEntity.setName(specObject.getString("name"));
                JSONArray values = specObject.getJSONArray("values");
                if (values != null && values.length()>0){
                    List<GoodDetailBean.SpecValue> specValues = new ArrayList<>();
                    for (int j =0;j<values.length();j++){
                        JSONObject valuesObject = values.getJSONObject(j);
                        GoodDetailBean.SpecValue specValue = new GoodDetailBean.SpecValue();
                        specValue.setName(valuesObject.getString("name"));
                        specValue.setId(valuesObject.getString("auto_id"));
                        specValues.add(specValue);
                    }
                    specItemEntity.setSpecGuiGe(specValues);
                }
                specData.add(specItemEntity);
            }
            bean.setSpecItems(specData);
        }
        JSONArray specSku = object.getJSONArray("spec_sku");
        if (specSku != null && specSku.length()>0){
            List<GoodDetailBean.SpecSku> specSkuData = new ArrayList<>();
            for (int i =0;i<specSku.length();i++){
                JSONObject specSkuObject = specSku.getJSONObject(i);
                GoodDetailBean.SpecSku skuEntity = new GoodDetailBean.SpecSku();
                skuEntity.setAutoId(specSkuObject.getString("auto_id"));
                skuEntity.setGoodCount(specSkuObject.getInt("goods_count"));
                skuEntity.setPrice(specSkuObject.getString("price_fm"));
                skuEntity.setGoodId(specSkuObject.getString("goods_id"));
                skuEntity.setSpecKey(specSkuObject.getString("properties"));
                specSkuData.add(skuEntity);
            }
            bean.setSpecSkus(specSkuData);
        }
        return bean;
    }

    /**
     * 我的店铺收藏
     * @param jsonObject
     * @return
     */
    public static List<MyShopCollectBean> shopCollect(JSONObject jsonObject) throws JSONException {
        List<MyShopCollectBean> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                MyShopCollectBean bean = new MyShopCollectBean();
                bean.setShopImage(object.getString("shop_logo_url"));
                bean.setShopDescription(object.getString("shop_content"));
                bean.setShopName(object.getString("shop_name"));
                bean.setShopId(object.getString("res_id"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 商品收藏
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static List<MyGoodsCollectBean> goodCollect(JSONObject jsonObject) throws JSONException {
        List<MyGoodsCollectBean> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                MyGoodsCollectBean bean = new MyGoodsCollectBean();
                bean.setAutoId(object.getInt("auto_id"));
                bean.setGoodImage(object.getString("image_url"));
                bean.setGoodName(object.getString("name"));
                bean.setPrice(object.getString("price_fm"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 优惠券列表
     * @param jsonObject
     * @return
     */
    public static List<MyTicketBean> getTicket(JSONObject jsonObject) throws JSONException {
        List<MyTicketBean> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0 ;i<jsonArray.length();i++){
                MyTicketBean bean = new MyTicketBean();
                JSONObject object = jsonArray.getJSONObject(i);
                bean.setAmount(object.getString("amount_fm"));
                bean.setCondition(object.getString("meet_fm"));
                bean.setEndTime(object.getString("due_time"));
                bean.setStartTime(object.getString("add_time"));
                bean.setTicketStatus(object.getString("status"));
                bean.setTitle(object.getString("road_text"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 动态列表
     * @param jsonObject
     * @return
     */
    public static List<DynaMicBean> getDyanmic(JSONObject jsonObject) throws JSONException {
        List<DynaMicBean> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                DynaMicBean bean = new DynaMicBean();
                bean.setId(object.getInt("auto_id"));
                bean.setContent(object.getString("summary"));
                bean.setTitle(object.getString("title"));
                bean.setTime(object.getString("add_time_text"));
                bean.setImageIcon(object.getString("image_url"));
                data.add(bean);
            }
        }
        return data;
    }
    public static List<MainBean.Recommand> getShops(JSONObject jsonObject) throws JSONException {
        List<MainBean.Recommand> data = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        if (jsonArray != null && jsonArray.length()>0){
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                MainBean.Recommand entity = new MainBean.Recommand();
                entity.setAutoId(object.getInt("auto_id"));
                entity.setImageUrl(object.getString("image_url"));
                entity.setName(object.getString("name"));
                entity.setMarketPrice(object.getInt("price_market"));
                entity.setPrice(object.getString("price_fm"));
                data.add(entity);
            }
        }
        return data;
    }
    public static WeiChatBean accessToken(JSONObject jsonObject) throws JSONException {
        WeiChatBean bean = new WeiChatBean();
        bean.setAccessToken(jsonObject.getString("access_token"));
        bean.setExpiresIn(jsonObject.getString("expires_in"));
        bean.setRefreshToken(jsonObject.getString("refresh_token"));
        bean.setOpenid(jsonObject.getString("openid"));
        bean.setScope(jsonObject.getString("scope"));
        if (jsonObject.has("unionid"))bean.setUnionid(jsonObject.getString("unionid"));
        return bean;
    }

    /**
     * 购物车列表数据
     * @param jsonObject
     * @return
     */
    public static ShopBean shopCarList(JSONObject jsonObject) throws JSONException {
        ShopBean shopBean = new ShopBean();
        int count=0;
        JSONArray jsonArray = jsonObject.getJSONArray("valid");
        if (jsonArray != null && jsonArray.length()>0){
            List<ShopBean.Shop> data = new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                ShopBean.Shop shopBeans = new ShopBean.Shop();
                JSONObject shop = object.getJSONObject("shop");
                shopBeans.setShopName(shop.getString("name"));
                String shopId = shop.getString("user_id");
                shopBeans.setShopId(shopId);
                JSONArray array = object.getJSONArray("goods");
                if (array != null && array.length()>0){
                    List<ShopBean.ShopLists> shopLists = new ArrayList<>();
                    for (int j = 0;j<array.length();j++){
                        ShopBean.ShopLists shopList = new ShopBean.ShopLists();
                        JSONObject list = array.getJSONObject(j);
                        shopList.setShopImage(list.getString("image_url"));
                        shopList.setShopTitle(list.getString("name"));
                        shopList.setShopGuiGe(list.getString("spec_string"));
                        shopList.setShopPrice(list.getDouble("buy_price"));
                        shopList.setShopAmount(list.getInt("buy_goods_num"));
                        shopList.setTotalAmount(list.getInt("total_goods_count"));
                        shopList.setShopGoodId(list.getString("auto_id"));
                        shopList.setGoodAutoId(list.getInt("goods_auto_id"));
                        shopList.setShopId(shopId);
                        count++;
                        shopLists.add(shopList);
                    }
                    shopBeans.setShopListses(shopLists);
                }
                data.add(shopBeans);
            }
            shopBean.setShopList(data);
            shopBean.setGoodsCount(count);
        }
        JSONArray array = jsonObject.getJSONArray("invalid");
        if (array != null && array.length()>0){
            List<ShopBean.OverTimeList> validData = new ArrayList<>();
            for (int i =0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                ShopBean.OverTimeList entity =  new ShopBean.OverTimeList();
                entity.setGoodId(object.getString("auto_id"));
                entity.setDimen(object.getString("spec_string"));
                entity.setImagePic(object.getString("image_url"));
                entity.setTitle(object.getString("name"));
                validData.add(entity);
            }
            shopBean.setOverTimeList(validData);
        }
        return shopBean;
    }
    /**
     * 购物车结算
     * @param jsonObject
     * @return
     */
    public static RightBuyBean shopCarAccount(JSONObject jsonObject) throws JSONException {
        RightBuyBean bean = new RightBuyBean();
        if (jsonObject.get("address") instanceof JSONObject){
            JSONObject object = jsonObject.getJSONObject("address");
            bean.setName(object.getString("name"));
            bean.setPhone(object.getString("telephone"));
            bean.setIsDefault(object.getString("is_def"));
            bean.setAddress(object.getString("area")+object.getString("detail"));
            bean.setAddressId(object.getString("auto_id"));
        }
        JSONObject totalObject = jsonObject.getJSONObject("total");
        bean.setTotalPrice(totalObject.getString("amount_fm"));
        bean.setTotalAmount(totalObject.getInt("goods_count"));
        bean.setServicePhone(jsonObject.getString("servicePhone"));
        bean.setYunFei(totalObject.getString("express_amount_fm"));
        JSONArray array = jsonObject.getJSONArray("list");
        List<RightBuyBean.RightBuyList> data = new ArrayList<>();
        for (int i =0;i<array.length();i++){
            JSONObject object = array.getJSONObject(i);
            String shopName = object.getJSONObject("shop").getString("name");
            String shopId = object.getJSONObject("shop").getString("user_id");
            JSONArray jsonArray = object.getJSONArray("goods");
            for (int j =0;j<jsonArray.length();j++){
                RightBuyBean.RightBuyList entity = new RightBuyBean.RightBuyList();
                JSONObject goodObject = jsonArray.getJSONObject(j);
                entity.setGoodImage(goodObject.getString("image_url"));
                entity.setGoodTitle(goodObject.getString("name"));
                entity.setGoodGuiGe(goodObject.getString("spec_string"));
                entity.setGoodPrice(goodObject.getString("buy_price_fm"));
                entity.setGoodAmount(goodObject.getInt("buy_goods_num"));
                entity.setShopId(shopId);
                entity.setShopName(shopName);
                data.add(entity);
            }
        }
        bean.setData(data);
        return bean;
    }

    /**
     * 个人中心
     * @param jsonObject
     * @return
     */
    public static MyCenterBean myCenter(JSONObject jsonObject) throws JSONException {
        MyCenterBean bean = new MyCenterBean();
        JSONObject user = jsonObject.getJSONObject("_user");
        if (user.has("user_nick")){
            bean.setNickName(user.getString("user_nick"));
            bean.setPictureUrl(user.getString("avatar"));
        }
        bean.setRefundCount(jsonObject.getInt("refundCount"));
        bean.setWaitDeliverCount(jsonObject.getInt("waitDeliverCount"));
        bean.setWaitTakeCount(jsonObject.getInt("waitTakeCount"));
        bean.setWaitPayCount(jsonObject.getInt("waitPayCount"));
        JSONObject helpObject = jsonObject.getJSONObject("help");
        bean.setHelpLink(helpObject.getString("link"));
        bean.setHelpTitle(helpObject.getString("title"));
        return bean;
    }

    /**
     * 订单管理
     * @param jsonObject
     * @return
     */
    public static MyOrderBean myOrder(JSONObject jsonObject) throws JSONException {
        MyOrderBean orderBean = new MyOrderBean();
        JSONArray jsonArray = jsonObject.getJSONArray("typeList");
        if (jsonArray != null && jsonArray.length()>0){
            List<MyOrderBean.OrderType> data = new ArrayList<>();
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                MyOrderBean.OrderType bean = new MyOrderBean.OrderType();
                bean.setTitle(object.getString("name"));
                bean.setType(object.getString("key"));
                data.add(bean);
            }
            orderBean.setTypeData(data);
        }
        JSONArray array = jsonObject.getJSONArray("list");
        if (array != null && array.length()>0){
            List<MyOrderBean.Orders> data = new ArrayList<>();
            for (int i = 0;i< array.length();i++){
                JSONObject object = array.getJSONObject(i);
                MyOrderBean.Orders bean = new MyOrderBean.Orders();
                bean.setExpressAmount(object.getString("amount_express_fm"));
                bean.setOrderNumber(object.getString("trade_num"));
                bean.setOrderStatus(object.getString("status"));
                bean.setStatusText(object.getString("status_text"));
                bean.setTotalPrice(object.getString("amount_fm"));
                String orderId = object.getString("auto_id");
                bean.setOrderId(orderId);
                JSONArray listArray = object.getJSONArray("goods");
                List<MyOrderBean.OrderList> orderLists = new ArrayList<>();
                for (int j =0;j<listArray.length();j++){
                    MyOrderBean.OrderList orderListBean = new MyOrderBean.OrderList();
                    JSONObject obj = listArray.getJSONObject(j);
                    orderListBean.setOrderAmount(obj.getString("goods_count"));
                    orderListBean.setOrderGuiGe(obj.getString("spec_string"));
                    orderListBean.setOrderImage(obj.getString("image_url"));
                    orderListBean.setOrderPrice(obj.getString("amount_fm"));
                    orderListBean.setOrderTitle(obj.getString("name"));
                    orderListBean.setOrderId(orderId);
                    orderLists.add(orderListBean);
                }
                bean.setOrderLists(orderLists);
                data.add(bean);
            }
            orderBean.setOrderses(data);
        }
        return orderBean;
    }

    /**
     * 订单详情
     * @param jsonObject
     * @return
     */
    public static OrderDetailBean orderDetail(JSONObject jsonObject) throws JSONException {
        OrderDetailBean bean = new OrderDetailBean();
        JSONObject object = jsonObject.getJSONObject("order");
        bean.setOrderStatusText(object.getString("status_text"));
        bean.setOrderStatus(object.getString("status"));
        bean.setDeliveryWay(object.getString("express_type"));
        bean.setDeliveryNumber(object.getString("express_num"));
        JSONObject addressObject = object.getJSONObject("address");
        bean.setName(addressObject.getString("name"));
        bean.setPhone(addressObject.getString("telephone"));
        bean.setAddress(addressObject.getString("area")+addressObject.getString("detail"));
        bean.setShopNames(object.getString("shop_name"));
        bean.setIsComment(object.getString("is_comment"));/**订单是否评价过了*/
        bean.setOrderId(object.getString("auto_id"));
        bean.setOrderNumber(object.getString("trade_num"));
        bean.setRefund(object.getBoolean("is_refund"));
        JSONArray jsonArray = object.getJSONArray("goods");
        if (jsonArray != null && jsonArray.length()>0){
            List<MyOrderBean.OrderList> data = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                MyOrderBean.OrderList entity = new MyOrderBean.OrderList();
                entity.setOrderTitle(obj.getString("name"));
                entity.setOrderPrice(obj.getString("amount_fm"));
                entity.setOrderImage(obj.getString("image_url"));
                entity.setOrderGuiGe(obj.getString("spec_string"));
                entity.setOrderAmount(obj.getString("goods_count"));
                entity.setGoodId(obj.getString("auto_id"));
                data.add(entity);
            }
            bean.setOrderDetailList(data);
        }
        bean.setPostAges(object.getString("amount_express_fm"));
        bean.setTotalPrice(object.getString("amount_fm"));
        bean.setOrderNumber(object.getString("trade_num"));
        if (object.has("add_time")) bean.setAddTime(object.getLong("add_time"));
        if (object.has("pay_time"))bean.setPayTime(object.getLong("pay_time"));
        if (object.has("deliver_time")) bean.setDeliverGoodsTime(object.getLong("deliver_time"));
        if (object.has("finish_time"))bean.setTakeGoodsTime(object.getLong("finish_time"));
        bean.setTotalOrderAmount(object.getString("goods_count"));
        bean.setTotalOrderPrice(object.getString("amount_fm"));
        return bean;
    }
    /**
     * 评论内容
     * @param jsonObject
     * @return
     */
    public static List<CommentBean> getComment(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("comment");
        List<CommentBean> data = new ArrayList<>();
        if (jsonArray != null && jsonArray.length()>0){
            for (int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                CommentBean bean = new CommentBean();
                bean.setContent(object.getString("content"));
                bean.setPersonImage(object.getString("avatar"));
                bean.setTime(object.getString("add_time_text"));
                bean.setUserNick(object.getString("user_nick"));
                data.add(bean);
            }
        }
        return data;
    }

    /**
     * 获取预支付订单信息
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static PaySureBean getPayParams(JSONObject jsonObject) throws JSONException {
        PaySureBean bean = new PaySureBean();
        JSONObject object = jsonObject.getJSONObject("pay");
        bean.setAppid(object.getString("appid"));
        bean.setNoncestr(object.getString("noncestr"));
        bean.setPackageType(object.getString("package"));
        bean.setPartnerid(object.getString("partnerid"));
        bean.setPrepayid(object.getString("prepayid"));
        bean.setSign(object.getString("sign"));
        bean.setTimestamp(object.getString("timestamp"));
        bean.setTrade_num(jsonObject.getString("trade_num"));
        return bean;
    }
}
