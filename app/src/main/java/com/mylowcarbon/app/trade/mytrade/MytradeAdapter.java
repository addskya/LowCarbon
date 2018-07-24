package com.mylowcarbon.app.trade.mytrade;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemMytradeBinding;
import com.mylowcarbon.app.net.response.MyTradeDetail;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.AmountUtil;

import java.util.List;

public class MytradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
     private List<MyTradeDetail.MyTradeDetailItem> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private MyTradeFragmentContract.View  mView;
    private int  mType;//0:卖出 1：买入 2：已完成

    public MytradeAdapter(Context context, List<MyTradeDetail.MyTradeDetailItem> datas , int mType) {
        this.mContext = context;
        this.mDatas = datas;
        this.mType = mType;
        inflater = LayoutInflater.from(mContext);
    }
    public void setView(MyTradeFragmentContract.View view) {
        this.mView = view ;
    }

    // 返回View的类别
    @Override
    public int getItemViewType(int position) {

        return ITEM_VIEW_TYPE_ITEM;
    }

    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMytradeBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_mytrade, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemMytradeBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
        MyTradeDetail.MyTradeDetailItem item = mDatas.get(position);
        switch (mType) {
            case MyTradeFragment.MYTRADE_TYPE_SELL:
                itemBinding.tvTradeRate.setVisibility(View.GONE);
                itemBinding.tvObj.setVisibility(View.GONE);
                itemBinding.btnOp.setVisibility(View.VISIBLE);
                itemBinding.tvStatus.setVisibility(View.GONE);

                itemBinding.tvNickName.setText(DateUtil.timeStampToStr2(item.start_time));
                itemBinding.tvPrice.setText(AmountUtil.priceToRMB(item.price));
                itemBinding.tvNumber.setText(""+item.number);
                itemBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(item.price,item.number));
                if(item.type==1){//（1：测单，2：子单列表）
                    itemBinding.btnOp.setText(R.string.txt_cancel_order);
                    itemBinding.btnOp.setTextColor(mContext.getResources().getColor(R.color.orange));
                    itemBinding.btnOp.setBackgroundResource(R.drawable.bg_rect_rounded_orange4);
                } else {
                    itemBinding.btnOp.setText(R.string.txt_child_list);
                    itemBinding.btnOp.setTextColor(mContext.getResources().getColor(R.color.white));
                    itemBinding.btnOp.setBackgroundResource(R.drawable.bg_rect_rounded_orange3);

                }


                break;
            case MyTradeFragment.MYTRADE_TYPE_BUY:
                itemBinding.tvTradeRate.setVisibility(View.VISIBLE);
                itemBinding.tvObj.setVisibility(View.GONE);
                itemBinding.btnOp.setVisibility(View.VISIBLE);
                itemBinding.tvStatus.setVisibility(View.GONE);

                itemBinding.tvNickName.setText(item.nickname);
                itemBinding.tvTradeRate.setText("成交量:"+item.success_trade_num);
                itemBinding.tvPrice.setText(AmountUtil.priceToRMB(item.price));
                itemBinding.tvNumber.setText(""+item.number);
                itemBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(item.price,item.number));
                //-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价
                if(item.order_status==-1){
                    itemBinding.btnOp.setVisibility(View.GONE);
                    itemBinding.tvStatus.setVisibility(View.VISIBLE);
                    itemBinding.tvStatus.setText(R.string.txt_order_status_minus_1);
                } else if(item.order_status==0){
                    itemBinding.btnOp.setVisibility(View.GONE);
                    itemBinding.tvStatus.setVisibility(View.VISIBLE);
                    itemBinding.tvStatus.setText(R.string.txt_order_status_response_0);
                }else  if(item.order_status==1){
                    itemBinding.btnOp.setText(R.string.txt_order_status_response_1);
                } else if(item.order_status==2){
                    itemBinding.btnOp.setVisibility(View.GONE);
                    itemBinding.tvStatus.setVisibility(View.VISIBLE);
                    itemBinding.tvStatus.setText(R.string.txt_order_status_response_2);
                }else if(item.order_status==3){
                    itemBinding.btnOp.setText(R.string.txt_order_status_response_3);
                } else if (item.order_status == 4) {
                    itemBinding.btnOp.setVisibility(View.GONE);
                    itemBinding.tvStatus.setVisibility(View.VISIBLE);
                    itemBinding.tvStatus.setText(R.string.txt_order_status_response_4);
                }
                break;
            case MyTradeFragment.MYTRADE_TYPE_FINISH:
                itemBinding.tvTradeRate.setVisibility(View.GONE);
                itemBinding.tvObj.setVisibility(View.VISIBLE);
                itemBinding.btnOp.setVisibility(View.GONE);
                itemBinding.tvStatus.setVisibility(View.GONE);

                itemBinding.tvNickName.setText(item.type==1?R.string.txt_complete_trade_type1:R.string.txt_complete_trade_type2);
                itemBinding.tvPrice.setText(AmountUtil.priceToRMB(item.price));
                itemBinding.tvNumber.setText(""+item.number);
                itemBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(item.price,item.number));
                itemBinding.tvObj.setText(item.nickname);
                break;

            default:

        }
        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.onItemClick(position);
            }
        });
        itemBinding.btnOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.onItemViewClick(position);
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


}