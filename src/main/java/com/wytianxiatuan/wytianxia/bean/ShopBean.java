package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by liuju on 2018/1/19.
 * 购物车
 */

public class ShopBean {
    private String totalPrice;
    private List<Shop> shopList;
    private List<OverTimeList> overTimeList;
    private int goodsCount;

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public List<OverTimeList> getOverTimeList() {
        return overTimeList;
    }

    public void setOverTimeList(List<OverTimeList> overTimeList) {
        this.overTimeList = overTimeList;
    }
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
    public static class ShopLists{
        private String shopImage;
        private String shopTitle;
        private String shopGuiGe;
        private double shopPrice;
        private int shopAmount;
        private int totalAmount;
        private String shopGoodId;
        private String shopId;
        private boolean isSelect;
        private int goodAutoId;

        public int getGoodAutoId() {
            return goodAutoId;
        }

        public void setGoodAutoId(int goodAutoId) {
            this.goodAutoId = goodAutoId;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopGoodId() {
            return shopGoodId;
        }

        public void setShopGoodId(String shopGoodId) {
            this.shopGoodId = shopGoodId;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getShopImage() {
            return shopImage;
        }

        public void setShopImage(String shopImage) {
            this.shopImage = shopImage;
        }

        public String getShopTitle() {
            return shopTitle;
        }

        public void setShopTitle(String shopTitle) {
            this.shopTitle = shopTitle;
        }

        public String getShopGuiGe() {
            return shopGuiGe;
        }

        public void setShopGuiGe(String shopGuiGe) {
            this.shopGuiGe = shopGuiGe;
        }

        public double getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(double shopPrice) {
            this.shopPrice = shopPrice;
        }

        public int getShopAmount() {
            return shopAmount;
        }

        public void setShopAmount(int shopAmount) {
            this.shopAmount = shopAmount;
        }
    }
    public static class Shop{
        private String shopName;
        private boolean isSelect;
        private String shopId;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private List<ShopLists> shopListses;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public List<ShopLists> getShopListses() {
            return shopListses;
        }

        public void setShopListses(List<ShopLists> shopListses) {
            this.shopListses = shopListses;
        }
    }
    public static class OverTimeList{
        private String imagePic;
        private String title;
        private String dimen;
        private String goodId;

        public String getImagePic() {
            return imagePic;
        }

        public void setImagePic(String imagePic) {
            this.imagePic = imagePic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDimen() {
            return dimen;
        }

        public void setDimen(String dimen) {
            this.dimen = dimen;
        }

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }
    }
}
