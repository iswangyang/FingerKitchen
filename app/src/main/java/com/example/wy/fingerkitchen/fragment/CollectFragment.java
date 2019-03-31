package com.example.wy.fingerkitchen.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.activity.DishDetailActivity;
import com.example.wy.fingerkitchen.activity.DishListActivity;
import com.example.wy.fingerkitchen.adapter.DishListAdapter;
import com.example.wy.fingerkitchen.database.DishSQLiteHelper;
import com.example.wy.fingerkitchen.decoration.LineItemDecoration;
import com.example.wy.fingerkitchen.entity.Dish;

import java.util.ArrayList;
import java.util.List;

import static com.example.wy.fingerkitchen.database.DishSQLiteHelper.DB_NAME;
import static com.example.wy.fingerkitchen.database.DishSQLiteHelper.DB_VERSION;
import static com.example.wy.fingerkitchen.database.DishSQLiteHelper.TABLE_COLLECT;

/**
 * @author LiLang
 * @date 2019/1/8
 */
public class CollectFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private DishListAdapter mAdapter;
    private DishSQLiteHelper mSQLiteHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, null);
        initView(view);
        return view;    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDataBase();
    }

    @Override
    public void onResume() {
        super.onResume();
        setCollectData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.collect_list_recycler);
        mAdapter = new DishListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new LineItemDecoration());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnItemClickListener(mItemClickListener);
        mAdapter.setOnItemOnLongClickListener(mItemLongClickListener);
    }

    private void initDataBase() {
        mSQLiteHelper = new DishSQLiteHelper(getActivity(), DB_NAME, null, DB_VERSION);
    }

    private void setCollectData() {
        SQLiteDatabase database = mSQLiteHelper.getReadableDatabase();
        List<Dish> collectDish = new ArrayList<>();
        if (database != null) {
            Cursor cursor = database.query(TABLE_COLLECT, null, null, null, null, null, null);
            while(cursor.moveToNext()) {
                Dish dish = new Dish();
                dish.id = cursor.getInt(0);
                List<String> albums = new ArrayList<>();
                albums.add(cursor.getString(1));
                dish.albums = albums;
                dish.title = cursor.getString(2);
                dish.tags = cursor.getString(3);
                collectDish.add(dish);
            }
            cursor.close();
        }
        mAdapter.setDishList(collectDish);
    }

    private DishListAdapter.OnItemClickListener mItemClickListener = new DishListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Dish dish) {
            Intent intent = new Intent(getActivity(), DishDetailActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("Dish", dish);
            startActivity(intent);
        }
    };

    private DishListAdapter.OnLongClickListener mItemLongClickListener = new DishListAdapter.OnLongClickListener() {
        @Override
        public void onItemLongClick(final Dish dish, final int position) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("取消收藏 " + dish.title + "?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
                            database.delete(TABLE_COLLECT, "dish_id = " + dish.id, null);
                            mAdapter.deletaItem(position);

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();

        }
    };


}

