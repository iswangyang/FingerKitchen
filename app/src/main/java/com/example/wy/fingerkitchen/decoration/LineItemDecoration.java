package com.example.wy.fingerkitchen.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wy.fingerkitchen.utils.UIUtils;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class LineItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mLinePaint;

    public LineItemDecoration() {
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.parseColor("#eeeeee"));
        mLinePaint.setStrokeWidth(2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = UIUtils.dpTopx(10);
        outRect.bottom  = UIUtils.dpTopx(10);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int itemCount = state.getItemCount();
        int left = 0 + parent.getPaddingLeft();
        int right = parent.getRight() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            int top = view.getTop() - UIUtils.dpTopx(10);
            int bottom = top + UIUtils.dpTopx(1);
            if (position != 0 && position != itemCount - 1) {
                c.drawLine(left, top, right, top, mLinePaint);
            }
        }
    }
}
