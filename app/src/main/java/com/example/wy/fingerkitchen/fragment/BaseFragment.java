package com.example.wy.fingerkitchen.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author LiLang
 * @date 2019/1/8
 */
public class BaseFragment extends Fragment {

    private boolean mViewCreated;
    private boolean mVisibleToUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewCreated = true;
        onPreLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisibleToUser = isVisibleToUser;
        onPreLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewCreated = false;
    }

    protected void createPresenter() {

    }

    protected void loadData() {

    }

    private void onPreLoadData() {
        if (mViewCreated && mVisibleToUser) {
            loadData();
        }
    }


}
