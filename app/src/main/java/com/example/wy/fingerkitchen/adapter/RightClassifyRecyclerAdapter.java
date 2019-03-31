package com.example.wy.fingerkitchen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.entity.ClassifyTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/1/10
 */
public class RightClassifyRecyclerAdapter extends RecyclerView.Adapter<RightClassifyRecyclerAdapter.ViewHolder> {

    private Activity mActivity;
    private List<ClassifyTwo> mDataList;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String cid);
    }

    public RightClassifyRecyclerAdapter(Activity activity) {
        mActivity = activity;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setData(List<ClassifyTwo> dataList) {
        mDataList = dataList;
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.right_recycler_content_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataList == null || mDataList.get(position) == null) {
            return;
        }
        ClassifyTwo classifyTwo = mDataList.get(position);
        if (TextUtils.isEmpty(classifyTwo.name)) {
            holder.mNameText.setVisibility(View.GONE);
        } else {
            holder.mNameText.setVisibility(View.VISIBLE);
            holder.mNameText.setText(classifyTwo.name);
        }
        bindClickEvent(holder, classifyTwo);
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameText;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameText = itemView.findViewById(R.id.content_name);
        }
    }

    private void bindClickEvent(ViewHolder holder, final ClassifyTwo classifyTwo) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null && classifyTwo != null) {
                    mItemClickListener.onItemClick(classifyTwo.id);
                }
            }
        });
    }
}
