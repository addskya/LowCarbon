package com.mylowcarbon.app.trade.order;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylowcarbon.app.R;

/**
 *
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.HorizontalVh> {

    private Context mContext;


    //时间节点数
    private int nodeNum = 0;

    //当前到达节点
    private int currentNode = 0;

    private int picWidth;
    private int picHeight;
    public TimeLineAdapter(Context context, int nodeNum) {
        this.mContext = context;
        this.nodeNum = nodeNum;
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        picWidth = (width / 4 ) - 20 ;
        picHeight = picWidth + 10;
    }

    @Override
    public HorizontalVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timeline, null, false);
        HorizontalVh vh = new HorizontalVh(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HorizontalVh holder, int position) {
        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(picWidth, picHeight));


        if (position < currentNode) {
            //当前节点之前的全部设为已经经过
            holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
            holder.lineRight.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        }
        switch (position) {
            case 0: //
                holder.lineLeft.setVisibility(View.INVISIBLE);
                holder.point.setImageResource(R.drawable.ic_order_line0_orange);
                 if (position<=currentNode){
                    holder.lineRight.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    holder.tvName.setText("已下单");
                }
                break;
            case 1: //
                if (position<=currentNode){
                    holder.point.setImageResource(R.drawable.ic_order_line1_orange);
                    holder.tvName.setText("已付款");

                    if (position == currentNode) {
                        holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                        holder.lineRight.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    }
                } else {
                    if (currentNode == 0) {
                        holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    }
                    holder.point.setImageResource(R.drawable.ic_order_line1_gray);
                    holder.tvName.setText("待付款");
                }
                break;
            case 2: //
                if (position<=currentNode){
                    holder.point.setImageResource(R.drawable.ic_order_line2_orange);
                    holder.tvName.setText("已收货");
                    if (position == currentNode) {
                        holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                        holder.lineRight.setBackgroundColor(mContext.getResources().getColor(R.color.orange));

                    }

                } else {
                    if (currentNode == 1) {
                        holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    }
                    holder.point.setImageResource(R.drawable.ic_order_line2_gray);
                    holder.tvName.setText("待收货");
                }
                break;
            case 3: //
                if (position<=currentNode){
                    holder.point.setImageResource(R.drawable.ic_order_line3_orange);
                    holder.tvName.setText("已评价");
                    if (position == currentNode) {
                        holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    }
                } else {
                    if (currentNode == 2) {
                        holder.lineLeft.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    }
                    holder.point.setImageResource(R.drawable.ic_order_line3_gray);
                    holder.tvName.setText("待评价");
                }
                holder.lineRight.setVisibility(View.INVISIBLE);

                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return nodeNum;
    }

    /**
     * 设置当前节点
     * @param currentNode
     */
    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
        this.notifyDataSetChanged();
    }

    class HorizontalVh extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private ImageView point;
        private TextView tvName;

        private View lineLeft, lineRight;


        public HorizontalVh(View itemView) {
            super(itemView);
            this.layout =   itemView.findViewById(R.id.layout);
            this.point =   itemView.findViewById(R.id.iv_image);
            this.lineLeft = itemView.findViewById(R.id.view_left);
            this.lineRight = itemView.findViewById(R.id.view_right);
            this.tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
