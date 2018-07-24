package com.mylowcarbon.app.bracelet.bind;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemBindBraceletBinding;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.ui.RecyclerView;

/**
 * Created by Orange on 18-4-12.
 * Email:addskya@163.com
 */

class ItemBindBraceletViewHolder extends RecyclerView.ViewHolder {
    private ItemBindBraceletViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemBindBraceletBinding binding = ItemBindBraceletBinding.inflate(inflater, parent, false);
        return new ItemBindBraceletViewHolder(binding.getRoot());
    }

    void bind(@Nullable BleDevices data,
                     @Nullable BindBraceletContract.View view) {
        ItemBindBraceletBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setDevice(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
