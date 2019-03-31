package com.example.wy.fingerkitchen.network.request;

import com.example.wy.fingerkitchen.constant.AppConstants;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishListRequest implements IRequest {

    private static final String CLASSIFY_REQUEST_BASE_URL = "http://apis.juhe.cn/cook/index?";
    private static final String DISH_REQUEST_BASE_URL = "http://apis.juhe.cn/cook/query.php?";
    private int mRequestType;
    private int mCid;
    private String mDishName;

    public DishListRequest(int requestType, int cid, String dishName) {
        mRequestType = requestType;
        mCid = cid;
        mDishName = dishName;
    }

    @Override
    public String getMethod() {
        return GET;
    }

    @Override
    public String getRequestUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        if (mRequestType == 1) {
            stringBuilder.append(CLASSIFY_REQUEST_BASE_URL);
            stringBuilder.append("key").append("=").append(AppConstants.APP_KEY)
                    .append("&").append("cid").append("=").append(mCid);
        } else if (mRequestType == 2) {
            stringBuilder.append(DISH_REQUEST_BASE_URL);
            stringBuilder.append("key").append("=").append(AppConstants.APP_KEY)
                    .append("&").append("menu").append("=").append(mDishName);
        }
        return stringBuilder.toString();
    }
}
