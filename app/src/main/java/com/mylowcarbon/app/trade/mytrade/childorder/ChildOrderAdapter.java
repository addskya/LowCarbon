package com.mylowcarbon.app.trade.mytrade.childorder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemChildorderBinding;
import com.mylowcarbon.app.databinding.ItemChildorderHeaderBinding;
import com.mylowcarbon.app.net.response.OrderDetail;
import com.mylowcarbon.app.utils.AmountUtil;

import java.util.List;

public class ChildOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<OrderDetail> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private ChildOrderContract.View mView;

    public ChildOrderAdapter(Context context, List<OrderDetail> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setView(ChildOrderContract.View view) {
        this.mView = view;
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
                ItemChildorderHeaderBinding headerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_childorder_header, parent, false);
                headerBinding.setView(mView);

                return new HeadHolder(headerBinding.getRoot());

            default:
                ItemChildorderBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_childorder, parent, false);
                return new ItemHolder(itemBinding.getRoot());
        }


    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


            ItemChildorderBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
            OrderDetail item = mDatas.get(position);
            itemBinding.tvPrice.setText(AmountUtil.priceToRMB(item.price));
            itemBinding.tvNumber.setText("" + item.number);
            itemBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(item.price, item.number));
            itemBinding.tvObj.setText(item.nickname);
            //订单状态（订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价））
            if(item.order_status==-1){
                itemBinding.btnOp.setVisibility(View.GONE);
                itemBinding.tvStatus.setVisibility(View.VISIBLE);
                itemBinding.tvStatus.setText(R.string.txt_order_status_minus_1);
            } else if (item.order_status == 0) {
                itemBinding.btnOp.setVisibility(View.GONE);
                itemBinding.tvStatus.setVisibility(View.VISIBLE);
                itemBinding.tvStatus.setText(R.string.txt_order_status_response_0);
            } else if (item.order_status == 1) {
                itemBinding.btnOp.setVisibility(View.GONE);
                itemBinding.tvStatus.setVisibility(View.VISIBLE);
                itemBinding.tvStatus.setText(R.string.txt_order_status_1);
            } else if (item.order_status == 2) {

                itemBinding.btnOp.setText(R.string.txt_order_status_response_5);
            } else if (item.order_status == 3) {
                itemBinding.btnOp.setText(R.string.txt_order_status_response_3);
            } else if (item.order_status == 4) {
                itemBinding.btnOp.setVisibility(View.GONE);
                itemBinding.tvStatus.setVisibility(View.VISIBLE);
                itemBinding.tvStatus.setText(R.string.txt_order_status_response_4);
            }
            itemBinding.llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.onItemClick(position);
                }
            });
            itemBinding.btnOp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.onItemClick(position);
                }
            });




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

    class HeadHolder extends RecyclerView.ViewHolder {
        public HeadHolder(View view) {
            super(view);
        }

    }

}