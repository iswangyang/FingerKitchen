package com.example.wy.fingerkitchen.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.contract.IMainContract.IMainPresenter;
import com.example.wy.fingerkitchen.contract.IMainContract.IMainView;
import com.example.wy.fingerkitchen.presenter.MainPresenterImpl;

/**
 * @author lilang
 */
public class MainActivity extends AppCompatActivity implements IMainView {

    private IMainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        initViewPager();
        initBottomNavigation();
    }

    private void initPresenter() {
        if (null == mMainPresenter) {
            mMainPresenter = new MainPresenterImpl(this);
        }
    }

    private void initViewPager() {
        if (mMainPresenter != null) {
            mMainPresenter.initViewPager();
        }
    }

    private void initBottomNavigation() {
        if (mMainPresenter != null) {
            mMainPresenter.initBottomNavigation();
        }
    }
}
