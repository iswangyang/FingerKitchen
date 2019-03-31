package com.example.wy.fingerkitchen.network.request;

import com.example.wy.fingerkitchen.constant.AppConstants;

/**
 * @author LiLang
 * @date 2019/1/8
 */
public class GroupRequest implements IRequest {

    private static final String BASE_URL = "http://apis.juhe.cn/cook/category?";

    @Override
    public String getMethod() {
        return GET;
    }

    @Override
    public String getRequestUrl() {
        StringBuilder stringBuilder = new StringBuilder(BASE_URL);
        stringBuilder.append("key").append("=").append(AppConstants.APP_KEY);
        return stringBuilder.toString();
    }
}
