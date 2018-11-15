package com.wytianxiatuan.wytianxia.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/25 0025.
 * 收货地址管理
 */

public class AddressBean implements Serializable{
    private String autoId;
    private String name;
    private String telephone;
    private String area;
    private String detail;
    private String isDefault;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
