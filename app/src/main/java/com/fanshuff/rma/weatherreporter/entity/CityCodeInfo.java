package com.fanshuff.rma.weatherreporter.entity;

public class CityCodeInfo {

    private int id;
    private String chineseName;
    private String adcode;
    private String citycode;

    public CityCodeInfo() {
    }

    public CityCodeInfo(int id, String chineseName, String adcode, String citycode) {
        this.id = id;
        this.chineseName = chineseName;
        this.adcode = adcode;
        this.citycode = citycode;
    }

    public CityCodeInfo(int id, String chineseName, String adcode) {
        this.id = id;
        this.chineseName = chineseName;
        this.adcode = adcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
}
