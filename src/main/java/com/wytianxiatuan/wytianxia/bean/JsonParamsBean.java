package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class JsonParamsBean{
    private String goods_id;
    private String sku_id;
    private int count;
    private List<String> carts;
    private String invoice_type;
    private String note;
    private String invoice_name;
    private String invoice_phone;
    private String invoice_comp_name;
    private String trade_num;
    private String addr_id;

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getTrade_num() {
        return trade_num;
    }

    public void setTrade_num(String trade_num) {
        this.trade_num = trade_num;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCarts(List<String> carts) {
        this.carts = carts;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public void setInvoice_phone(String invoice_phone) {
        this.invoice_phone = invoice_phone;
    }

    public void setInvoice_comp_name(String invoice_comp_name) {
        this.invoice_comp_name = invoice_comp_name;
    }

    public void setInvoice_comp_code(String invoice_comp_code) {
        this.invoice_comp_code = invoice_comp_code;
    }

    private String invoice_comp_code;

}
