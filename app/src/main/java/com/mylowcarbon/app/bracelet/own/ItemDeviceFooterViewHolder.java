package com.mylowcarbon.app.bracelet.own;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemDeviceFooterBinding;

/**
 * Created by Orange on 18-4-25.
 * Email:addskya@163.com
 */

class ItemDeviceFooterViewHolder extends RecyclerView.ViewHolder {

    private ItemDeviceFooterViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemDeviceFooterBinding binding = ItemDeviceFooterBinding.inflate(
                inflater, parent, false);
        return new ItemDeviceFooterViewHolder(binding.getRoot());
    }

    void bind(@Nullable DevicesContract.View view) {
        ItemDeviceFooterBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setView(view);
    }
}
