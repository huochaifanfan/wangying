package com.wytianxiatuan.wytianxia.bean;

/**
 * Created by liuju on 2018/1/16.
 * 个人中心
 */

public class MyCenterBean {
    private String pictureUrl;
    private String nickName;
    private int waitPayCount;
    private int waitDeliverCount;
    private int waitTakeCount;
    private int refundCount;
    private String helpLink;
    private String helpTitle;

    public String getHelpLink() {
        return helpLink;
    }

    public void setHelpLink(String helpLink) {
        this.helpLink = helpLink;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getWaitPayCount() {
        return waitPayCount;
    }

    public void setWaitPayCount(int waitPayCount) {
        this.waitPayCount = waitPayCount;
    }

    public int getWaitDeliverCount() {
        return waitDeliverCount;
    }

    public void setWaitDeliverCount(int waitDeliverCount) {
        this.waitDeliverCount = waitDeliverCount;
    }

    public int getWaitTakeCount() {
        return waitTakeCount;
    }

    public void setWaitTakeCount(int waitTakeCount) {
        this.waitTakeCount = waitTakeCount;
    }

    public int getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(int refundCount) {
        this.refundCount = refundCount;
    }
}
