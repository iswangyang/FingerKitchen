package com.example.wy.fingerkitchen.presenter;

import com.example.wy.fingerkitchen.contract.IHomeContract;

/**
 * @author LiLang
 * @date 2019/3/31
 */
public class HomePresenterImpl implements IHomeContract.IPresenter {

    private IHomeContract.IView mView;
    private IHomeContract.IModel mModel;

    public HomePresenterImpl(IHomeContract.IView view) {
        mView = view;
    }
}
