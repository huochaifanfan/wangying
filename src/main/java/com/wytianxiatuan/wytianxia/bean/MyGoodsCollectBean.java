package com.wytianxiatuan.wytianxia.bean;

/**
 * Created by liuju on 2018/1/18.
 * 商品收藏
 */

public class MyGoodsCollectBean {
    private String goodImage;
    private String goodName;
    private String price;
    private int autoId;

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
