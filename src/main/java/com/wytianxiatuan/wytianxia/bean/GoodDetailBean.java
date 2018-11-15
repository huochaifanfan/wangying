package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26 0026.
 * 商品详情
 */

public class GoodDetailBean {
    private List<String> imagePicture;
    private int autoId;
    private int shopId;
    private String goodName;
    private String marketPrice;
    private String currentPrice;
    private String notice;
    private String mainImage;
    private String shopNotice;
    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    /**店铺相关数据*/
    private String shopUrl;
    private String shopName;
    private String contractPhone;
    private String contractName;
    private int goodCount;
    private String workAddress;
    private String goodsDes;
    private List<String> detailPic;
    private List<Recommond> recommondData;
    private boolean isCollect;/**商品是否收藏*/
    private List<SpecItem> specItems;
    private List<SpecSku> specSkus;
    private String  isCash;
    private String isPromote;
    private String isQuYang;

    public String getIsCash() {
        return isCash;
    }

    public void setIsCash(String isCash) {
        this.isCash = isCash;
    }

    public String getIsPromote() {
        return isPromote;
    }

    public void setIsPromote(String isPromote) {
        this.isPromote = isPromote;
    }

    public String getIsQuYang() {
        return isQuYang;
    }

    public void setIsQuYang(String isQuYang) {
        this.isQuYang = isQuYang;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }

    public List<SpecSku> getSpecSkus() {
        return specSkus;
    }

    public void setSpecSkus(List<SpecSku> specSkus) {
        this.specSkus = specSkus;
    }

    public static class SpecSku{
        private String autoId;
        private String goodId;
        private String price;
        private int goodCount;
        private String specKey;

        public String getAutoId() {
            return autoId;
        }

        public void setAutoId(String autoId) {
            this.autoId = autoId;
        }

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(int goodCount) {
            this.goodCount = goodCount;
        }

        public String getSpecKey() {
            return specKey;
        }

        public void setSpecKey(String specKey) {
            this.specKey = specKey;
        }
    }
    public List<SpecItem> getSpecItems() {
        return specItems;
    }

    public void setSpecItems(List<SpecItem> specItems) {
        this.specItems = specItems;
    }

    /**商品sku数据*/
    public static class SpecItem{
        private String name;
        private List<SpecValue> specGuiGe;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SpecValue> getSpecGuiGe() {
            return specGuiGe;
        }

        public void setSpecGuiGe(List<SpecValue> specGuiGe) {
            this.specGuiGe = specGuiGe;
        }
    }
    public static class SpecValue{
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    public boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public List<Recommond> getRecommondData() {
        return recommondData;
    }

    public void setRecommondData(List<Recommond> recommondData) {
        this.recommondData = recommondData;
    }

    public List<String> getDetailPic() {
        return detailPic;
    }

    public void setDetailPic(List<String> detailPic) {
        this.detailPic = detailPic;
    }

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    /**未完待续.......*/
    public List<String> getImagePicture() {
        return imagePicture;
    }

    public void setImagePicture(List<String> imagePicture) {
        this.imagePicture = imagePicture;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }
    public static class Recommond{
        private String imageUrl;
        private String marketPrice;
        private String currentPrice;
        private String name;
        private int autoId;

        public int getAutoId() {
            return autoId;
        }

        public void setAutoId(int autoId) {
            this.autoId = autoId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
