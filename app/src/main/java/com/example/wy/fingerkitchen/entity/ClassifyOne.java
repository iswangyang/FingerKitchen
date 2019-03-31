package com.example.wy.fingerkitchen.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public class ClassifyOne {

    @SerializedName("parentId")
    public String parentId;

    @SerializedName("name")
    public String name;

    @SerializedName("list")
    public List<ClassifyTwo> list;
}
