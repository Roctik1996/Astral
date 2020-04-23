package com.goroscop.astral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Horoscope {
    @SerializedName("today")
    @Expose
    private Today today;
    @SerializedName("tomorrow")
    @Expose
    private String tomorrow;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("china")
    @Expose
    private China china;

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public China getChina() {
        return china;
    }

    public void setChina(China china) {
        this.china = china;
    }

}
