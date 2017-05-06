package com.popomusic.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/20 0020.
 */
@Entity
public class SearchNameBean {
    @Id
    private Long id;

    @Property(nameInDb = "SearchNAME")
    private String searchname;

    @Generated(hash = 1225585678)
    public SearchNameBean(Long id, String searchname) {
        this.id = id;
        this.searchname = searchname;
    }

    @Generated(hash = 445480946)
    public SearchNameBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchname() {
        return this.searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }

}
