package com.mylowcarbon.app.home.trade;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemTradeBinding;
import com.mylowcarbon.app.net.response.TradeDetail;
import com.mylowcarbon.app.utils.AmountUtil;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<TradeDetail> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private TradeContract.View mView;

    public TradeAdapter(Context context, List<TradeDetail> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }


    // 返回View的类别
    @Override
    public int getItemViewType(int position) {
            return ITEM_VIEW_TYPE_ITEM;

    }
    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTradeBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_trade, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder,  final int position) {
        TradeDetail item = mDatas.get(position);

        ItemTradeBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);

        itemBinding.tvNickName.setText(item.nickname);
        itemBinding.tvPrice.setText(AmountUtil.priceToRMB(item.price));
        itemBinding.tvNum.setText(""+item.number);
        itemBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(item.price,item.number));
        itemBinding.tvTradeNum.setText(String.format(mContext.getResources().getString(R.string.format_trade_num), item.trade_num));
        itemBinding.tvSuccessTradeNum.setText(String.format(mContext.getResources().getString(R.string.format_success_trade_num), item.success_trade_num));
        itemBinding.tvGoodCommentRate.setText(String.format(mContext.getResources().getString(R.string.format_good_comment_rate), item.good_comment_num));
        itemBinding.tvSuccessTradeRate.setText(String.format(mContext.getResources().getString(R.string.format_success_trade_rate), item.success_trade_num));


        itemBinding.btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.onItemClick(position);
            }
        });
    }

    public void setView(TradeContract.View view) {
        this.mView = view ;
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder  {

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