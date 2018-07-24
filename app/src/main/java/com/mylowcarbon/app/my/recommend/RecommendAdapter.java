package com.mylowcarbon.app.my.recommend;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemRecommendBinding;
import com.mylowcarbon.app.my.wallet.receipt.ReceiptContract;
import com.mylowcarbon.app.net.response.MyRecommend;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<MyRecommend.MyRecommendItem> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private ReceiptContract.View  mView;

    public RecommendAdapter(Context context, List<MyRecommend.MyRecommendItem> datas) {
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
        ItemRecommendBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_recommend, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemRecommendBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
        MyRecommend.MyRecommendItem item = mDatas.get(position);
        itemBinding.tvName.setText(item.nickname);

        Drawable unOdentityDrawable = mContext.getResources().getDrawable(R.drawable.ic_unidentity_gray);
        unOdentityDrawable.setBounds(0, 0, unOdentityDrawable.getMinimumWidth(), unOdentityDrawable.getMinimumHeight());
        Drawable identityDrawable = mContext.getResources().getDrawable(R.drawable.ic_identity_green);// 找到资源图片
        // 这一步必须要做，否则不会显示。
        identityDrawable.setBounds(0, 0, identityDrawable.getMinimumWidth(), identityDrawable.getMinimumHeight());// 设置图片宽高
        //（－2：用户冻结，-1:异常状态，0：未实名认证，1：实名认证审核中，2：已实名认证,3:拒绝认证）
        switch (position) {
            case -2://
                itemBinding.tvStatus.setText(R.string.txt_user_status_1);
                itemBinding.tvStatus.setCompoundDrawables(identityDrawable, null, null, null);// 设置到控件中
                 break;
            case -1://
                itemBinding.tvStatus.setText(R.string.txt_user_status_2);
                itemBinding.tvStatus.setCompoundDrawables(identityDrawable, null, null, null);// 设置到控件中
                 break;
            case 0://
                itemBinding.tvStatus.setText(R.string.txt_user_status_3);
                itemBinding.tvStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);

                break;
            case 1://
                itemBinding.tvStatus.setText(R.string.txt_user_status_4);
                itemBinding.tvStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);

                break;
            case 2://
                itemBinding.tvStatus.setText(R.string.txt_user_status_5);
                itemBinding.tvStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);

                break;
            case 3://
                itemBinding.tvStatus.setText(R.string.txt_user_status_6);
                itemBinding.tvStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);
                break;


            default:
                itemBinding.tvStatus.setText(R.string.txt_user_status_3);
                itemBinding.tvStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);

                break;
        }

        //1：一级用户，2:二级用户
        if(item.level == -1){
            itemBinding.tvLevel.setText(R.string.txt_user_level_1);
        } else {
            itemBinding.tvLevel.setText(R.string.txt_user_level_2);
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

}