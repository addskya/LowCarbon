package com.mylowcarbon.app.my.about;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ItemAboutBinding;
import com.mylowcarbon.app.my.about.AboutContract;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private AboutContract.View mView;

    public AboutAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }
    public void setView(AboutContract.View view) {
        this.mView = view;
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    // 重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAboutBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_about, parent, false);
        return new ItemHolder(itemBinding.getRoot());
    }

    // 填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String url = mDatas.get(position);
        ItemAboutBinding itemBinding = DataBindingUtil.getBinding(holder.itemView);
//        itemBinding.ivImage
         //GlideUtil.getInstance().into(mContext,url,itemBinding.ivImage,R.drawable.ic_header_default);
        Uri uri = Uri.parse(url);
        Log.e("test",url);
        itemBinding.ivImage.setImageURI(uri);
    }


    class ItemHolder extends ViewHolder {
      
        public ItemHolder(View view) {
            super(view);

        }

    }

}