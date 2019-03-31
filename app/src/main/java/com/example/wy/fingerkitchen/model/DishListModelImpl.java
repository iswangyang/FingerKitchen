package com.example.wy.fingerkitchen.model;

import android.app.Activity;
import android.text.TextUtils;

import com.example.wy.fingerkitchen.contract.IDishListContract.*;
import com.example.wy.fingerkitchen.entity.DishPage;
import com.example.wy.fingerkitchen.network.IOkHttpClient;
import com.example.wy.fingerkitchen.network.OkHttpClientImpl;
import com.example.wy.fingerkitchen.network.request.DishListRequest;
import com.example.wy.fingerkitchen.network.response.IRequestCallback;
import com.google.gson.Gson;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishListModelImpl implements IModel {

    private static final String SUCCESS = "200";
    private Activity mActivity;
    private IPresenter mPresenter;

    public DishListModelImpl(Activity activity, IPresenter presenter) {
        mActivity = activity;
        mPresenter = presenter;
    }

    @Override
    public void requestData(int requestType, int cid, String dishName) {
        DishListRequest request = new DishListRequest(requestType, cid, dishName);
        IOkHttpClient okHttpClient = new OkHttpClientImpl();
        okHttpClient.performRequest(request, new IRequestCallback() {

            @Override
            public void onSuccess(String json) {
                if (TextUtils.isEmpty(json)) {
                    onFailure();
                    return;
                }
                Gson gson = new Gson();
                final DishPage page = gson.fromJson(json, DishPage.class);
                if (page == null || !SUCCESS.equals(page.resultcode)) {
                    onFailure();
                    return;
                }
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onDataRequestSuccess(page);
                    }
                });
            }

            @Override
            public void onFailure() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPresenter != null) {
                            mPresenter.onDataRequestFailure();
                        }
                    }
                });
            }
        });
    }

    private void onDataRequestSuccess(DishPage page) {
        if (mPresenter != null) {
            mPresenter.onDataRequestSuccess(page);
        }
    }
}
