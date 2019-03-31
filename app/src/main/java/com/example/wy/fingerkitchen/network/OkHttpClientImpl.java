package com.example.wy.fingerkitchen.network;

import com.example.wy.fingerkitchen.network.request.IRequest;
import com.example.wy.fingerkitchen.network.response.IRequestCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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
    public void performRequest(IRequest request, final IRequestCallback callback) {
        if (request == null) {
            return;
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
        Call call = mOkHttpClient.newCall(realRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && callback != null) {
                   callback.onSuccess(response.body().string());
                }
            }
        });
    }
}
