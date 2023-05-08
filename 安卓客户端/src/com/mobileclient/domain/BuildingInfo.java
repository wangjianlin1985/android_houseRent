package com.mobileclient.domain;

import java.io.Serializable;

public class BuildingInfo implements Serializable {
    /*楼盘编号*/
    private int buildingId;
    public int getBuildingId() {
        return buildingId;
    }
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /*所在区域*/
    private int areaObj;
    public int getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(int areaObj) {
        this.areaObj = areaObj;
    }

    /*楼盘名称*/
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /*楼盘图片*/
    private String buildingPhoto;
    public String getBuildingPhoto() {
        return buildingPhoto;
    }
    public void setBuildingPhoto(String buildingPhoto) {
        this.buildingPhoto = buildingPhoto;
    }

}