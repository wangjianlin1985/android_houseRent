package com.mobileclient.domain;

import java.io.Serializable;

public class AreaInfo implements Serializable {
    /*记录编号*/
    private int areaId;
    public int getAreaId() {
        return areaId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    /*区域名称*/
    private String areaName;
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

}