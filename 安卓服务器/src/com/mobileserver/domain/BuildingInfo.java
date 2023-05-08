package com.mobileserver.domain;

public class BuildingInfo {
    /*Â¥ÅÌ±àºÅ*/
    private int buildingId;
    public int getBuildingId() {
        return buildingId;
    }
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /*ËùÔÚÇøÓò*/
    private int areaObj;
    public int getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(int areaObj) {
        this.areaObj = areaObj;
    }

    /*Â¥ÅÌÃû³Æ*/
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /*Â¥ÅÌÍ¼Æ¬*/
    private String buildingPhoto;
    public String getBuildingPhoto() {
        return buildingPhoto;
    }
    public void setBuildingPhoto(String buildingPhoto) {
        this.buildingPhoto = buildingPhoto;
    }

}