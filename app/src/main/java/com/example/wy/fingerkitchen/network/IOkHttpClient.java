package com.example.wy.fingerkitchen.network;

import com.example.wy.fingerkitchen.network.request.IRequest;
import com.example.wy.fingerkitchen.network.response.IRequestCallback;

/**
 * @author wangyang
 * @date 2018/10/8
 */
public interface IOkHttpClient {

    void performRequest(IRequest request, IRequestCallback callback);

}
