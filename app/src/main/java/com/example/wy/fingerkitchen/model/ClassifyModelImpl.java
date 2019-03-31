package com.example.wy.fingerkitchen.model;

import android.app.Activity;
import android.text.TextUtils;

import com.example.wy.fingerkitchen.contract.IClassifyContract;
import com.example.wy.fingerkitchen.entity.ClassifyOne;
import com.example.wy.fingerkitchen.entity.ClassifyTwo;
import com.example.wy.fingerkitchen.entity.Page;
import com.example.wy.fingerkitchen.network.IOkHttpClient;
import com.example.wy.fingerkitchen.network.OkHttpClientImpl;
import com.example.wy.fingerkitchen.network.request.GroupRequest;
import com.example.wy.fingerkitchen.network.response.IRequestCallback;
import com.example.wy.fingerkitchen.utils.NetWorkUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public class ClassifyModelImpl implements IClassifyContract.IModel {

    private static final String SUCCESS = "200";
    private Activity mActivity;
    private IClassifyContract.IPresenter mPresenter;
    private HashMap<String, String> mIdToNameMap;

    public ClassifyModelImpl(Activity activity, IClassifyContract.IPresenter presenter) {
        mActivity = activity;
        mPresenter = presenter;
    }

    @Override
    public void requestData() {
        if (!NetWorkUtils.isNetWorkAvailble(mActivity)) {
            onDataRequestFailure();
            return;
        }
        GroupRequest groupRequest = new GroupRequest();
        IOkHttpClient okHttpClient = new OkHttpClientImpl();
        okHttpClient.performRequest(groupRequest, new IRequestCallback() {
            @Override
            public void onSuccess(String json) {
                if (TextUtils.isEmpty(json)) {
                    onFailure();
                    return;
                }
                Gson gson = new Gson();
                final Page page = gson.fromJson(json, Page.class);
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
                onDataRequestFailure();
            }
        });
    }

    private void onDataRequestSuccess(Page page) {
        List<ClassifyOne> classifyOneList = page.result;
        List<ClassifyTwo> classifyTwoList = constructClassifyTwoList(page);
        mPresenter.onDataRequestSuccess(classifyOneList, classifyTwoList, mIdToNameMap);
    }

    private void onDataRequestFailure() {
        mPresenter.onDataRequestFailure();
    }

    private List<ClassifyTwo> constructClassifyTwoList(Page page) {
        mIdToNameMap = new HashMap<>();
        List<ClassifyTwo> result = new ArrayList<>();
        if (page == null || page.result == null) {
            return result;
        }
        for (ClassifyOne classifyOne : page.result) {
            if (classifyOne.list != null && classifyOne.list.size() > 0) {
                String groupName = classifyOne.name;
                for (ClassifyTwo classifyTwo : classifyOne.list) {
                    result.add(classifyTwo);
                    mIdToNameMap.put(classifyTwo.id, groupName);
                }
                int size = classifyOne.list.size();
                if (size % 2 == 1) {
                    ClassifyTwo lastOne = classifyOne.list.get(size - 1);
                    ClassifyTwo empty = new ClassifyTwo();
                    empty.id = lastOne.id + "right";
                    empty.parentId = lastOne.parentId;
                    empty.name = "";
                    result.add(empty);
                    classifyOne.list.add(empty);
                    mIdToNameMap.put(empty.id, groupName);
                }
            }
        }
        return result;
    }
}
