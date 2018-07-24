package com.mylowcarbon.app.my.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemLinkBraceletFooterBinding;
import com.mylowcarbon.app.databinding.ItemOrderBinding;
import com.mylowcarbon.app.my.order.MyOrdersContract;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<Object> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private MyOrdersContract.View mView;


    public OrderAdapter(Context context, List<Object> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setView(MyOrdersContract.View view) {
        this.mView = view;
    }

    // 返回View的类别
    @Override
    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()) {
//            return ITEM_VIEW_TYPE_FOOTER;
//        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_FOOTER:
                ItemLinkBraceletFooterBinding footerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_link_bracelet_footer, parent, false);
                return new FootHolder(footerBinding.getRoot());

            default:
                ItemOrderBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_order, parent, false);
                return new ItemHolder(itemBinding.getRoot());
        }
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FootHolder) {
            ItemLinkBraceletFooterBinding footerBinding = DataBindingUtil.getBinding(holder.itemView);
            /*footerBinding.llLinkbraceletFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, BindBraceletActivity.class);
                    mContext.startActivity(intent);
                }
            });*/
        } else {
            ItemOrderBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
            if (position == 0) {
                itemBinding.btnRemind.setVisibility(View.VISIBLE);
                itemBinding.btnView.setVisibility(View.GONE);
                itemBinding.btnConfirm.setVisibility(View.GONE);
                itemBinding.btnExchange.setVisibility(View.GONE);
             } else if (position == 1) {
                itemBinding.btnRemind.setVisibility(View.GONE);
                itemBinding.btnView.setVisibility(View.VISIBLE);
                itemBinding.btnConfirm.setVisibility(View.VISIBLE);
                itemBinding.btnExchange.setVisibility(View.GONE);

            } else {
                itemBinding.btnRemind.setVisibility(View.GONE);
                itemBinding.btnView.setVisibility(View.GONE);
                itemBinding.btnConfirm.setVisibility(View.GONE);
                itemBinding.btnExchange.setVisibility(View.VISIBLE);

            }
            itemBinding.btnRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.remind(position);
                }
            });
            itemBinding.btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.view(position);
                }
            });
            itemBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.confirm(position);
                }
            });
            itemBinding.btnExchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.exchange(position);
                }
            });
            itemBinding.llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.onItemClick(position);
                }
            });
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

    class FootHolder extends RecyclerView.ViewHolder {
        public FootHolder(View view) {
            super(view);
        }

    }
}