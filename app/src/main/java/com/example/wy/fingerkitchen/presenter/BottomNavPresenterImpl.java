package com.example.wy.fingerkitchen.presenter;

import android.app.Activity;
import com.example.wy.fingerkitchen.contract.IBottomNavContract.IBottomNavView;
import com.example.wy.fingerkitchen.contract.IBottomNavContract.IBottomNavPresenter;
import com.example.wy.fingerkitchen.view.BottomNavViewImpl;

/**
 * @author LiLang
 * @date 2018/11/13
 *
 * 首页导航栏逻辑控制层
 */
public class BottomNavPresenterImpl implements IBottomNavPresenter {

    private IBottomNavView mBottomNavView;

    public BottomNavPresenterImpl(Activity activity) {
        mBottomNavView = new BottomNavViewImpl(activity);
    }
}
