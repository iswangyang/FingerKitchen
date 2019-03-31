package com.example.wy.fingerkitchen.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.adapter.MainPageAdapter;
import com.example.wy.fingerkitchen.contract.IMainContract.IMainPresenter;
import com.example.wy.fingerkitchen.contract.IMainContract.IMainView;
import com.example.wy.fingerkitchen.fragment.BaseFragment;
import com.example.wy.fingerkitchen.fragment.ClassifyFragment;
import com.example.wy.fingerkitchen.fragment.HomeFragment;
import com.example.wy.fingerkitchen.fragment.CollectFragment;
import com.example.wy.fingerkitchen.presenter.MainPresenterImpl;
import com.example.wy.fingerkitchen.utils.UIUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lilang
 */
public class MainActivity extends FragmentActivity implements IMainView {

    private static final int[] CHECKED_STATE_SET = new int[] {android.R.attr.state_checked};
    private static final int[] UNCHECKED_STATE_SET = new int[] {-android.R.attr.state_checked};
    private IMainPresenter mMainPresenter;
    private ViewPager mViewPager;
    private MainPageAdapter mViewPagerAdapter;
    private BottomNavigationView mBottomNavView;
    private List<BaseFragment> mPageDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        setStatusBar();
        initView();
        initPresenter();
        initNavigation();
    }

    private void initView() {
        initPageContent();
        intViewPager();
        initNavigation();
    }

    private void initPresenter() {
        if (null == mMainPresenter) {
            mMainPresenter = new MainPresenterImpl(this);
        }
    }

    private void initPageContent() {
        mPageDataList = new ArrayList<>();
        mPageDataList.add(new HomeFragment());
        mPageDataList.add(new ClassifyFragment());
        mPageDataList.add(new CollectFragment());
    }

    private void intViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new MainPageAdapter(getSupportFragmentManager());
        mViewPagerAdapter.setDataList(mPageDataList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initNavigation() {
        mBottomNavView = findViewById(R.id.bottom_navigation);
        int[][] stateSet = new int[][] {UNCHECKED_STATE_SET, CHECKED_STATE_SET};
        int[] colorSet = new int[] {getResources().getColor(R.color.colorGrey),
                getResources().getColor(R.color.colorIndigo)};
        ColorStateList colorStateList = new ColorStateList(stateSet, colorSet);
        mBottomNavView.setItemTextColor(colorStateList);
        mBottomNavView.setItemIconTintList(colorStateList);
        mBottomNavView.setOnNavigationItemSelectedListener(mOnNavItemSelectedListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                mBottomNavView.setSelectedItemId(R.id.item_home);
            } else if (position == 1) {
                mBottomNavView.setSelectedItemId(R.id.item_classify);
            } else if (position == 2) {
                mBottomNavView.setSelectedItemId(R.id.item_mine);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private OnNavigationItemSelectedListener mOnNavItemSelectedListener = new OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if (item == null) {
                return false;
            }
            int currentId = 0;
            switch (item.getItemId()) {
                case R.id.item_home:
                    currentId = 0;
                    break;
                case R.id.item_classify:
                    currentId = 1;
                    break;
                case R.id.item_mine:
                    currentId = 2;
                    break;
                default:
                    break;
            }
            if (mViewPager != null) {
                mViewPager.setCurrentItem(currentId);
            }
            return true;
        }
    };

    private void setStatusBar() {
        UIUtils.setWindowStatusBarColor(this, Color.parseColor("#7BBD9A"));
    }
}
