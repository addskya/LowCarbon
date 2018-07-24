package com.mylowcarbon.app.my.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseAdapter;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 */
class QuestionAdapter extends BaseAdapter<Question, QuestionContract.View> {

    /**
     * create QuestionAdapter object
     *
     * @param inflater the layout inflater
     * @param view     the contract view
     */
    QuestionAdapter(@NonNull LayoutInflater inflater,
                    @Nullable QuestionContract.View view) {
        super(inflater, view);

    }

    @Nullable
    @Override
    protected RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType) {
        return QuestionViewHolder.newHolder(inflater, parent);
    }

    @Override
    protected void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable Question data,
            @Nullable QuestionContract.View view) {
        if (holder instanceof QuestionViewHolder) {
            ((QuestionViewHolder) holder).bind(data, view);
        }
    }
}
