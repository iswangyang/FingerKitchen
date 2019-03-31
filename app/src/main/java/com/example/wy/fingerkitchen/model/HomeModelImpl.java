package com.example.wy.fingerkitchen.model;

import com.example.wy.fingerkitchen.contract.IHomeContract;

/**
 * @author LiLang
 * @date 2019/3/31
 */
public class HomeModelImpl implements IHomeContract.IModel {


    private IHomeContract.IPresenter mPresenter;

    public HomeModelImpl(IHomeContract.IPresenter presenter) {
        mPresenter = presenter;
    }

}
