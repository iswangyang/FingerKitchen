package com.example.wy.fingerkitchen.contract;


/**
 * @author LiLang
 * @date 2018/11/15
 */
public interface IMainContract {

    interface IMainView {

    }

    interface IMainPresenter {

        void initViewPager();

        void initBottomNavigation();
    }
}
