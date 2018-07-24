package com.mylowcarbon.app.forget.question;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 18-4-23.
 * Email:addskya@163.com
 */

class QuestionAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private final List<Question> mData;

    QuestionAdapter(@NonNull LayoutInflater inflater) {
        mInflater = inflater;
        mData = new ArrayList<>(1);
        mData.clear();
    }

    void addData(@Nullable List<Question> data) {
        if (data == null) {
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    void setData(@Nullable List<Question> data) {
        mData.clear();
        addData(data);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Question getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        ((TextView) convertView).setText(getItem(position).getContent());
        return convertView;
    }
}
