package com.example.wy.fingerkitchen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.adapter.DishListAdapter;
import com.example.wy.fingerkitchen.contract.IDishListContract.*;
import com.example.wy.fingerkitchen.decoration.LineItemDecoration;
import com.example.wy.fingerkitchen.entity.Dish;
import com.example.wy.fingerkitchen.entity.DishPage;
import com.example.wy.fingerkitchen.presenter.DishListPresenterImpl;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishListActivity extends Activity implements IView {

    public static final String CLASSIFY_ID = "classify_id";
    public static final String DISH_NAME = "dish_name";
    private static final int REQUEST_TYPE_NOE = 0;
    private static final int REQUEST_TYPE_CLASSIFY = 1;
    private static final int REQUEST_TYPE_DISH = 2;

    private RecyclerView mRecyclerView;
    private TextView mTvNone;

    private DishListAdapter mAdapter;

    private IPresenter mPresenter;

    /**
     * 请求类型，分类查询请求或菜名查询请求
     */
    private int mRequestType = REQUEST_TYPE_NOE;
    /**
     * 分类查询id
     */
    private int mClassifyId = -1;
    /**
     * 菜名查询名称
     */
    private String mDishName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);
        checkIntent();
        createPresenter();
        initView();
    }

    private void checkIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mClassifyId = bundle.getInt(CLASSIFY_ID, -1);
        mDishName = bundle.getString(DISH_NAME);
        if (mClassifyId != -1) {
            mRequestType = REQUEST_TYPE_CLASSIFY;
        } else if (!TextUtils.isEmpty(mDishName)) {
            mRequestType = REQUEST_TYPE_DISH;
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.dish_list_recycler);
        mTvNone = findViewById(R.id.tv_none);
        mAdapter = new DishListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new LineItemDecoration());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnItemClickListener(mItemClickListener);
        mTvNone.setVisibility(View.GONE);
    }

    private void createPresenter() {
        mPresenter = new DishListPresenterImpl(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.requestData(mRequestType, mClassifyId, mDishName);
    }

    @Override
    public void onDataRequestSuccess(DishPage page) {
        if (page != null && page.result != null && page.result.data != null) {
            mAdapter.setDishList(page.result.data);
        }
    }

    @Override
    public void onDataRequestFailure() {
        mTvNone.setVisibility(View.VISIBLE);
    }

    private DishListAdapter.OnItemClickListener mItemClickListener = new DishListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Dish dish) {
            Intent intent = new Intent(DishListActivity.this, DishDetailActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("Dish", dish);
            startActivity(intent);
        }
    };
}
