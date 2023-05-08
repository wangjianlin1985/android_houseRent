package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Hourse {
    /*���ݱ��*/
    private int hourseId;
    public int getHourseId() {
        return hourseId;
    }
    public void setHourseId(int hourseId) {
        this.hourseId = hourseId;
    }

    /*��������*/
    private String hourseName;
    public String getHourseName() {
        return hourseName;
    }
    public void setHourseName(String hourseName) {
        this.hourseName = hourseName;
    }

    /*����¥��*/
    private BuildingInfo buildingObj;
    public BuildingInfo getBuildingObj() {
        return buildingObj;
    }
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }

    /*����ͼƬ*/
    private String housePhoto;
    public String getHousePhoto() {
        return housePhoto;
    }
    public void setHousePhoto(String housePhoto) {
        this.housePhoto = housePhoto;
    }

    /*��������*/
    private HourseType hourseTypeObj;
    public HourseType getHourseTypeObj() {
        return hourseTypeObj;
    }
    public void setHourseTypeObj(HourseType hourseTypeObj) {
        this.hourseTypeObj = hourseTypeObj;
    }

    /*�۸�Χ*/
    private PriceRange priceRangeObj;
    public PriceRange getPriceRangeObj() {
        return priceRangeObj;
    }
    public void setPriceRangeObj(PriceRange priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }

    /*���*/
    private String area;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    /*���(Ԫ/��)*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*¥��/��¥��*/
    private String louceng;
    public String getLouceng() {
        return louceng;
    }
    public void setLouceng(String louceng) {
        this.louceng = louceng;
    }

    /*װ��*/
    private String zhuangxiu;
    public String getZhuangxiu() {
        return zhuangxiu;
    }
    public void setZhuangxiu(String zhuangxiu) {
        this.zhuangxiu = zhuangxiu;
    }

    /*����*/
    private String caoxiang;
    public String getCaoxiang() {
        return caoxiang;
    }
    public void setCaoxiang(String caoxiang) {
        this.caoxiang = caoxiang;
    }

    /*�������*/
    private String madeYear;
    public String getMadeYear() {
        return madeYear;
    }
    public void setMadeYear(String madeYear) {
        this.madeYear = madeYear;
    }

    /*��ϵ��*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*��ϵ�绰*/
    private String connectPhone;
    public String getConnectPhone() {
        return connectPhone;
    }
    public void setConnectPhone(String connectPhone) {
        this.connectPhone = connectPhone;
    }

    /*��ϸ��Ϣ*/
    private String detail;
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /*��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}