package com.example.wy.fingerkitchen.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.example.wy.fingerkitchen.entity.ClassifyTwo;
import com.example.wy.fingerkitchen.utils.UIUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/11
 */
public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    private List<ClassifyTwo> mDataList;
    private HashMap<String, String> mIdToGroupMap;
    private int mGroupHeight = UIUtils.dpTopx(50);
    private int mTopMargin = UIUtils.dpTopx(10);
    private Paint mPaint;
    private TextPaint mTextPaint;
    private Paint mGroupLinePaint;

    public StickyItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(40);
        mTextPaint.setColor(Color.parseColor("#7BBD9A"));

        mGroupLinePaint = new Paint();
        mGroupLinePaint.setColor(Color.parseColor("#eeeeee"));
        mGroupLinePaint.setStrokeWidth(2);
        mGroupLinePaint.setStyle(Paint.Style.FILL);
    }

    public void update(List<ClassifyTwo> dataList, HashMap<String, String> idToGroupMap) {
        mDataList = dataList;
        mIdToGroupMap = idToGroupMap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDataList == null || mDataList.size() <= 0) {
            return;
        }
        int position = parent.getChildAdapterPosition(view);
        if (isFirstItemInGroup(position)) {
            outRect.top = mGroupHeight;
        } else {
            outRect.top = mTopMargin;
        }
        outRect.bottom = mTopMargin;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        int itemCount = state.getItemCount();
        int left = 0 + parent.getPaddingLeft();
        int right = parent.getRight() - parent.getPaddingRight();
        String preItemGroupId;
        String currentItemGroupId = null;
        for (int i = 0; i < childCount; i++) {
            View currentView = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(currentView);
            preItemGroupId = currentItemGroupId;
            currentItemGroupId = getGroupId(position);
            if (currentItemGroupId == null || TextUtils.equals(preItemGroupId, currentItemGroupId)) {
                continue;
            }
            int viewBottom = currentView.getBottom();
            float top = Math.max(currentView.getTop(), mGroupHeight);
            if (position < itemCount - 1) {
                String nextItemGroupId = getGroupId(position + 1);
                if (!TextUtils.equals(nextItemGroupId, currentItemGroupId) && viewBottom < top) {
                    top = viewBottom;
                }
            }
            c.drawRect(left, top - mGroupHeight, right, top - mTopMargin, mPaint);
            //drawGroupName
            String text = getGroupName(position);
            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            float baseLine = top - (mGroupHeight + mTopMargin - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(text, left + 50, baseLine, mTextPaint);
            c.drawLine(left + 50, top - 2 - mTopMargin,
                    left + 250, top - 2 - mTopMargin, mGroupLinePaint);
        }
    }

    private boolean isFirstItemInGroup(int position) {
        if (mDataList == null || mDataList.size() <= 0) {
            return false;
        }
        if (position == 0) {
            return true;
        }
        String preItemGroupId = mDataList.get(position - 1).parentId;
        String currentItemGroupId = mDataList.get(position).parentId;
        if (position == 1) {
            return TextUtils.equals(preItemGroupId, currentItemGroupId);
        }
        String prePreItemGroupId = mDataList.get(position - 2).parentId;
        return !TextUtils.equals(preItemGroupId, currentItemGroupId) ||
                TextUtils.equals(preItemGroupId, currentItemGroupId)
                && !TextUtils.equals(prePreItemGroupId, currentItemGroupId);
    }

    private String getGroupId(int position) {
        if (mDataList == null || mDataList.size() <= 0) {
            return "";
        }
        return mDataList.get(position).parentId;
    }

    private String getGroupName(int position) {
        if (mIdToGroupMap == null || mDataList == null) {
            return "";
        }
        String itemId = mDataList.get(position).id;
        return mIdToGroupMap.get(itemId);
    }
}
