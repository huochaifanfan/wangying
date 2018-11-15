package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by liuju on 2018/1/19.
 * 立即购买 提交订单
 */

public class RightBuyBean {
    private String name;
    private String phone;
    private String isDefault;
    private String address;
    private String faPiao;
    private String yunFei;
    private String servicePhone;
    private String totalPrice;
    private int totalAmount;
    private List<RightBuyList> data;
    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getYunFei() {
        return yunFei;
    }

    public void setYunFei(String yunFei) {
        this.yunFei = yunFei;
    }
    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaPiao() {
        return faPiao;
    }

    public void setFaPiao(String faPiao) {
        this.faPiao = faPiao;
    }

    public List<RightBuyList> getData() {
        return data;
    }

    public void setData(List<RightBuyList> data) {
        this.data = data;
    }

    public static class RightBuyList{
        private String shopName;
        private String shopId;
        private String goodImage;
        private String goodTitle;
        private int goodId;
        private String goodGuiGe;
        private String goodPrice;
        private int goodAmount;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getGoodImage() {
            return goodImage;
        }

        public void setGoodImage(String goodImage) {
            this.goodImage = goodImage;
        }

        public String getGoodTitle() {
            return goodTitle;
        }

        public void setGoodTitle(String goodTitle) {
            this.goodTitle = goodTitle;
        }

        public int getGoodId() {
            return goodId;
        }

        public void setGoodId(int goodId) {
            this.goodId = goodId;
        }

        public String getGoodGuiGe() {
            return goodGuiGe;
        }

        public void setGoodGuiGe(String goodGuiGe) {
            this.goodGuiGe = goodGuiGe;
        }

        public String getGoodPrice() {
            return goodPrice;
        }

        public void setGoodPrice(String goodPrice) {
            this.goodPrice = goodPrice;
        }

        public int getGoodAmount() {
            return goodAmount;
        }

        public void setGoodAmount(int goodAmount) {
            this.goodAmount = goodAmount;
        }
    }
}
