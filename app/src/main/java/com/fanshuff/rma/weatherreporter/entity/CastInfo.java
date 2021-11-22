package com.fanshuff.rma.weatherreporter.entity;


public class CastInfo {
    //日期

    private String data = null;
    //星期几

    private String week = null;
    //白天天气情况

    private String dayweather = null;
    //夜间天气情况

    private String nightweather = null;
    //白天温度。

    private String daytemp = null;
    //夜间温度

    private String nighttemp = null;
    //白天风向

    private String daywind = null;
    //夜间风向

    private String nightwind = null;
    //白天风力

    private String daypower = null;
    //夜间风力

    private String nightpower = null;


    public CastInfo() {
    }

    public CastInfo(String data, String week, String dayweather, String nightweather, String daytemp, String nighttemp, String daywind, String nightwind, String daypower, String nightpower) {
        this.data = data;
        this.week = week;
        this.dayweather = dayweather;
        this.nightweather = nightweather;
        this.daytemp = daytemp;
        this.nighttemp = nighttemp;
        this.daywind = daywind;
        this.nightwind = nightwind;
        this.daypower = daypower;
        this.nightpower = nightpower;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDayweather() {
        return dayweather;
    }

    public void setDayweather(String dayweather) {
        this.dayweather = dayweather;
    }

    public String getNightweather() {
        return nightweather;
    }

    public void setNightweather(String nightweather) {
        this.nightweather = nightweather;
    }

    public String getDaytemp() {
        return daytemp;
    }

    public void setDaytemp(String daytemp) {
        this.daytemp = daytemp;
    }

    public String getNighttemp() {
        return nighttemp;
    }

    public void setNighttemp(String nighttemp) {
        this.nighttemp = nighttemp;
    }

    public String getDaywind() {
        return daywind;
    }

    public void setDaywind(String daywind) {
        this.daywind = daywind;
    }

    public String getNightwind() {
        return nightwind;
    }

    public void setNightwind(String nightwind) {
        this.nightwind = nightwind;
    }

    public String getDaypower() {
        return daypower;
    }

    public void setDaypower(String daypower) {
        this.daypower = daypower;
    }

    public String getNightpower() {
        return nightpower;
    }

    public void setNightpower(String nightpower) {
        this.nightpower = nightpower;
    }
}
