package com.mobileserver.domain;

public class UserInfo {
    /*�û���*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*����*/
    private String realName;
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*��������*/
    private java.sql.Timestamp birthday;
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /*���֤*/
    private String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /*����*/
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    /*��Ƭ*/
    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*��ͥ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}