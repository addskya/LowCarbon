package com.mylowcarbon.app.my.verify;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 18-4-21.
 * Email:addskya@163.com
 */
class ProblemAdapter extends BaseAdapter {

    private List<Problem> mData;
    private LayoutInflater mLayoutInflater;

    ProblemAdapter(@NonNull LayoutInflater inflater) {
        mLayoutInflater = inflater;
        mData = new ArrayList<>();
        mData.clear();
    }

    void setData(List<Problem> data) {
        mData.clear();
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Problem getItem(int position) {
        return mData != null ? mData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View view = mLayoutInflater.inflate(
                    android.R.layout.simple_list_item_1, parent, false);
            ViewHolder holder = new ViewHolder();
            TextView textView = view.findViewById(android.R.id.text1);
            holder.holder = textView;
            view.setTag(holder);
            convertView = view;
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        Problem problem = getItem(position);
        holder.holder.setText(problem.getContent());

        return convertView;
    }

    private final class ViewHolder {
        TextView holder;
    }
}
