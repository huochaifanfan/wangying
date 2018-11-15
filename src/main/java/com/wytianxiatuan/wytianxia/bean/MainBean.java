package com.wytianxiatuan.wytianxia.bean;

import java.util.List;

/**
 * Created by liuju on 2018/1/20.
 * 首页
 */

public class MainBean {
    private List<Banner> bannerData;
    private List<CateGory> cateGoryData;
    private List<MainShops> mainShopData;
    private String middleTitle;
    private int middleAutoId;
    private String middleImageUrl;
    private String middleLink;
    private String middleLinkType;
    private String middleRedirectType;
    private String middleRedirectId;
    private String mainDynamicTitle;
    private String mainDynamicId;
    private String mainDynamicLink;
    private List<Recommand> recommandData;
    private String requireMentLink;
    private String developTitle;
    private String developLink;
    private String isCash;
    private String isPromote;
    private String isQuyang;
    private String peijianId;
    private String jiagongId;

    public String getMiddleLinkType() {
        return middleLinkType;
    }

    public void setMiddleLinkType(String middleLinkType) {
        this.middleLinkType = middleLinkType;
    }

    public String getMiddleRedirectType() {
        return middleRedirectType;
    }

    public void setMiddleRedirectType(String middleRedirectType) {
        this.middleRedirectType = middleRedirectType;
    }

    public String getMiddleRedirectId() {
        return middleRedirectId;
    }

    public void setMiddleRedirectId(String middleRedirectId) {
        this.middleRedirectId = middleRedirectId;
    }

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

    public String getIsQuyang() {
        return isQuyang;
    }

    public void setIsQuyang(String isQuyang) {
        this.isQuyang = isQuyang;
    }

    public String getPeijianId() {
        return peijianId;
    }

    public void setPeijianId(String peijianId) {
        this.peijianId = peijianId;
    }

    public String getJiagongId() {
        return jiagongId;
    }

    public void setJiagongId(String jiagongId) {
        this.jiagongId = jiagongId;
    }

    public String getRequireMentLink() {
        return requireMentLink;
    }

    public void setRequireMentLink(String requireMentLink) {
        this.requireMentLink = requireMentLink;
    }

    public String getDevelopTitle() {
        return developTitle;
    }

    public void setDevelopTitle(String developTitle) {
        this.developTitle = developTitle;
    }

    public String getDevelopLink() {
        return developLink;
    }

    public void setDevelopLink(String developLink) {
        this.developLink = developLink;
    }

    public List<Recommand> getRecommandData() {
        return recommandData;
    }

    public void setRecommandData(List<Recommand> recommandData) {
        this.recommandData = recommandData;
    }

    public List<Banner> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<Banner> bannerData) {
        this.bannerData = bannerData;
    }

    public List<CateGory> getCateGoryData() {
        return cateGoryData;
    }

    public void setCateGoryData(List<CateGory> cateGoryData) {
        this.cateGoryData = cateGoryData;
    }

    public List<MainShops> getMainShopData() {
        return mainShopData;
    }

    public void setMainShopData(List<MainShops> mainShopData) {
        this.mainShopData = mainShopData;
    }

    public String getMiddleTitle() {
        return middleTitle;
    }

    public void setMiddleTitle(String middleTitle) {
        this.middleTitle = middleTitle;
    }

    public int getMiddleAutoId() {
        return middleAutoId;
    }

    public void setMiddleAutoId(int middleAutoId) {
        this.middleAutoId = middleAutoId;
    }

    public String getMiddleImageUrl() {
        return middleImageUrl;
    }

    public void setMiddleImageUrl(String middleImageUrl) {
        this.middleImageUrl = middleImageUrl;
    }

    public String getMiddleLink() {
        return middleLink;
    }

    public void setMiddleLink(String middleLink) {
        this.middleLink = middleLink;
    }

    public String getMainDynamicTitle() {
        return mainDynamicTitle;
    }

    public void setMainDynamicTitle(String mainDynamicTitle) {
        this.mainDynamicTitle = mainDynamicTitle;
    }

    public String getMainDynamicId() {
        return mainDynamicId;
    }

    public void setMainDynamicId(String mainDynamicId) {
        this.mainDynamicId = mainDynamicId;
    }

    public String getMainDynamicLink() {
        return mainDynamicLink;
    }

    public void setMainDynamicLink(String mainDynamicLink) {
        this.mainDynamicLink = mainDynamicLink;
    }

    /**首页banner图*/
    public static class Banner{
        private String title;
        private String image;
        private String imageLink;
        private String linkType;
        private String redirectType;
        private String redirectId;

        public String getLinkType() {
            return linkType;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public String getRedirectType() {
            return redirectType;
        }

        public void setRedirectType(String redirectType) {
            this.redirectType = redirectType;
        }

        public String getRedirectId() {
            return redirectId;
        }

        public void setRedirectId(String redirectId) {
            this.redirectId = redirectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }
    }

    /**
     * 首页分类
     */
    public static class CateGory{
        private int autoId;
        private String imageUrl;
        private String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 首页星际推荐
     */
    public static class MainShops{
        private int userId;
        private String name;
        private String logo;
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
    public static class Recommand{
        private int autoId;
        private String name;
        private int goodsCount;
        private int marketPrice;
        private String price;
        private String isCash;
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

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
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

        public String getIsCash() {
            return isCash;
        }

        public void setIsCash(String isCash) {
            this.isCash = isCash;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
