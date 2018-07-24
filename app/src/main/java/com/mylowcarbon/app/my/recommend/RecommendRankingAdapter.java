package com.mylowcarbon.app.my.recommend;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemRecommendrankingBinding;
import com.mylowcarbon.app.my.wallet.receipt.ReceiptContract;
import com.mylowcarbon.app.net.response.RecommendRank;

import java.util.List;

public class RecommendRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<RecommendRank.RecommendRankItem> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private ReceiptContract.View  mView;

    public RecommendRankingAdapter(Context context, List<RecommendRank.RecommendRankItem> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }
    public void setView(ReceiptContract.View view) {
        this.mView = view ;
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
        ItemRecommendrankingBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_recommendranking, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemRecommendrankingBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
        RecommendRank.RecommendRankItem item = mDatas.get(position);
        switch (position) {
            case 0:
                itemBinding.ivRanking.setImageResource(R.drawable.ic_ranking_1);
                itemBinding.tvRanking.setVisibility(View.INVISIBLE);
                break;
            case 1:
                itemBinding.ivRanking.setImageResource(R.drawable.ic_ranking_2);
                itemBinding.tvRanking.setVisibility(View.INVISIBLE);
                break;
            case 2:
                itemBinding.ivRanking.setImageResource(R.drawable.ic_ranking_3);
                itemBinding.tvRanking.setVisibility(View.INVISIBLE);
                break;
            default:
                itemBinding.ivRanking.setVisibility(View.INVISIBLE);
                itemBinding.tvRanking.setVisibility(View.VISIBLE);
                if (position<9){
                    itemBinding.tvRanking.setText("0"+(position+1));

                } else {
                    itemBinding.tvRanking.setText(""+(position+1));

                }
                break;

        }
        itemBinding.civHead.setImageURI(Uri.parse(item.avatar));
        itemBinding.tvName.setText(item.nickname);
        itemBinding.tvSumLevel.setText(String.format(mContext.getResources().getString(R.string.format_sum_level), item.sum_level_1, item.sum_level_2, item.sum_level_3));


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