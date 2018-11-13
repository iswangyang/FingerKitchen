package com.example.wy.fingerkitchen.presenter;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import com.example.wy.fingerkitchen.contract.IBottomNavContract.IBottomNavView;
import com.example.wy.fingerkitchen.contract.IBottomNavContract.IBottomNavPresenter;
import com.example.wy.fingerkitchen.view.BottomNavViewImpl;

/**
 * @author LiLang
 * @date 2018/11/13
 */
public class BottomNavPresenterImpl implements IBottomNavPresenter {

    private IBottomNavView mBottomNavView;

    public BottomNavPresenterImpl(BottomNavigationView navigationView, Activity activity) {
        mBottomNavView = new BottomNavViewImpl(navigationView, activity);
    }
}
