package com.example.wy.fingerkitchen.network.response;

/**
 * @author wangyang
 * @date 2018/10/8
 */
public class ResponseImpl implements IResponse {

    private int mCode;
    private String mResult;

    @Override
    public int getCode() {
        return mCode;
    }

    @Override
    public String getResult() {
        return mResult;
    }

    @Override
    public void setCode(int code) {
        this.mCode = code;
    }

    @Override
    public void setResult(String result) {
        this.mResult = result;
    }
}
