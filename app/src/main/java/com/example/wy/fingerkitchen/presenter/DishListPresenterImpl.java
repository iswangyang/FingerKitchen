package com.example.wy.fingerkitchen.presenter;

import android.app.Activity;

import com.example.wy.fingerkitchen.contract.IDishListContract.*;
import com.example.wy.fingerkitchen.entity.DishPage;
import com.example.wy.fingerkitchen.model.DishListModelImpl;


/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishListPresenterImpl implements IPresenter {

    private Activity mActivity;
    private IModel mModel;
    private IView mView;

    public DishListPresenterImpl(Activity activity, IView view) {
        mActivity = activity;
        mView = view;
        mModel = new DishListModelImpl(activity, this);
    }

    @Override
    public void requestData(int requestType, int cid, String dishName) {
        mModel.requestData(requestType, cid, dishName);
    }

    @Override
    public void onDataRequestSuccess(DishPage page) {
        mView.onDataRequestSuccess(page);
    }

    @Override
    public void onDataRequestFailure() {
        mView.onDataRequestFailure();
    }
}
