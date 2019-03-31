package com.example.wy.fingerkitchen.network.request;

import com.example.wy.fingerkitchen.constant.AppConstants;

/**
 * @author LiLang
 * @date 2019/3/31
 */
public class DishRequest implements IRequest {

    private static final String BASE_URL = "http://apis.juhe.cn/cook/queryid?";
    private int mDishId;

    public DishRequest(int dishId) {
        mDishId = dishId;
    }

    @Override
    public String getMethod() {
        return GET;
    }

    @Override
    public String getRequestUrl() {
        StringBuilder stringBuilder = new StringBuilder(BASE_URL);
        stringBuilder.append("key").append("=").append(AppConstants.APP_KEY)
                .append("&").append("id").append("=").append(mDishId);
        return stringBuilder.toString();
    }
}
