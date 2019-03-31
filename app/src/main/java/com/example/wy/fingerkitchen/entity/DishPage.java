package com.example.wy.fingerkitchen.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishPage {
    @SerializedName("resultcode")
    public String resultcode;

    @SerializedName("reason")
    public String reason;

    @SerializedName("result")
    public DishResult result;

    @SerializedName("error_code")
    int error_code;
}
