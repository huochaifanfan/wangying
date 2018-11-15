package com.wytianxiatuan.wytianxia.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class JsonBean implements IPickerViewData {
    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private List<CityBean> city;

    @Override
    public String toString() {
        return "JsonBean{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }
    @Override
    public String getPickerViewText() {
        return this.name;
    }



    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String name;
        private List<String> area;

        @Override
        public String toString() {
            return "CityBean{" +
                    "name='" + name + '\'' +
                    ", area=" + area +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
