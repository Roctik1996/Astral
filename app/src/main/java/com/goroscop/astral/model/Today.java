package com.goroscop.astral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Today {
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("luck_numbers")
    @Expose
    private List<Integer> luckNumbers = null;
    @SerializedName("love")
    @Expose
    private Integer love;
    @SerializedName("health")
    @Expose
    private Integer health;
    @SerializedName("career")
    @Expose
    private Integer career;
    @SerializedName("success_day")
    @Expose
    private Integer successDay;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Integer> getLuckNumbers() {
        return luckNumbers;
    }

    public void setLuckNumbers(List<Integer> luckNumbers) {
        this.luckNumbers = luckNumbers;
    }

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }

    public Integer getSuccessDay() {
        return successDay;
    }

    public void setSuccessDay(Integer successDay) {
        this.successDay = successDay;
    }
}
