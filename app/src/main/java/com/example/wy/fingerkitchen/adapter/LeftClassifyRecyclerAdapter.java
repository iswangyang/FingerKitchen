package com.example.wy.fingerkitchen.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.entity.ClassifyOne;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public class LeftClassifyRecyclerAdapter extends RecyclerView.Adapter<LeftClassifyRecyclerAdapter.ViewHolder> {

    private Activity mActivity;
    private List<ClassifyOne> mDataList;
    private OnItemClickListener mItemClickListener;
    private int mSelectedPostion;

    public LeftClassifyRecyclerAdapter(Activity activity) {
        mActivity = activity;
    }

    public void setData(List<ClassifyOne> dataList) {
        mDataList = dataList;
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        mSelectedPostion = position;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.left_recycler_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataList == null || mDataList.get(position) == null) {
            return;
        }
        ClassifyOne classifyOne = mDataList.get(position);
        if (!TextUtils.isEmpty(classifyOne.name)) {
            holder.mNameText.setText(classifyOne.name);
            if (mSelectedPostion == position) {
                holder.mSelectedBar.setVisibility(View.VISIBLE);
                holder.mNameText.setTextColor(Color.parseColor("#7BBD9A"));
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                holder.mSelectedBar.setVisibility(View.GONE);
                holder.mNameText.setTextColor(Color.parseColor("#777777"));
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        bindClickEvent(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private View mSelectedBar;
        private TextView mNameText;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameText = itemView.findViewById(R.id.content_name);
            mSelectedBar = itemView.findViewById(R.id.selected_bar);
        }
    }

    private void bindClickEvent(ViewHolder viewHolder, final int position) {
        if (viewHolder == null) {
            return;
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }
}
