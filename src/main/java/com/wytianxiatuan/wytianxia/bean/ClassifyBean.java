package com.wytianxiatuan.wytianxia.bean;

/**
 * Created by liuju on 2018/1/24.
 * 分类
 */

public class ClassifyBean {
    private int autoId;
    private String name;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        /**
         * 排序列表
         */
        this.imageUrl = imageUrl;
    }
}
