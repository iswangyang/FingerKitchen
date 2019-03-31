package com.example.wy.fingerkitchen.contract;

import com.example.wy.fingerkitchen.entity.ClassifyOne;
import com.example.wy.fingerkitchen.entity.ClassifyTwo;
import java.util.HashMap;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public interface IClassifyContract {

    interface IView {
        void onDataRequestSuccess(List<ClassifyOne> classifyOneList, List<ClassifyTwo> classifyTwoList,
                                  HashMap<String, String> idToGroupMap);

        void onDataRequestFailure();
    }

    interface IPresenter {
        void requestData();

        void onDataRequestSuccess(List<ClassifyOne> classifyOneList, List<ClassifyTwo> classifyTwoList,
                                  HashMap<String, String> idToGroupMap);

        void onDataRequestFailure();
    }

    interface IModel {
        void requestData();
    }
}
