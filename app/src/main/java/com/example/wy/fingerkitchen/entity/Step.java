package com.example.wy.fingerkitchen.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class Step implements Parcelable {

    @SerializedName("img")
    public String img;
    @SerializedName("step")
    public String step;

    protected Step(Parcel in) {
        img = in.readString();
        step = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img);
        parcel.writeString(step);
    }
}
