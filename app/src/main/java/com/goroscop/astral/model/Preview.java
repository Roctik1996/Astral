package com.goroscop.astral.model;

public class Preview {
    private int iconResource;
    private String info;

    public Preview() {
    }

    public Preview(int iconResource, String info) {
        this.iconResource = iconResource;
        this.info = info;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
