package com.goroscop.astral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class China {

    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("success_day")
    @Expose
    private Integer successDay;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getSuccessDay() {
        return successDay;
    }

    public void setSuccessDay(Integer successDay) {
        this.successDay = successDay;
    }
}
