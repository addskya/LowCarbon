package com.mylowcarbon.app.bracelet.bind;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemBindBraceletFooterBinding;
import com.mylowcarbon.app.ui.RecyclerView;

/**
 * Created by Orange on 18-4-12.
 * Email:addskya@163.com
 */

class ItemBindBraceletFooterViewHolder extends RecyclerView.ViewHolder {
    private ItemBindBraceletFooterViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemBindBraceletFooterBinding binding = ItemBindBraceletFooterBinding.inflate(
                inflater, parent, false);
        return new ItemBindBraceletFooterViewHolder(binding.getRoot());
    }

    public void bind(BindBraceletContract.View view) {
        // nothing to to
    }
}
