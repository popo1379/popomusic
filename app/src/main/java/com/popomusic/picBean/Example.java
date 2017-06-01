package com.popomusic.picBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class Example {

    @SerializedName("showapi_res_code")
    @Expose
    private Integer showapiResCode;
    @SerializedName("showapi_res_error")
    @Expose
    private String showapiResError;
    @SerializedName("showapi_res_body")
    @Expose
    private ShowapiResBody showapiResBody;

    public Integer getShowapiResCode() {
        return showapiResCode;
    }

    public void setShowapiResCode(Integer showapiResCode) {
        this.showapiResCode = showapiResCode;
    }

    public String getShowapiResError() {
        return showapiResError;
    }

    public void setShowapiResError(String showapiResError) {
        this.showapiResError = showapiResError;
    }

    public ShowapiResBody getShowapiResBody() {
        return showapiResBody;
    }

    public void setShowapiResBody(ShowapiResBody showapiResBody) {
        this.showapiResBody = showapiResBody;
    }
}

