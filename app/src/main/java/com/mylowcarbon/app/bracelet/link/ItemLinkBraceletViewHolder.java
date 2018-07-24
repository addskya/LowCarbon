package com.mylowcarbon.app.bracelet.link;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemLinkBraceletBinding;
import com.mylowcarbon.app.model.BleDevices;
import com.mylowcarbon.app.ui.RecyclerView;

/**
 * Created by Orange on 18-4-12.
 * Email:addskya@163.com
 */

class ItemLinkBraceletViewHolder extends RecyclerView.ViewHolder {

    private ItemLinkBraceletViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemLinkBraceletBinding binding = ItemLinkBraceletBinding.inflate(
                inflater, parent, false);
        return new ItemLinkBraceletViewHolder(binding.getRoot());
    }


    void bind(@Nullable BleDevices data,
              @Nullable LinkBraceletContract.View view) {
        ItemLinkBraceletBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }

        binding.setDevice(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
