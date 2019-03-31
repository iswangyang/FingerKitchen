package com.example.wy.fingerkitchen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.activity.DishListActivity;
import com.example.wy.fingerkitchen.adapter.LeftClassifyRecyclerAdapter;
import com.example.wy.fingerkitchen.adapter.RightClassifyRecyclerAdapter;
import com.example.wy.fingerkitchen.contract.IClassifyContract;
import com.example.wy.fingerkitchen.decoration.StickyItemDecoration;
import com.example.wy.fingerkitchen.entity.ClassifyOne;
import com.example.wy.fingerkitchen.entity.ClassifyTwo;
import com.example.wy.fingerkitchen.presenter.ClassifyPresenterImpl;

import java.util.HashMap;
import java.util.List;

import static com.example.wy.fingerkitchen.activity.DishListActivity.CLASSIFY_ID;

/**
 * @author LiLang
 * @date 2019/1/8
 *
 * 分类Fragment
 */
public class ClassifyFragment extends BaseFragment implements IClassifyContract.IView {

    private static final String TAG = "ClassifyFragment";
    private IClassifyContract.IPresenter mPresenter;
    private View mRootView;
    private View mLoadingView;
    private View mLoadingProgressBar;
    private View mRetryView;
    private RecyclerView mLeftRecycler;
    private LeftClassifyRecyclerAdapter mLeftAdapter;
    private RecyclerView mRightRecycler;
    private RightClassifyRecyclerAdapter mRightAdapter;
    private GridLayoutManager mGridLayoutManager;
    private List<ClassifyOne> mClassifyOneList;
    private List<ClassifyTwo> mClassifyTwoList;
    private StickyItemDecoration mStickyItemDecoration;
    private int mRightLacationIndex;
    private boolean mNeedScroll;

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

        mRootView = inflater.inflate(R.layout.fragment_classify, null);
        initView();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onDataRequestSuccess(List<ClassifyOne> classifyOneList, List<ClassifyTwo> classifyTwoList,
                                     HashMap<String, String> idToGroupMap) {
        mLoadingView.setVisibility(View.GONE);
        mClassifyOneList = classifyOneList;
        mClassifyTwoList = classifyTwoList;
        if (mLeftAdapter != null) {
            mLeftAdapter.setData(classifyOneList);
        }
        mStickyItemDecoration.update(classifyTwoList, idToGroupMap);
        if (mRightAdapter != null) {
            mRightAdapter.setData(classifyTwoList);
        }
    }

    @Override
    public void onDataRequestFailure() {
        mLoadingProgressBar.setVisibility(View.GONE);
        mRetryView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void createPresenter() {
        mPresenter = new ClassifyPresenterImpl(getActivity(), this);
    }

    @Override
    protected void loadData() {
        mPresenter.requestData();
    }

    private void initView() {
        mLeftRecycler = mRootView.findViewById(R.id.left_recycler);
        mLeftAdapter = new LeftClassifyRecyclerAdapter(getActivity());
        mLeftRecycler.setAdapter(mLeftAdapter);
        mLeftRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mLeftAdapter.setOnItemClickListener(mLeftOnItemClickListener);

        mRightRecycler = mRootView.findViewById(R.id.right_recycler);
        mRightAdapter = new RightClassifyRecyclerAdapter(getActivity());
        mRightAdapter.setOnItemClickListener(mRightOnItemClickListener);
        mRightRecycler.setAdapter(mRightAdapter);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRightRecycler.setLayoutManager(mGridLayoutManager);
        mStickyItemDecoration = new StickyItemDecoration();
        mRightRecycler.addItemDecoration(mStickyItemDecoration);
        mRightRecycler.addOnScrollListener(mOnScrollListener);

        mLoadingView = mRootView.findViewById(R.id.loading_view);
        mLoadingProgressBar = mRootView.findViewById(R.id.loadingProgressBar);
        mRetryView = mRootView.findViewById(R.id.loading_info);
        mRetryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.requestData();
            }
        });

    }

    private LeftClassifyRecyclerAdapter.OnItemClickListener mLeftOnItemClickListener =
            new LeftClassifyRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            int pos = 0;
            for (int i = 0; i < position; i++) {
                pos += mClassifyOneList.get(i).list.size();
            }
            mRightLacationIndex = pos;
            int firstVisibleItemPos = mGridLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPos = mGridLayoutManager.findLastVisibleItemPosition();
            if (pos <= firstVisibleItemPos) {
                mRightRecycler.scrollToPosition(pos);
            } else if (pos <= lastVisibleItemPos) {
                int top = mRightRecycler.getChildAt(pos - firstVisibleItemPos).getTop();
                mRightRecycler.scrollBy(0, top);
            } else {
                mNeedScroll = true;
                mRightRecycler.scrollToPosition(pos);
            }
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mNeedScroll) {
                mNeedScroll = false;
                int pos = mRightLacationIndex - mGridLayoutManager.findFirstVisibleItemPosition();
                if (pos < mRightRecycler.getChildCount() && pos >= 0) {
                    int top = mRightRecycler.getChildAt(pos).getTop();
                    mRightRecycler.scrollBy(0, top);
                }
            }
            int firstVisiblePos = mGridLayoutManager.findFirstVisibleItemPosition();
            ClassifyTwo classifyTwo = mClassifyTwoList.get(firstVisiblePos);
            String parentId = classifyTwo.parentId;
            int pos = 0;
            for (int i = 0; i < mClassifyOneList.size(); i++) {
                ClassifyOne classifyOne = mClassifyOneList.get(i);
                if (TextUtils.equals(parentId, classifyOne.parentId)) {
                    pos = i;
                    break;
                }
            }
            mLeftRecycler.scrollToPosition(pos);
            mLeftAdapter.setSelectedPosition(pos);
        }
    };

    private RightClassifyRecyclerAdapter.OnItemClickListener mRightOnItemClickListener =
            new RightClassifyRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String cid) {
            if (TextUtils.isEmpty(cid)) {
                return;
            }
            Intent intent = new Intent(getActivity(), DishListActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(CLASSIFY_ID, Integer.valueOf(cid));
            startActivity(intent);
        }
    };
}
