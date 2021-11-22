package com.fanshuff.rma.weatherreporter.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ForecastInfo {
    //城市名称
    @JSONField(name = "city")
    private String city;
    //城市编码
    @JSONField(name = "adcode")
    private String adcode;
    //省份名称
    @JSONField(name = "province")
    private String province;
    //预报发布时间
    @JSONField(name = "reporttime")
    private String reporttime;
    //当天，第二天，第三天...的天气情况列表
    @JSONField(name = "casts")
    private List<CastInfo> casts;

    public ForecastInfo(String city, String adcode, String province, String reporttime, List<CastInfo> casts) {
        this.city = city;
        this.adcode = adcode;
        this.province = province;
        this.reporttime = reporttime;
        this.casts = casts;
    }

    public ForecastInfo() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public List<CastInfo> getCasts() {
        return casts;
    }

    public void setCasts(List<CastInfo> casts) {
        this.casts = casts;
    }
}
