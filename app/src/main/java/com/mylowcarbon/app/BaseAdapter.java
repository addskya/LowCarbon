package com.mylowcarbon.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 18-4-11.
 * Email:addskya@163.com
 * 高度抽象的Adapter类,
 * 使用时只需要传入一个 LayoutInflater,及一个View接口
 */

public abstract class BaseAdapter<D, V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BaseAdapter";

    private final ArrayList<D> mDatas;
    protected V mView;
    private final LayoutInflater mInflater;

    private boolean mAllowRepeated;
    private boolean mInsertToHead;

    public BaseAdapter(@NonNull LayoutInflater inflater,
                       @Nullable V view) {
        mInflater = inflater;
        mView = view;
        mDatas = new ArrayList<D>(1);
        mDatas.clear();
    }

    /**
     * Insert new Data into Adapter
     *
     * @param data the data
     */
    public void addData(D data) {
        addDataWithChanged(data, true);
        onDataChanged();
    }

    /**
     * Insert new Data list into Adapter
     *
     * @param list the new data list
     */
    public void addData(List<D> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (D data : list) {
            addDataWithChanged(data, false);
        }
        notifyDataChanged();
    }

    public void addDataToHead(List<D> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        mDatas.addAll(0, list);
        notifyItemRangeInserted(0, list.size());
    }

    /**
     * @param item item
     * @return whether the datas contain item
     */
    public boolean containItem(D item) {
        return mDatas.contains(item);
    }

    /**
     * Insert new Data into Adapter
     *
     * @param data   the data
     * @param notify whether or not notify update
     */
    private void addDataWithChanged(D data, boolean notify) {
        if (data == null) {
            return;
        }
        int insertIndex = mInsertToHead ? 0 : mDatas.size();
        if (mAllowRepeated) {
            mDatas.add(insertIndex, data);
        } else if (!mDatas.contains(data)) {
            mDatas.add(insertIndex, data);
        }
        if (notify) {
            insertIndex += getHeaderCount();
            notifyItemInserted(insertIndex);
        }
    }

    /**
     * Insert new Data into Adapter
     *
     * @param index the insert index
     * @param data  the data
     */
    public void insertData(int index, D data) {
        boolean contain = mDatas.contains(data);
        if (contain && !mAllowRepeated) {
            return;
        }
        int insertMinIndex = Math.max(0, index);

        int insertMaxIndex = mDatas.size();
        insertMaxIndex = Math.max(0, insertMaxIndex);

        index = Math.min(insertMinIndex, insertMaxIndex);
        mDatas.add(index, data);
        notifyItemInserted(index);
    }

    /**
     * Remove Data from Adapter
     *
     * @param data the data removed
     * @return whether or not success
     */
    public boolean removeData(D data) {
        return removeDataWithChanged(data, true);
    }

    /**
     * Remove Data from Adapter
     *
     * @param data   the data removed
     * @param notify whether or not notify to update ui
     * @return true remove success or otherwise
     */
    private boolean removeDataWithChanged(D data, boolean notify) {
        if (data == null) {
            return true;
        }
        int position = mDatas.indexOf(data);
        if (position == -1) {
            return true;
        }
        position += getHeaderCount();
        boolean removed = mDatas.remove(data);
        if (removed && notify) {
            notifyItemRemoved(position);
        }
        onDataChanged();
        return removed;
    }

    /**
     * Remove Data from Adapter
     *
     * @param list the Remove data list
     * @return whether or not success
     */
    public boolean removeData(List<D> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        boolean removed = false;
        for (D data : list) {
            removed = removeDataWithChanged(data, false) || removed;
        }
        notifyDataChanged();
        return removed;
    }

    /**
     * Clear All the Data
     */
    public void clear() {
        int itemCount = mDatas.size();
        mDatas.clear();
        int fromPosition = getHeaderCount();
        notifyItemRangeRemoved(fromPosition, itemCount);
        onDataChanged();
    }

    /**
     * Replace the data
     *
     * @param data the new data
     */
    public void replaceData(D data) {
        int index = mDatas.indexOf(data);
        if (index != -1) {
            mDatas.set(index, data);
            notifyItemChanged(index + getHeaderCount());
        }
    }

    /**
     * Set New Data,Clear the old data first.
     *
     * @param list the new data list
     */
    public void setData(List<D> list) {
        clear();
        addData(list);
    }

    /**
     * Add Or Replace the data
     *
     * @param list    the data list
     * @param replace whether or not replace the data exist
     */
    public void addOrSetData(List<D> list, boolean replace) {
        if (replace) {
            setData(list);
        } else {
            addData(list);
        }
    }

    /**
     * get the origin data list.
     *
     * @return the origin data list.
     */
    @NonNull
    public ArrayList<D> getDatas() {
        return mDatas;
    }

    /**
     * get the specified data
     *
     * @param data the query data
     * @return the origin data in list
     */
    @Nullable
    public D getData(D data) {
        int index = mDatas.indexOf(data);
        if (index != -1) {
            return mDatas.get(index);
        }
        return null;
    }

    /**
     * Set whether or not allow data repeated
     *
     * @param allowRepeated whether or not allow repeated
     */
    public void setAllowRepeated(boolean allowRepeated) {
        mAllowRepeated = allowRepeated;
    }

    /**
     * Set whether or not insert new data into list header
     *
     * @param insertToHead whether or not insert into the first position
     */
    public void setInsertToHead(boolean insertToHead) {
        mInsertToHead = insertToHead;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(
            @Nullable ViewGroup parent,
            int viewType) {
        return newHolder(mInflater, parent, viewType);
    }

    /**
     * create the ItemView Holder for this
     *
     * @param inflater the layout inflater
     * @param parent   the parent viewGroup
     * @param viewType the item viewType
     * @return the item ViewHolder
     */
    @Nullable
    protected abstract RecyclerView.ViewHolder newHolder(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup parent,
            int viewType);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        D data;
        final int headerCount = getHeaderCount();
        final int dataSize = getDatas().size();
        final int footerCount = getFooterCount();

        if (position < headerCount) {
            data = null;
        } else if (position >= headerCount && position <= headerCount + dataSize - 1) {
            int dataIndex = position - headerCount;
            data = getDatas().get(dataIndex);
        } else {
            data = null;
        }
        bindViewHolder(holder, data, mView);
    }

    protected abstract void bindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @Nullable D data,
            @Nullable V view);

    @Override
    public int getItemCount() {
        return getDatas().size() + getHeaderCount() + getFooterCount();
    }

    /**
     * How many Header in AdapterView
     *
     * @return the header count in RecyclerView
     */
    public int getHeaderCount() {
        return 0;
    }

    /**
     * How many Data Item in AdapterView
     *
     * @return the data list size
     */
    public final int getDataCount() {
        return mDatas.size();
    }

    /**
     * How many Footer in AdapterView
     *
     * @return the footer count in RecyclerView
     */
    protected int getFooterCount() {
        return 0;
    }

    /**
     * Hook Api,Call Back when the list Changed
     */
    protected void onDataChanged() {

    }

    /**
     * Send Notify when data list Changed
     */
    private void notifyDataChanged() {
        notifyDataSetChanged();
        onDataChanged();
    }
}