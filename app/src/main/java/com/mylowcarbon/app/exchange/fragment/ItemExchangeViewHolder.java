package com.mylowcarbon.app.exchange.fragment;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemExchangeBinding;
import com.mylowcarbon.app.exchange.Mining;


/**
 * Created by Orange on 18-4-30.
 * Email:addskya@163.com
 */
class ItemExchangeViewHolder extends RecyclerView.ViewHolder {


    private ItemExchangeViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemExchangeBinding binding = ItemExchangeBinding.inflate(inflater, parent, false);
        return new ItemExchangeViewHolder(binding.getRoot());
    }

    void bind(@Nullable Mining data,
              @Nullable ExchangeContract.View view) {
        ItemExchangeBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setMining(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
