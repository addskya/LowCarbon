package com.mylowcarbon.app.bracelet.own;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemDeviceBinding;

/**
 * Created by Orange on 18-4-25.
 * Email:addskya@163.com
 */

class ItemDeviceViewHolder extends RecyclerView.ViewHolder {

    private ItemDeviceViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemDeviceBinding binding = ItemDeviceBinding.inflate(
                inflater, parent, false);
        return new ItemDeviceViewHolder(binding.getRoot());
    }

    void bind(@Nullable Device data,
              @Nullable DevicesContract.View view) {
        ItemDeviceBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setDevice(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
