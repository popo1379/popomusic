package com.popomusic.bean;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
public class downBean {
    String name;
    String state;
    int soFarBytes;
    int totalBytes;

    public void setName(String name){
      this.name=name;
  }

    public void setState(String state){
        this.state=state;
    }

    public void setSoFarBytes(int soFarBytes){
        this.soFarBytes=soFarBytes;
    }

    public void setTotalBytes(int totalBytes){
        this.totalBytes=totalBytes;
    }

    public int getSoFarBytes(){
        return soFarBytes;
    }

    public int getTotalBytes(){
        return totalBytes;
    }


    public String getName(){
        return name;
    }

    public String getState(){
        return state;
    }








}
