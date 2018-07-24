package com.mylowcarbon.app.exchange.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseAdapter;
import com.mylowcarbon.app.exchange.Mining;

class ExchangeAdapter extends BaseAdapter<Mining, ExchangeContract.View> {


    ExchangeAdapter(@NonNull LayoutInflater inflater,
                    @Nullable ExchangeContract.View view) {
        super(inflater, view);
    }

    @Nullable
    @Override
    protected RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType) {
        return ItemExchangeViewHolder.newHolder(inflater, parent);
    }

    @Override
    protected void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable Mining data,
            @Nullable ExchangeContract.View view) {
        if (holder instanceof ItemExchangeViewHolder) {
            ((ItemExchangeViewHolder) holder).bind(data, view);
        }
    }

}