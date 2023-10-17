package com.ran.devx.peterobi_onenigeria.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Merch implements Parcelable {
    String xTitle;
    String xInfo;
    String xPrice;
    String xLink;
    String xType;
    byte[] xArt;

    public Merch(String mTitle, String mInfo, String mPrice, String mLink, String mType, byte[] mArt){
        xTitle = mTitle;
        xInfo = mInfo;
        xPrice = mPrice;
        xLink = mLink;
        xType = mType;
        xArt = mArt;

    }

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

    public String getxPrice() {
        return xPrice;
    }

    public void setxPrice(String xPrice) {
        this.xPrice = xPrice;
    }

    public String getxLink() {
        return xLink;
    }

    public void setxLink(String xLink) {
        this.xLink = xLink;
    }

    public String getxType() {
        return xType;
    }

    public void setxType(String xType) {
        this.xType = xType;
    }

    public byte[] getxArt() {
        return xArt;
    }

    public void setxArt(byte[] xArt) {
        this.xArt = xArt;
    }

    protected Merch(Parcel in) {
        xTitle = in.readString();
        xInfo = in.readString();
        xPrice = in.readString();
        xType = in.readString();
        xArt = in.createByteArray();
    }


    public static final Creator<Merch> CREATOR = new Creator<Merch>() {
        @Override
        public Merch createFromParcel(Parcel in) {
            return new Merch(in);
        }

        @Override
        public Merch[] newArray(int size) {
            return new Merch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(xTitle);
        parcel.writeString(xInfo);
        parcel.writeString(xPrice);
        parcel.writeString(xLink);
        parcel.writeString(xType);
        parcel.writeByteArray(xArt);
    }
}
