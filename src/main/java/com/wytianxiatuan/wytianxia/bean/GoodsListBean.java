package com.wytianxiatuan.wytianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuju on 2018/1/28.
 */

public class GoodsListBean implements Serializable{
    private List<GoodsList> goodsLists;
    private List<Category> categories;
    private List<OrderList> orderLists;

    public List<GoodsList> getGoodsLists() {
        return goodsLists;
    }

    public void setGoodsLists(List<GoodsList> goodsLists) {
        this.goodsLists = goodsLists;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<OrderList> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(List<OrderList> orderLists) {
        this.orderLists = orderLists;
    }

    public static class GoodsList implements Serializable{
        private int autoId;
        private String name;
        private int marketPrice;
        private String currentPrice;
        private String goodStatus;
        private String imageUrl;

        public int getAutoId() {
            return autoId;
        }

        public void setAutoId(int autoId) {
            this.autoId = autoId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getGoodStatus() {
            return goodStatus;
        }

        public void setGoodStatus(String goodStatus) {
            this.goodStatus = goodStatus;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    /**
     * 所有的分类
     */
    public static class Category implements Serializable{
        private String autoId;
        private String name;

        public String getAutoId() {
            return autoId;
        }

        public void setAutoId(String autoId) {
            this.autoId = autoId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class OrderList implements Serializable{
        private String name;
        private String orderId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
