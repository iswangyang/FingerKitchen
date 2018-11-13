package com.example.wy.fingerkitchen.view;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.contract.IBottomNavContract.IBottomNavView;

/**
 * @author LiLang
 * @date 2018/11/13
 */
public class BottomNavViewImpl implements IBottomNavView {

    private static final int[] CHECKED_STATE_SET = new int[] {android.R.attr.state_checked};
    private static final int[] UNCHECKED_STATE_SET = new int[] {-android.R.attr.state_checked};

    private BottomNavigationView mBottomNavView;
    private Activity mActivity;

    public BottomNavViewImpl(@NonNull BottomNavigationView navigationView, Activity activity) {
        mBottomNavView = navigationView;
        mActivity = activity;
        init();
    }

    private void init() {
        int[][] stateSet = new int[][] {UNCHECKED_STATE_SET, CHECKED_STATE_SET};
        int[] colorSet = new int[] {mActivity.getResources().getColor(R.color.colorGrey),
                mActivity.getResources().getColor(R.color.colorIndigo)};
        ColorStateList colorStateList = new ColorStateList(stateSet, colorSet);
        mBottomNavView.setItemTextColor(colorStateList);
        mBottomNavView.setItemIconTintList(colorStateList);
    }
}
