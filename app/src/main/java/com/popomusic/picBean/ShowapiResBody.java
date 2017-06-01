package com.popomusic.picBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class ShowapiResBody {
    @SerializedName("pagebean")
    @Expose
    private Pagebean pagebean;
    @SerializedName("ret_code")
    @Expose
    private Integer retCode;

    public Pagebean getPagebean() {
        return pagebean;
    }

    public void setPagebean(Pagebean pagebean) {
        this.pagebean = pagebean;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }
}
