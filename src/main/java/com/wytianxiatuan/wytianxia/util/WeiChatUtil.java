package com.wytianxiatuan.wytianxia.util;

import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.WeiChatBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

public class WeiChatUtil {
    private static String httpsGet(String url) throws Exception {
        String result =null;
        URL uri = new URL(url);
        HttpURLConnection httpsURLConnection = (HttpURLConnection) uri.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setConnectTimeout(15*1000);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.connect();
        if (httpsURLConnection.getResponseCode() == 200){
            InputStream inputStream = httpsURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer))!= -1){
                byteArrayOutputStream.write(buffer,0,len);
                byteArrayOutputStream.flush();
            }
            result = byteArrayOutputStream.toString("utf-8");
        }
        return result;
    }
    private static WeiChatBean accessToken(String result){
        WeiChatBean bean = new WeiChatBean();
        try{
            JSONObject jsonObject = new JSONObject(result);
            bean.setAccessToken(jsonObject.getString("access_token"));
            bean.setExpiresIn(jsonObject.getString("expires_in"));
            bean.setRefreshToken(jsonObject.getString("refresh_token"));
            bean.setOpenid(jsonObject.getString("openid"));
            bean.setScope(jsonObject.getString("scope"));
            if (jsonObject.has("unionid"))bean.setUnionid(jsonObject.getString("unionid"));
        }catch (Exception e){}
        return bean;
    }

    /**
     * 检查access_token是否还有效
     * @param url
     * @return
     */
    private static boolean isValid(String url){
        boolean isvalid = false;
        try {
            String result = httpsGet(url);
            JSONObject jsonObject = new JSONObject(result);
            if ("0".equals(jsonObject.getString("errcode"))&& "ok".equals(jsonObject.getString("errmsg"))){
                isvalid = true;
            }else {
                isvalid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isvalid;
    }

    /**
     * 获取用户信息
     * @param url
     * @return
     * @throws Exception
     */
    private static WeiChatBean userInfo(String url) throws Exception {
        String result = httpsGet(url);
        WeiChatBean bean = new WeiChatBean();
        JSONObject jsonObject = new JSONObject(result);
        bean.setNickName(jsonObject.getString("nickname"));
        bean.setHeadImage(jsonObject.getString("headimgurl"));
        return bean;
    }

    /**
     * 微信登录
     * @param appId  微信appId
     * @param appSerect 微信秘钥
     * @param code 获取授权之后的code
     * @return
     */
    public static WeiChatBean getUserInfo(String code,String appId,String appSerect){
        try{
            /**第一步：首先获取access_token*/
            String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+
                    "&secret="+appSerect+"&code="+code+"&grant_type=authorization_code";
            String result = httpsGet(accessTokenUrl);
            WeiChatBean bean = accessToken(result);
            String access_token = bean!=null?bean.getAccessToken():"";
            String openId = bean != null?bean.getOpenid():"";
            String reflashToken = bean != null?bean.getRefreshToken():"";
            /**第二步：检查access_token是否有效*/
            String isValidUrl =  "https://api.weixin.qq.com/sns/auth?access_token="+access_token+"&openid="+openId;
            if (!isValid(isValidUrl)){
                /**如果无效 则刷新access_token*/
                String reflashUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+MainApplication.weiChatAppId+
                        "&grant_type=refresh_token&refresh_token="+reflashToken;
                WeiChatBean weiChatBean = accessToken(httpsGet(reflashUrl));
                String accessToken = weiChatBean!=null?weiChatBean.getAccessToken():"";
                String reflashOpenId = weiChatBean!=null?weiChatBean.getOpenid():"";
                /**刷新之后获取用户的基本信息*/
                String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+reflashOpenId;
                WeiChatBean userBean = userInfo(userInfoUrl);
                return userBean;
            }else {
                String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId;
                WeiChatBean userBean = userInfo(userInfoUrl);
                return userBean;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
