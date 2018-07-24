package com.mylowcarbon.app.jiguang;

import android.view.View;
import android.widget.TextView;

import com.mylowcarbon.app.R;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.BaseMessageViewHolder;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.messages.MsgListAdapter;

public class MyCustomerViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE> implements MsgListAdapter.DefaultMessageViewHolder {

    private TextView mMsgTv;
//    private RoundTextView mDateTv;
//    private TextView mDisplayNameTv;
//    private ImageButton mResendIb;
//    private ProgressBar mSendingPb;
    private boolean mIsSender;

    public MyCustomerViewHolder(View itemView, boolean isSender) {
        super(itemView);
        this.mIsSender = isSender;
        mMsgTv = (TextView) itemView.findViewById(R.id.tv_msg_sys);
//        mDateTv = (RoundTextView) itemView.findViewById(R.id.aurora_tv_msgitem_date);
//        if (isSender) {
//            mDisplayNameTv = (TextView) itemView.findViewById(R.id.aurora_tv_msgitem_sender_display_name);
//        } else {
//            mDisplayNameTv = (TextView) itemView.findViewById(R.id.aurora_tv_msgitem_receiver_display_name);
//        }
//        mResendIb = (ImageButton) itemView.findViewById(R.id.aurora_ib_msgitem_resend);
//        mSendingPb = (ProgressBar) itemView.findViewById(R.id.aurora_pb_msgitem_sending);
    }



    @Override
    public void onBind(final MESSAGE message) {
        mMsgTv.setText(message.getText());
 //        String timeString = message.getTimeString();
//        mDateTv.setVisibility(View.VISIBLE);
//        if (timeString != null && !TextUtils.isEmpty(timeString)) {
//            mDateTv.setText(timeString);
//        } else {
//            mDateTv.setVisibility(View.GONE);
//        }
//        boolean isAvatarExists = message.getFromUser().getAvatarFilePath() != null
//                && !message.getFromUser().getAvatarFilePath().isEmpty();
//
//        if (mDisplayNameTv.getVisibility() == View.VISIBLE) {
//            mDisplayNameTv.setText(message.getFromUser().getDisplayName());
//        }
//        if (mIsSender) {
//            switch (message.getMessageStatus()) {
//                case SEND_GOING:
//                    mSendingPb.setVisibility(View.VISIBLE);
//                    mResendIb.setVisibility(View.GONE);
//                    Log.i("TxtViewHolder", "sending message");
//                    break;
//                case SEND_FAILED:
//                    mSendingPb.setVisibility(View.GONE);
//                    Log.i("TxtViewHolder", "send message failed");
//                    mResendIb.setVisibility(View.VISIBLE);
//                    mResendIb.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mMsgStatusViewClickListener != null) {
//                                mMsgStatusViewClickListener.onStatusViewClick(message);
//                            }
//                        }
//                    });
//                    break;
//                case SEND_SUCCEED:
//                    mSendingPb.setVisibility(View.GONE);
//                    mResendIb.setVisibility(View.GONE);
//                    Log.i("TxtViewHolder", "send message succeed");
//                    break;
//            }
//        }




    }

    @Override
    public void applyStyle(MessageListStyle style) {
//        mMsgTv.setMaxWidth((int) (style.getWindowWidth() * style.getBubbleMaxWidth()));
//        mMsgTv.setLineSpacing(style.getLineSpacingExtra(), style.getLineSpacingMultiplier());
//        if (mIsSender) {
//            mMsgTv.setBackground(style.getSendBubbleDrawable());
//            mMsgTv.setTextColor(style.getSendBubbleTextColor());
//            mMsgTv.setTextSize(style.getSendBubbleTextSize());
//            mMsgTv.setPadding(style.getSendBubblePaddingLeft(),
//                    style.getSendBubblePaddingTop(),
//                    style.getSendBubblePaddingRight(),
//                    style.getSendBubblePaddingBottom());
//            if (style.getSendingProgressDrawable() != null) {
//                mSendingPb.setProgressDrawable(style.getSendingProgressDrawable());
//            }
//            if (style.getSendingIndeterminateDrawable() != null) {
//                mSendingPb.setIndeterminateDrawable(style.getSendingIndeterminateDrawable());
//            }
//            if (style.getShowSenderDisplayName()) {
//                mDisplayNameTv.setVisibility(View.VISIBLE);
//            } else {
//                mDisplayNameTv.setVisibility(View.GONE);
//            }
//        } else {
//            mMsgTv.setBackground(style.getReceiveBubbleDrawable());
//            mMsgTv.setTextColor(style.getReceiveBubbleTextColor());
//            mMsgTv.setTextSize(style.getReceiveBubbleTextSize());
//            mMsgTv.setPadding(style.getReceiveBubblePaddingLeft(),
//                    style.getReceiveBubblePaddingTop(),
//                    style.getReceiveBubblePaddingRight(),
//                    style.getReceiveBubblePaddingBottom());
//            if (style.getShowReceiverDisplayName()) {
//                mDisplayNameTv.setVisibility(View.VISIBLE);
//            } else {
//                mDisplayNameTv.setVisibility(View.GONE);
//            }
//        }
//        mDisplayNameTv.setTextSize(style.getDisplayNameTextSize());
//        mDisplayNameTv.setTextColor(style.getDisplayNameTextColor());
//        mDisplayNameTv.setPadding(style.getDisplayNamePaddingLeft(), style.getDisplayNamePaddingTop(),
//                style.getDisplayNamePaddingRight(), style.getDisplayNamePaddingBottom());
//        mDateTv.setTextSize(style.getDateTextSize());
//        mDateTv.setTextColor(style.getDateTextColor());
//        mDateTv.setPadding(style.getDatePaddingLeft(), style.getDatePaddingTop(),
//                style.getDatePaddingRight(), style.getDatePaddingBottom());
//        mDateTv.setBgCornerRadius(style.getDateBgCornerRadius());
//        mDateTv.setBgColor(style.getDateBgColor());
//
//        android.view.ViewGroup.LayoutParams layoutParams = mAvatarIv.getLayoutParams();
//        layoutParams.width = style.getAvatarWidth();
//        layoutParams.height = style.getAvatarHeight();

    }

    public TextView getMsgTextView() {
        return mMsgTv;
    }


}