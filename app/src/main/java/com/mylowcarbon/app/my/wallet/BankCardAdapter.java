package com.mylowcarbon.app.my.wallet;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemBankcardBinding;
import com.mylowcarbon.app.my.wallet.receipt.ReceiptContract;
import com.mylowcarbon.app.net.response.BankType;

import java.util.List;

public class BankCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<BankType> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private ReceiptContract.View mView;
    private int mDefaultId;
    private int selectPositoin;

    public BankCardAdapter(Context context, List<BankType> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setView(ReceiptContract.View view) {
        this.mView = view;
    }

    // 返回View的类别
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_VIEW_TYPE_FOOTER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBankcardBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_bankcard, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ItemBankcardBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
        final BankType item = mDatas.get(position);
        if (item.is_default == 1) {
            mDefaultId = item.id;
            selectPositoin = position;
            itemBinding.rbStaus.setChecked(true);
            itemBinding.rbStaus.setText("默认");
        } else {
            itemBinding.rbStaus.setChecked(false);
            itemBinding.rbStaus.setText("设置");
        }
        itemBinding.tvBankType.setText(item.card_type);
        itemBinding.tvBankNum.setText(item.card_number);
        itemBinding.rbStaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.get(selectPositoin).is_default = 0;
                item.is_default = 1;
                mView.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getDefaultId() {
        return mDefaultId;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View view) {
            super(view);

        }

    }

}