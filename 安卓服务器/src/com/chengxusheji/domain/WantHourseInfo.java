package com.chengxusheji.domain;

import java.sql.Timestamp;
public class WantHourseInfo {
    /*记录编号*/
    private int wantHourseId;
    public int getWantHourseId() {
        return wantHourseId;
    }
    public void setWantHourseId(int wantHourseId) {
        this.wantHourseId = wantHourseId;
    }

    /*求租用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*求租区域*/
    private AreaInfo position;
    public AreaInfo getPosition() {
        return position;
    }
    public void setPosition(AreaInfo position) {
        this.position = position;
    }

    /*房屋类型*/
    private HourseType hourseTypeObj;
    public HourseType getHourseTypeObj() {
        return hourseTypeObj;
    }
    public void setHourseTypeObj(HourseType hourseTypeObj) {
        this.hourseTypeObj = hourseTypeObj;
    }

    /*价格范围*/
    private PriceRange priceRangeObj;
    public PriceRange getPriceRangeObj() {
        return priceRangeObj;
    }
    public void setPriceRangeObj(PriceRange priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }

    /*最高能出租金*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*联系人*/
    private String lianxiren;
    public String getLianxiren() {
        return lianxiren;
    }
    public void setLianxiren(String lianxiren) {
        this.lianxiren = lianxiren;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}