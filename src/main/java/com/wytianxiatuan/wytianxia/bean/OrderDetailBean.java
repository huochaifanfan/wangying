package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 订单详情
 */

public class OrderDetailBean {
    private String orderStatus;
    private String orderStatusText;
    private String deliveryWay;
    private String deliveryNumber;
    private String name;
    private String phone;
    private String address;
    private String shopNames;
    private List<MyOrderBean.OrderList> orderDetailList;
    private String ticketAmount;
    private String postAges;
    private String totalOrderPrice;
    private String orderNumber;
    private String orderId;
    private long payTime;
    private long deliverGoodsTime;
    private long takeGoodsTime;
    private String totalPrice;
    private String totalOrderAmount;
    private String isComment;
    private boolean isRefund;
    private long addTime;

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public boolean isRefund() {
        return isRefund;
    }

    public void setRefund(boolean refund) {
        isRefund = refund;
    }
    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**订单是否评价了*/

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopNames() {
        return shopNames;
    }

    public void setShopNames(String shopNames) {
        this.shopNames = shopNames;
    }

    public List<MyOrderBean.OrderList> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<MyOrderBean.OrderList> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(String ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getPostAges() {
        return postAges;
    }

    public void setPostAges(String postAges) {
        this.postAges = postAges;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public long getDeliverGoodsTime() {
        return deliverGoodsTime;
    }

    public void setDeliverGoodsTime(long deliverGoodsTime) {
        this.deliverGoodsTime = deliverGoodsTime;
    }

    public long getTakeGoodsTime() {
        return takeGoodsTime;
    }

    public void setTakeGoodsTime(long takeGoodsTime) {
        this.takeGoodsTime = takeGoodsTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(String totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }
}
