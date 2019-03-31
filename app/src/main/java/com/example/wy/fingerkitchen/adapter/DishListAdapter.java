package com.example.wy.fingerkitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wy.fingerkitchen.R;
import com.example.wy.fingerkitchen.entity.Dish;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class DishListAdapter extends RecyclerView.Adapter<DishListAdapter.ViewHolder> {

    private Context mContext;
    private List<Dish> mDishList = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private OnLongClickListener mLongItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Dish dish);
    }

    public interface OnLongClickListener {
        void onItemLongClick(Dish dish, int position);
    }

    public DishListAdapter(Context context) {
        mContext = context;
    }

    public void deletaItem(int position) {
        if (mDishList != null && mDishList.size() > position) {
            mDishList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void setDishList(List<Dish> dishList) {
        if (dishList != null) {
            mDishList = dishList;
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemOnLongClickListener(OnLongClickListener longClickListener) {
        mLongItemClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.dish_list_recycler_item, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dish dish =  mDishList.get(position);
        if (dish.albums != null && dish.albums.size() > 0) {
            holder.mDishImage.setImageURI(dish.albums.get(0));
        }
        if (!TextUtils.isEmpty(dish.title)) {
            holder.mDishNameText.setText(dish.title);
        }
        if (!TextUtils.isEmpty(dish.tags)) {
            holder.mDishInfoText.setText(dish.tags);
        }
        bindClickEvent(holder, dish, position);
    }

    @Override
    public int getItemCount() {
        return mDishList.size();
    }

    private void bindClickEvent(ViewHolder viewHolder, final Dish dish, final int position) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(dish);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mLongItemClickListener != null) {
                    mLongItemClickListener.onItemLongClick(dish, position);
                }
                return false;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mDishImage;
        private TextView mDishNameText;
        private TextView mDishInfoText;

        public ViewHolder(View itemView) {
            super(itemView);
            mDishImage = itemView.findViewById(R.id.dish_image);
            mDishNameText = itemView.findViewById(R.id.dish_name_text);
            mDishInfoText = itemView.findViewById(R.id.dish_info_text);
        }
    }
}
