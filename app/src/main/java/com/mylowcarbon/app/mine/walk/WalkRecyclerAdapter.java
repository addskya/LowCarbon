package com.mylowcarbon.app.mine.walk;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemWalkBinding;

import java.util.List;

public class WalkRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public WalkRecyclerAdapter(Context context, List<Object> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.item_walk, parent, false);
//        MyViewHolder holder = new MyViewHolder(view);
//        return holder;
        ItemWalkBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_walk, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Object obj = mDatas.get(position);
         ItemWalkBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
        switch (position) {
            case 0: //
                itemBinding.llLayout1.setVisibility(View.VISIBLE);
                itemBinding.llLayout2.setVisibility(View.GONE);
                itemBinding.ivImage.setImageResource(R.drawable.ic_walk_light);
                itemBinding.tvCount.setText("4900步");
                itemBinding.tvDes.setText("今日步数");
                break;
            case 1: //
                itemBinding.llLayout1.setVisibility(View.VISIBLE);
                itemBinding.llLayout2.setVisibility(View.GONE);
                itemBinding.ivImage.setImageResource(R.drawable.ic_cal_light);
                itemBinding.tvCount.setText("50千卡");
                itemBinding.tvDes.setText("卡路里");
                break;
            case 2: //
                itemBinding.llLayout1.setVisibility(View.VISIBLE);
                itemBinding.llLayout2.setVisibility(View.GONE);
                itemBinding.ivImage.setImageResource(R.drawable.ic_energy_light);
                itemBinding.tvCount.setText("2000");
                itemBinding.tvDes.setText("能力值");
                break;
            case 3: //
                itemBinding.llLayout1.setVisibility(View.GONE);
                itemBinding.llLayout2.setVisibility(View.VISIBLE);
                itemBinding.ivImage.setImageResource(R.drawable.ic_heart_light);
//                itemBinding.tvData.setVisibility(View.GONE);
                itemBinding.tvData.setText("90");
                itemBinding.tvName.setText("心率");
                break;
            case 4: //
                itemBinding.llLayout1.setVisibility(View.GONE);
                itemBinding.llLayout2.setVisibility(View.VISIBLE);
                itemBinding.ivImage.setImageResource(R.drawable.ic_blood_light);
//                itemBinding.tvData.setVisibility(View.GONE);
                itemBinding.tvData.setText("100");
                itemBinding.tvName.setText("血压");

                break;
            case 5: //
                itemBinding.llLayout1.setVisibility(View.GONE);
                itemBinding.llLayout2.setVisibility(View.VISIBLE);
                itemBinding.ivImage.setImageResource(R.drawable.ic_sleep_light);
//                itemBinding.tvData.setVisibility(View.GONE);
                itemBinding.tvData.setText("5小时");
                itemBinding.tvName.setText("睡眠");

                break;

            default:
                break;
        }
    }


    class ItemHolder extends ViewHolder {
      
        public ItemHolder(View view) {
            super(view);

        }

    }

}