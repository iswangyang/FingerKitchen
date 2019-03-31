package com.example.wy.fingerkitchen.presenter;

import android.app.Activity;

import com.example.wy.fingerkitchen.contract.IClassifyContract;
import com.example.wy.fingerkitchen.entity.ClassifyOne;
import com.example.wy.fingerkitchen.entity.ClassifyTwo;
import com.example.wy.fingerkitchen.entity.Page;
import com.example.wy.fingerkitchen.model.ClassifyModelImpl;

import java.util.HashMap;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public class ClassifyPresenterImpl implements IClassifyContract.IPresenter {

    private IClassifyContract.IModel mModel;
    private IClassifyContract.IView mView;

    public ClassifyPresenterImpl(Activity activity, IClassifyContract.IView view) {
        mView = view;
        mModel = new ClassifyModelImpl(activity, this);
    }

    @Override
    public void requestData() {
        mModel.requestData();
    }

    @Override
    public void onDataRequestSuccess(List<ClassifyOne> classifyOneList, List<ClassifyTwo> classifyTwoList,
                                     HashMap<String, String> idToGroupMap) {
        mView.onDataRequestSuccess(classifyOneList, classifyTwoList, idToGroupMap);
    }

    @Override
    public void onDataRequestFailure() {
        mView.onDataRequestFailure();
    }
}
