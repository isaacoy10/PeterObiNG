package com.ran.devx.peterobi_onenigeria.objects;

public class Update {
    private String xTitle, xInfo, xDate, xUrl;
    byte[] xArt;


    public String getxTitle() {
        return xTitle;
    }

    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
    }

    public String getxInfo() {
        return xInfo;
    }

    public void setxInfo(String xInfo) {
        this.xInfo = xInfo;
    }

    public String getxDate() {
        return xDate;
    }

    public void setxDate(String xDate) {
        this.xDate = xDate;
    }

    public String getxUrl() {
        return xUrl;
    }

    public void setxUrl(String xUrl) {
        this.xUrl = xUrl;
    }

    public byte[] getxArt() {
        return xArt;
    }

    public void setxArt(byte[] xArt) {
        this.xArt = xArt;
    }

    public Update(String title, String info, String date, String url, byte[] art) {
        this.xTitle = title;
        this.xInfo = info;
        this.xDate = date;
        this.xUrl = url;
        this.xArt = art;
    }
}
