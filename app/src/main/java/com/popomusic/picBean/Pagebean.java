package com.popomusic.picBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class Pagebean {

    @SerializedName("allNum")
    @Expose
    private Integer allNum;
    @SerializedName("allPages")
    @Expose
    private Integer allPages;
    @SerializedName("contentlist")
    @Expose
    private List<Contentlist> contentlist = null;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("maxResult")
    @Expose
    private Integer maxResult;

        public Integer getAllNum() {
            return allNum;
        }

        public void setAllNum(Integer allNum) {
            this.allNum = allNum;
        }

        public Integer getAllPages() {
            return allPages;
        }

        public void setAllPages(Integer allPages) {
            this.allPages = allPages;
        }

        public List<Contentlist> getContentlist() {
            return contentlist;
        }

        public void setContentlist(java.util.List<Contentlist> contentlist) {
            this.contentlist = contentlist;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(Integer maxResult) {
            this.maxResult = maxResult;
        }

    }

