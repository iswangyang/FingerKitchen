package com.example.wy.fingerkitchen.network;

import com.example.wy.fingerkitchen.network.request.IRequest;
import com.example.wy.fingerkitchen.network.response.IResponse;

/**
 * @author wangyang
 * @date 2018/10/8
 */
public interface IOkHttpClient {

    IResponse performRequest(IRequest request);

}
