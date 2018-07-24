package com.mylowcarbon.app.bracelet.link;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseAdapter;
import com.mylowcarbon.app.model.BleDevices;

class LinkBraceletAdapter extends BaseAdapter<BleDevices, LinkBraceletContract.View> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;

    LinkBraceletAdapter(@NonNull LayoutInflater inflater,
                        @Nullable LinkBraceletContract.View view) {
        super(inflater, view);
    }

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

    @Nullable
    @Override
    protected RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_ITEM: {
                return ItemLinkBraceletViewHolder.newHolder(inflater, parent);
            }
            case ITEM_VIEW_TYPE_FOOTER: {
                return ItemLinkBraceletFooterViewHolder.newHolder(inflater, parent);
            }
            default: {
                throw new IllegalArgumentException("");
            }
        }
    }

    @Override
    protected void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable BleDevices data,
            @Nullable LinkBraceletContract.View view) {
        if (holder instanceof ItemLinkBraceletViewHolder) {
            ((ItemLinkBraceletViewHolder) holder).bind(data, view);
        } else if (holder instanceof ItemLinkBraceletFooterViewHolder) {
            ((ItemLinkBraceletFooterViewHolder) holder).bind(view);
        }
    }
}