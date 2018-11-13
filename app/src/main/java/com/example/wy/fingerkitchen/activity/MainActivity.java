package com.example.wy.fingerkitchen.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.contract.IBottomNavContract.IBottomNavPresenter;
import com.example.wy.fingerkitchen.presenter.BottomNavPresenterImpl;

/**
 * author: lilang
 * date:   2018/10/7 21:22
 */
public class MainActivity extends AppCompatActivity {

    private IBottomNavPresenter mBottomNavPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigation();
    }

    private void initBottomNavigation() {
        if (null == mBottomNavPresenter) {
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            mBottomNavPresenter = new BottomNavPresenterImpl(bottomNav, this);
        }
    }
}
