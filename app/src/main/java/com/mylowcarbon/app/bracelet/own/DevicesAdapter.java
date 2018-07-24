package com.mylowcarbon.app.bracelet.own;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseAdapter;

class DevicesAdapter extends BaseAdapter<Device, DevicesContract.View> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;


    DevicesAdapter(@NonNull LayoutInflater inflater,
                   @Nullable DevicesContract.View view) {
        super(inflater, view);
    }

    @Nullable
    @Override
    protected RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_ITEM: {
                return ItemDeviceViewHolder.newHolder(inflater, parent);
            }
            case ITEM_VIEW_TYPE_FOOTER: {
                return ItemDeviceFooterViewHolder.newHolder(inflater, parent);
            }
            default: {
                throw new IllegalArgumentException("Unknown viewType:" + viewType);
            }
        }
    }

    @Override
    protected void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable Device data,
            @Nullable DevicesContract.View view) {
        if (holder instanceof ItemDeviceViewHolder) {
            ((ItemDeviceViewHolder) holder).bind(data, view);
        } else if (holder instanceof ItemDeviceFooterViewHolder) {
            ((ItemDeviceFooterViewHolder) holder).bind(view);
        }
    }

    // 返回View的类别
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_VIEW_TYPE_FOOTER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    protected int getFooterCount() {
        return 1;
    }
}