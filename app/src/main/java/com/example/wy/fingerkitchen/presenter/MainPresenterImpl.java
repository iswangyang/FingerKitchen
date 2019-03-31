package com.example.wy.fingerkitchen.presenter;

import android.app.Activity;

import com.example.wy.fingerkitchen.contract.IMainContract.IMainPresenter;

/**
 * @author LiLang
 * @date 2018/11/15
 */
public class MainPresenterImpl implements IMainPresenter {

    private Activity mActivity;

    public MainPresenterImpl(Activity activity) {
        mActivity = activity;
    }
}
