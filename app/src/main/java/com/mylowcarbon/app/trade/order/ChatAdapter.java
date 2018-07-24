package com.mylowcarbon.app.trade.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemChatBinding;
import com.mylowcarbon.app.databinding.ItemMytradeHeaderBinding;
import com.mylowcarbon.app.trade.order.OrderDetailContract;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<Object> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private OrderDetailContract.View  mView;

    public ChatAdapter(Context context, List<Object> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }
    public void setView(OrderDetailContract.View view) {
        this.mView = view ;
    }

    // 返回View的类别
    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return ITEM_VIEW_TYPE_HEADER;
//        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                ItemMytradeHeaderBinding headerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_mytrade_header, parent, false);
//                headerBinding.setView(mView);

                return new HeadHolder(headerBinding.getRoot());

            default:
                ItemChatBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_chat, parent, false);
                return new ItemHolder(itemBinding.getRoot());
        }



    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof  HeadHolder) {

            ItemMytradeHeaderBinding headerBinding = DataBindingUtil.getBinding(holder.itemView);

        } else {

            ItemChatBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);


        }


    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View view) {
            super(view);

        }

    }
    class HeadHolder  extends RecyclerView.ViewHolder  {
        public HeadHolder (View view) {
            super(view);
        }

    }

}