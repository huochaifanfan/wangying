package com.wytianxiatuan.wytianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 我的订单
 */

public class MyOrderBean implements Serializable{
    private List<OrderType> typeData;
    private List<Orders> orderses;

    public List<Orders> getOrderses() {
        return orderses;
    }

    public void setOrderses(List<Orders> orderses) {
        this.orderses = orderses;
    }

    public static class Orders implements Serializable{
        private String orderNumber;
        private String statusText;
        private String orderStatus;
        private String expressAmount;
        private String totalPrice;
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        private List<OrderList> orderLists;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getExpressAmount() {
            return expressAmount;
        }

        public void setExpressAmount(String expressAmount) {
            this.expressAmount = expressAmount;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public List<OrderList> getOrderLists() {
            return orderLists;
        }

        public void setOrderLists(List<OrderList> orderLists) {
            this.orderLists = orderLists;
        }
    }
    public List<OrderType> getTypeData() {
        return typeData;
    }
    public void setTypeData(List<OrderType> typeData) {
        this.typeData = typeData;
    }

    /**订单列表*/
    public static class OrderList implements Serializable{
        private String orderImage;
        private String orderTitle;
        private String orderGuiGe;
        private String orderPrice;
        private String orderAmount;
        private String orderId;
        private String goodId;
        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderImage() {
            return orderImage;
        }

        public void setOrderImage(String orderImage) {
            this.orderImage = orderImage;
        }

        public String getOrderTitle() {
            return orderTitle;
        }

        public void setOrderTitle(String orderTitle) {
            this.orderTitle = orderTitle;
        }

        public String getOrderGuiGe() {
            return orderGuiGe;
        }

        public void setOrderGuiGe(String orderGuiGe) {
            this.orderGuiGe = orderGuiGe;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }
    }
    /**获取顶部标题*/
    public static class OrderType implements Serializable{
        private String type;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
