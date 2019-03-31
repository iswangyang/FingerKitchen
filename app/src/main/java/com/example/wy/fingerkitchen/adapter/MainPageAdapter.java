package com.example.wy.fingerkitchen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.wy.fingerkitchen.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/8
 */
public class MainPageAdapter extends FragmentPagerAdapter {

    public List<BaseFragment> mFragmentList;

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDataList(List<BaseFragment> dataList) {
        mFragmentList = dataList;
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}
