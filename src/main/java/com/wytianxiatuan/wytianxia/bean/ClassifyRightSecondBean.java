package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class ClassifyRightSecondBean {
    private int autoId;
    private String name;
    private int marketPrice;
    private String price;
    private String goodStatus;
    private String imageUrl;
    private List<BeforeGood> beforeGoods;
    private List<AfterGood> afterGoods;

    public List<BeforeGood> getBeforeGoods() {
        return beforeGoods;
    }

    public void setBeforeGoods(List<BeforeGood> beforeGoods) {
        this.beforeGoods = beforeGoods;
    }

    public List<AfterGood> getAfterGoods() {
        return afterGoods;
    }

    public void setAfterGoods(List<AfterGood> afterGoods) {
        this.afterGoods = afterGoods;
    }

    public static class AfterGood{
        private int autoId;
        private String name;
        private int marketPrice;
        private String price;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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
    public static class BeforeGood{
        private int autoId;
        private String name;
        private int marketPrice;
        private String price;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
