package com.popomusic.videoModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
@Entity
public class VideoBean {
    @Id(autoincrement = true)
    private Long id;
    public String dataType;
   // public int id;
    public String title;
    public String text;
    public String description;
    public String image;
    public String actionUrl;
    public boolean shade;
   // public Cover cover;
    public String playUrl;
    public String category;
    public long duration;
   // public Header header;
  //  public List<ItemList> itemList;
   // public Author author;
    public String icon;
    @Generated(hash = 80025058)
    public VideoBean(Long id, String dataType, String title, String text,
            String description, String image, String actionUrl, boolean shade,
            String playUrl, String category, long duration, String icon) {
        this.id = id;
        this.dataType = dataType;
        this.title = title;
        this.text = text;
        this.description = description;
        this.image = image;
        this.actionUrl = actionUrl;
        this.shade = shade;
        this.playUrl = playUrl;
        this.category = category;
        this.duration = duration;
        this.icon = icon;
    }
    @Generated(hash = 2024490299)
    public VideoBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDataType() {
        return this.dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getActionUrl() {
        return this.actionUrl;
    }
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
    public boolean getShade() {
        return this.shade;
    }
    public void setShade(boolean shade) {
        this.shade = shade;
    }
    public String getPlayUrl() {
        return this.playUrl;
    }
    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public long getDuration() {
        return this.duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
