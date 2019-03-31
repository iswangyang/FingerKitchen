package com.example.wy.fingerkitchen.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishResult {

    @SerializedName("data")
    public List<Dish> data;

}
