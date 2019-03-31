package com.example.wy.fingerkitchen.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.adapter.StepRecyclerAdapter;
import com.example.wy.fingerkitchen.database.DishSQLiteHelper;
import com.example.wy.fingerkitchen.entity.Dish;
import com.example.wy.fingerkitchen.entity.DishPage;
import com.example.wy.fingerkitchen.entity.DishResult;
import com.example.wy.fingerkitchen.entity.Page;
import com.example.wy.fingerkitchen.network.IOkHttpClient;
import com.example.wy.fingerkitchen.network.OkHttpClientImpl;
import com.example.wy.fingerkitchen.network.request.DishRequest;
import com.example.wy.fingerkitchen.network.response.IRequestCallback;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import static com.example.wy.fingerkitchen.database.DishSQLiteHelper.DB_NAME;
import static com.example.wy.fingerkitchen.database.DishSQLiteHelper.DB_VERSION;
import static com.example.wy.fingerkitchen.database.DishSQLiteHelper.TABLE_COLLECT;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishDetailActivity extends Activity implements View.OnClickListener{

    private Dish mDish;
    private TextView mTitleDishName;
    private SimpleDraweeView mDishImage;
    private TextView mDishInfo;
    private TextView mDishMaterial;
    private ImageView mDishCollectImage;
    private RecyclerView mRecyclerView;
    private StepRecyclerAdapter mAdapter;
    private DishSQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);
        Fresco.initialize(this);
        checkIntent();
        initDataBase();
        initView();
    }

    private void checkIntent() {
        Intent intent = getIntent();
        mDish = intent.getParcelableExtra("Dish");
        if (mDish == null) {
            int dishId = intent.getIntExtra("dish_id", -1);
            if (dishId != -1) {
                requestDish(dishId);
            }
        }
    }

    private void initDataBase() {
        mSQLiteHelper = new DishSQLiteHelper(this, DB_NAME, null, DB_VERSION);
    }


    private void initView() {
        mTitleDishName = findViewById(R.id.title_dish_name);
        mDishImage = findViewById(R.id.dish_image);
        mDishInfo = findViewById(R.id.tv_info);
        mDishMaterial = findViewById(R.id.tv_material_content);
        mDishCollectImage = findViewById(R.id.dish_collect_image);
        mDishCollectImage.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.step_recycler);
        mRecyclerView.setFocusable(false);
        mAdapter = new StepRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
    }

    private void setInfo() {
        if (mDish == null) {
            return;
        }
        if (!TextUtils.isEmpty(mDish.title)) {
            mTitleDishName.setText(mDish.title);
        }
        if (mDish.albums != null && mDish.albums.size() > 0) {
            mDishImage.setImageURI(mDish.albums.get(0));
        }
        if (!TextUtils.isEmpty(mDish.imtro)) {
            mDishInfo.setText(mDish.imtro);
        }
        if (!TextUtils.isEmpty(mDish.burden)) {
            mDishMaterial.setText(mDish.burden);
        }
        SQLiteDatabase database =  mSQLiteHelper.getReadableDatabase();
        if (database != null) {
            Cursor cursor = database.query(TABLE_COLLECT, null, "dish_id = " + mDish.id,
                    null, null, null, null, null);
            boolean hasCollected = false;
            while (cursor.moveToNext()) {
                hasCollected = true;
                break;
            }
            cursor.close();
            mDishCollectImage.setSelected(hasCollected);
        }
        mAdapter.setData(mDish.steps);
    }

    private void requestDish(int dishId) {
        DishRequest request = new DishRequest(dishId);
        IOkHttpClient okHttpClient = new OkHttpClientImpl();
        okHttpClient.performRequest(request, new IRequestCallback() {

            @Override
            public void onSuccess(String json) {
                parseData(json);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mDishCollectImage) {
            collectDish();
        }
    }

    private void collectDish() {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        if (mDishCollectImage.isSelected()) {
            //取消收藏
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
            database.delete(TABLE_COLLECT, "dish_id = " + mDish.id, null);
            mDishCollectImage.setSelected(false);
        } else {
            //收藏
            if (database != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("dish_id", mDish.id);
                if (mDish.albums != null && mDish.albums.size() > 0) {
                    contentValues.put("dish_image_url", mDish.albums.get(0));
                }
                contentValues.put("dish_name", mDish.title);
                contentValues.put("dish_info", mDish.tags);
                database.insert(TABLE_COLLECT, null, contentValues);
            }
            mDishCollectImage.setSelected(true);
            Toast.makeText(this, "已加入收藏", Toast.LENGTH_SHORT).show();
        }

    }

    private void parseData(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        final DishPage page = new Gson().fromJson(json, DishPage.class);
        if (page == null || !"200".equals(page.resultcode)) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onDataRequstSuccess(page);
            }
        });
    }

    private void onDataRequstSuccess(DishPage page) {
        if (page == null || page.result == null || page.result.data == null || page.result.data.size() == 0) {
            return;
        }
        mDish = page.result.data.get(0);
        setInfo();
    }


}
