package com.example.wy.fingerkitchen.network;

import com.example.wy.fingerkitchen.network.request.IRequest;
import com.example.wy.fingerkitchen.network.response.IResponse;
import com.example.wy.fingerkitchen.network.response.ResponseImpl;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangyang
 * @date 2018/10/8
 *
 * 数据请求封装
 */
public class OkHttpClientImpl implements IOkHttpClient {

    private OkHttpClient mOkHttpClient;

    public OkHttpClientImpl() {
        mOkHttpClient = new OkHttpClient.Builder().build();;
    }

    @Override
    public IResponse performRequest(IRequest request) {
        if (request == null) {
            return null;
        }
        Request.Builder builder = new Request.Builder();
        builder.url(request.getRequestUrl());
        String method = request.getMethod();
        if (IRequest.POST.equals(method)) {
            //POST请求
        } else {
            //GET请求（默认GET）
            builder.get();
        }
        Request realRequest = builder.build();
        IResponse response = new ResponseImpl();
        try {
            Response okResponse = mOkHttpClient.newCall(realRequest).execute();
            if (okResponse != null) {
                response.setCode(okResponse.code());
                response.setResult(okResponse.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
