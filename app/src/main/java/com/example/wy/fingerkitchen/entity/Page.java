package com.example.wy.fingerkitchen.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public class Page {

    @SerializedName("resultcode")
    public String resultcode;

    @SerializedName("reason")
    public String reason;

    @SerializedName("result")
    public List<ClassifyOne> result;

    @SerializedName("error_code")
    int error_code;
}
