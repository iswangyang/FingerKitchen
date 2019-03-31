package com.example.wy.fingerkitchen.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class Dish implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("tags")
    public String tags;

    @SerializedName("imtro")
    public String imtro;

    @SerializedName("ingredients")
    public String ingredients;

    @SerializedName("burden")
    public String burden;

    @SerializedName("albums")
    public List<String> albums;

    @SerializedName("steps")
    public List<Step> steps;

    public Dish() {

    }

    protected Dish(Parcel in) {
        id = in.readInt();
        title = in.readString();
        tags = in.readString();
        imtro = in.readString();
        ingredients = in.readString();
        burden = in.readString();
        albums = in.createStringArrayList();
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(tags);
        parcel.writeString(imtro);
        parcel.writeString(ingredients);
        parcel.writeString(burden);
        parcel.writeStringList(albums);
        parcel.writeTypedList(steps);
    }
}
