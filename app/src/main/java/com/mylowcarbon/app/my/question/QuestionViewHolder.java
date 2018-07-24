package com.mylowcarbon.app.my.question;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.databinding.ItemQuestionBinding;
import com.mylowcarbon.app.ui.RecyclerView;

/**
 * Created by Orange on 18-4-28.
 * Email:addskya@163.com
 */

class QuestionViewHolder extends RecyclerView.ViewHolder {

    private QuestionViewHolder(View itemView) {
        super(itemView);
    }

    static RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent) {
        ItemQuestionBinding binding = ItemQuestionBinding.inflate(
                inflater, parent, false);
        return new QuestionViewHolder(binding.getRoot());
    }

    void bind(@Nullable Question data,
              @Nullable QuestionContract.View view) {
        ItemQuestionBinding binding = DataBindingUtil.getBinding(itemView);
        if (binding == null) {
            return;
        }
        binding.setQuestion(data);
        binding.setView(view);
        binding.executePendingBindings();
    }
}
