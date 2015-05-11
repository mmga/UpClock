package com.mmga.upclock.activity;

/**
 * Created by mmga on 2015/5/10.
 */
public class ListCellData {
    private Boolean isAvailable;
    private int hour;
    private int minite;

    public ListCellData(Boolean isAvailable, int minite, int hour) {
        this.isAvailable = isAvailable;
        this.minite = minite;
        this.hour = hour;
    }


    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinite() {
        return minite;
    }

    public void setMinite(int minite) {
        this.minite = minite;
    }



}
