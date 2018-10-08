package com.example.wy.fingerkitchen.network.response;

/**
 * @author wangyang
 * @date 2018/10/8
 *
 * http响应结果抽象
 */
public interface IResponse {

    /**
     * 获取响应码
     */
    int getCode();

    void setCode(int code);

    /**
     * 获取请求结果串
     */
    String getResult();

    void setResult(String result);
}
