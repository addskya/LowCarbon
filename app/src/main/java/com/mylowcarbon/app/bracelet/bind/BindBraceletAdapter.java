package com.mylowcarbon.app.bracelet.bind;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseAdapter;
import com.mylowcarbon.app.model.BleDevices;

class BindBraceletAdapter extends BaseAdapter<BleDevices, BindBraceletContract.View> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;

    BindBraceletAdapter(@NonNull LayoutInflater inflater,
                        @Nullable BindBraceletContract.View view) {
        super(inflater, view);
    }

    // 返回View的类别
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_VIEW_TYPE_FOOTER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getHeaderCount() {
        return 1;
    }

    @Nullable
    @Override
    protected RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_ITEM: {
                return ItemBindBraceletViewHolder.newHolder(inflater, parent);
            }
            case ITEM_VIEW_TYPE_FOOTER: {
                return ItemBindBraceletFooterViewHolder.newHolder(inflater, parent);
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    protected void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable BleDevices data,
            @Nullable BindBraceletContract.View view) {
        if (holder instanceof ItemBindBraceletViewHolder) {
            ((ItemBindBraceletViewHolder) holder).bind(data, view);
        } else if (holder instanceof ItemBindBraceletFooterViewHolder) {
            ((ItemBindBraceletFooterViewHolder) holder).bind(view);
        }
    }

}