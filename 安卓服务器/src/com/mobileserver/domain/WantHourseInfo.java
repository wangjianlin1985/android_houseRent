package com.mobileserver.domain;

public class WantHourseInfo {
    /*��¼���*/
    private int wantHourseId;
    public int getWantHourseId() {
        return wantHourseId;
    }
    public void setWantHourseId(int wantHourseId) {
        this.wantHourseId = wantHourseId;
    }

    /*�����û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��������*/
    private int position;
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    /*��������*/
    private int hourseTypeObj;
    public int getHourseTypeObj() {
        return hourseTypeObj;
    }
    public void setHourseTypeObj(int hourseTypeObj) {
        this.hourseTypeObj = hourseTypeObj;
    }

    /*�۸�Χ*/
    private int priceRangeObj;
    public int getPriceRangeObj() {
        return priceRangeObj;
    }
    public void setPriceRangeObj(int priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }

    /*����ܳ����*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��ϵ��*/
    private String lianxiren;
    public String getLianxiren() {
        return lianxiren;
    }
    public void setLianxiren(String lianxiren) {
        this.lianxiren = lianxiren;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}