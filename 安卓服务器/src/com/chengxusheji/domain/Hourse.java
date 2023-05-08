package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Hourse {
    /*房屋编号*/
    private int hourseId;
    public int getHourseId() {
        return hourseId;
    }
    public void setHourseId(int hourseId) {
        this.hourseId = hourseId;
    }

    /*房屋名称*/
    private String hourseName;
    public String getHourseName() {
        return hourseName;
    }
    public void setHourseName(String hourseName) {
        this.hourseName = hourseName;
    }

    /*所在楼盘*/
    private BuildingInfo buildingObj;
    public BuildingInfo getBuildingObj() {
        return buildingObj;
    }
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }

    /*房屋图片*/
    private String housePhoto;
    public String getHousePhoto() {
        return housePhoto;
    }
    public void setHousePhoto(String housePhoto) {
        this.housePhoto = housePhoto;
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

    /*面积*/
    private String area;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    /*租金(元/月)*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*楼层/总楼层*/
    private String louceng;
    public String getLouceng() {
        return louceng;
    }
    public void setLouceng(String louceng) {
        this.louceng = louceng;
    }

    /*装修*/
    private String zhuangxiu;
    public String getZhuangxiu() {
        return zhuangxiu;
    }
    public void setZhuangxiu(String zhuangxiu) {
        this.zhuangxiu = zhuangxiu;
    }

    /*朝向*/
    private String caoxiang;
    public String getCaoxiang() {
        return caoxiang;
    }
    public void setCaoxiang(String caoxiang) {
        this.caoxiang = caoxiang;
    }

    /*建筑年代*/
    private String madeYear;
    public String getMadeYear() {
        return madeYear;
    }
    public void setMadeYear(String madeYear) {
        this.madeYear = madeYear;
    }

    /*联系人*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*联系电话*/
    private String connectPhone;
    public String getConnectPhone() {
        return connectPhone;
    }
    public void setConnectPhone(String connectPhone) {
        this.connectPhone = connectPhone;
    }

    /*详细信息*/
    private String detail;
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /*地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}