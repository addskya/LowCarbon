package com.mylowcarbon.app.bracelet.link;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemLinkBraceletFooterBinding;
import com.mylowcarbon.app.ui.RecyclerView;

/**
 * Created by Orange on 18-4-12.
 * Email:addskya@163.com
 * <p>
 * 添加手环 Footer
 */

class ItemLinkBraceletFooterViewHolder extends RecyclerView.ViewHolder {
    private ItemLinkBraceletFooterViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemLinkBraceletFooterBinding binding = ItemLinkBraceletFooterBinding.inflate(
                inflater, parent, false);
        return new ItemLinkBraceletFooterViewHolder(binding.getRoot());
    }

    public void bind(@Nullable LinkBraceletContract.View view) {
        ItemLinkBraceletFooterBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setView(view);
        binding.executePendingBindings();
    }

}
