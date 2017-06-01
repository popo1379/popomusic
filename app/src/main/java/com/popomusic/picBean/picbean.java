package com.popomusic.picBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class Picbean {

    @SerializedName("big")
    @Expose
    private String big;
    @SerializedName("middle")
    @Expose
    private String middle;
    @SerializedName("small")
    @Expose
    private String small;

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

}