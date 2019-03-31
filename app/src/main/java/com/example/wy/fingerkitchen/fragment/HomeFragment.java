package com.example.wy.fingerkitchen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.activity.DishDetailActivity;
import com.example.wy.fingerkitchen.activity.DishListActivity;
import com.example.wy.fingerkitchen.contract.IHomeContract;
import com.example.wy.fingerkitchen.custom.SearchView;
import com.example.wy.fingerkitchen.presenter.HomePresenterImpl;
import com.example.wy.fingerkitchen.utils.UIUtils;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;

import static com.example.wy.fingerkitchen.activity.DishListActivity.CLASSIFY_ID;
import static com.example.wy.fingerkitchen.activity.DishListActivity.DISH_NAME;

/**
 * @author LiLang
 * @date 2019/1/8
 *
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment implements IHomeContract.IView, View.OnClickListener {

    private ViewPager mViewPager;
    private List<ImageView> mImageViewList;
    private int[] mImageIds = new int[] {R.drawable.song_shu_fish, R.drawable.dong_po_rou,
            R.drawable.shi_guo_ban_fan, R.drawable.cong_bao_rou, R.drawable.qing_zhen_fish};
    private int[] mRecIds = new int[] {24170, 42036, 10080, 68163, 20853};
    private int[] mHotRecClassifyIds = new int[] {37, 38, 40, 241, 243, 242};
    private IHomeContract.IPresenter mPresenter;
    private SearchView mSearchView;
    private View mBreakLayout;
    private View mLunchLayout;
    private View mDinnerLayout;
    private View mPartyLayot;
    private View mSingleLayout;
    private View mAoyeLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    private void initView(View rooView) {
        mViewPager = rooView.findViewById(R.id.id_viewpager);
        mSearchView = rooView.findViewById(R.id.searchView);
        mBreakLayout = rooView.findViewById(R.id.layout_breakfast);
        mLunchLayout = rooView.findViewById(R.id.layout_lunch);
        mDinnerLayout = rooView.findViewById(R.id.layout_dinner);
        mPartyLayot = rooView.findViewById(R.id.layout_party);
        mSingleLayout = rooView.findViewById(R.id.layout_single);
        mAoyeLayout = rooView.findViewById(R.id.layout_aoye);
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViewList.get(position));
                return mImageViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViewList.get(position));
            }

            @Override
            public int getCount() {
                return mImageIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        mViewPager.setPageTransformer(true,
                new ScaleInTransformer());

        mSearchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(DISH_NAME, string);
                startActivity(intent);
            }
        });
        mBreakLayout.setOnClickListener(this);
        mLunchLayout.setOnClickListener(this);
        mDinnerLayout.setOnClickListener(this);
        mPartyLayot.setOnClickListener(this);
        mSingleLayout.setOnClickListener(this);
        mAoyeLayout.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager.setCurrentItem(2);
        createPresenter();
    }

    private void initData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            int imageId = mImageIds[i];
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imageId);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), DishDetailActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra("dish_id", mRecIds[finalI]);
                    startActivity(intent);
                }
            });
            mImageViewList.add(imageView);

        }
    }

    @Override
    protected void createPresenter() {
        mPresenter = new HomePresenterImpl(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int classifyId = 0;
        switch (view.getId()) {
            case R.id.layout_breakfast:
                classifyId = mHotRecClassifyIds[0];
                break;
            case R.id.layout_lunch:
                classifyId = mHotRecClassifyIds[1];
                break;
            case R.id.layout_dinner:
                classifyId = mHotRecClassifyIds[2];
                break;
            case R.id.layout_party:
                classifyId = mHotRecClassifyIds[3];
                break;
            case R.id.layout_single:
                classifyId = mHotRecClassifyIds[4];
                break;
            case R.id.layout_aoye:
                classifyId = mHotRecClassifyIds[5];
                break;
            default:
                break;
        }

        Intent intent = new Intent(getActivity(), DishListActivity.class);
        intent.putExtra(CLASSIFY_ID, classifyId);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
