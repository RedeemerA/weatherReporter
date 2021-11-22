package com.fanshuff.rma.weatherreporter.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WeatherDetailInfo {
    //返回状态，1为成功，0为失败。
    @JSONField(name = "status")
    private int status;
    //返回结果总数
    @JSONField(name = "count")
    private int count;
    //返回的状态信息
    @JSONField(name = "info")
    private String info;
    //返回的状态信息说明，10000代表正确
    @JSONField(name = "infocode")
    private int infocode;

    @JSONField(name = "lives")
    private List<LiveInfo> lives;

    public WeatherDetailInfo() {
    }

    public WeatherDetailInfo(int status, int count, String info, int infocode, List<LiveInfo> lives) {
        this.status = status;
        this.count = count;
        this.info = info;
        this.infocode = infocode;
        this.lives = lives;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getInfocode() {
        return infocode;
    }

    public void setInfocode(int infocode) {
        this.infocode = infocode;
    }

    public List<LiveInfo> getLives() {
        return lives;
    }

    public void setLives(List<LiveInfo> lives) {
        this.lives = lives;
    }
}
