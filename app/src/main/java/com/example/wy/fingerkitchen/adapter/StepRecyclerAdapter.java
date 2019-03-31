package com.example.wy.fingerkitchen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.entity.Step;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mDataList = new ArrayList<>();

    public StepRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Step> dataList) {
        if (dataList != null) {
            mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public StepRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dish_detail_step, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepRecyclerAdapter.ViewHolder holder, int position) {
        Step step = mDataList.get(position);
        holder.mStepOrder.setText("步骤" + (position + 1));
        holder.mSimpleDraweeView.setImageURI(step.img);
        holder.mStepContent.setText(step.step);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mStepOrder;
        public SimpleDraweeView mSimpleDraweeView;
        public TextView mStepContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mStepOrder = itemView.findViewById(R.id.tv_step_order);
            mSimpleDraweeView = itemView.findViewById(R.id.step_image);
            mStepContent = itemView.findViewById(R.id.tv_step_content);
        }
    }
}
