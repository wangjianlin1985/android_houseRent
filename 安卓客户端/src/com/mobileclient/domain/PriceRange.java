package com.mobileclient.domain;

import java.io.Serializable;

public class PriceRange implements Serializable {
    /*记录编号*/
    private int rangeId;
    public int getRangeId() {
        return rangeId;
    }
    public void setRangeId(int rangeId) {
        this.rangeId = rangeId;
    }

    /*价格区间*/
    private String priceName;
    public String getPriceName() {
        return priceName;
    }
    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

}