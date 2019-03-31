package com.example.wy.fingerkitchen.network.response;

/**
 * @author wangyang
 * @date 2018/10/8
 *
 * http响应结果回调
 */
public interface IRequestCallback {

    void onSuccess(String json);

    void onFailure();
}
