package com.example.wy.fingerkitchen.network.request;

/**
 * @author wangyang
 * @date 2018/10/8
 *
 * http请求接口抽象
 */
public interface IRequest {

    String GET = "GET";
    String POST = "POST";

    /**
     * 设置请求method
     * @param method
     */
    void setMethod(String method);

    String getMethod();

    /**
     * 获取请求url
     */
    String getRequestUrl();
}
