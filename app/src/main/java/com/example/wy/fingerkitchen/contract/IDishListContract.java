package com.example.wy.fingerkitchen.contract;

import com.example.wy.fingerkitchen.entity.DishPage;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public interface IDishListContract {

    interface IPresenter {
        void requestData(int requestType, int cid, String dishName);

        void onDataRequestSuccess(DishPage page);

        void onDataRequestFailure();
    }

    interface IView {
        void onDataRequestSuccess(DishPage page);

        void onDataRequestFailure();
    }

    interface IModel {
        void requestData(int requestType, int cid, String dishName);
    }
}
