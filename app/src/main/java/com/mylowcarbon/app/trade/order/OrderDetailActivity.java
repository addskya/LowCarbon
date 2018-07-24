package com.mylowcarbon.app.trade.order;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityOrderdetailBinding;
import com.mylowcarbon.app.jiguang.ChatView;
import com.mylowcarbon.app.jiguang.JMessageUtil;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.complaints.ComplaintsActivity;
import com.mylowcarbon.app.net.response.OrderDetail;
import com.mylowcarbon.app.ui.AppraiseDialog;
import com.mylowcarbon.app.ui.ConfirmDialog;
import com.mylowcarbon.app.ui.customize.MySwipeRefreshLayout;
import com.mylowcarbon.app.ui.customize.SimpleToolbar;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.AmountUtil;
import com.mylowcarbon.app.utils.ToastUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.imui.chatinput.listener.CameraControllerListener;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 订单详情
 */
public class OrderDetailActivity extends ActionBarActivity implements OrderDetailContract.View {
    private static final String TAG = "OrderDetailActivity";
    private OrderDetailContract.Presenter mPresenter;
    private TimeLineAdapter mTimeLineAdapter;
    private ActivityOrderdetailBinding mBinding;
    private RecyclerView mTimeLineRecyclerView;
    private ChatAdapter mChatAdapter;
    private SimpleToolbar mToolBar;
    //    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private OrderDetail mOrderDetail;
    private int mOrderId;
    private boolean mIsBuyer = true;//是否是买家
    private boolean mIsCreate = false;//是否是创建
    private ChatView mChatView;//
    private Activity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_orderdetail);
        mBinding.setView(this);
        mBinding.executePendingBindings();
        mContext = this;
        setOperateOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, ComplaintsActivity.class);
                intent.putExtra(AppConstants.ORDER_NICK_NAME, mOrderDetail != null ? mOrderDetail.nickname : "");
                startActivity(intent);
            }
        });
        initView();
        initData();


    }

    @Override
    protected int getUiTitle() {
        return R.string.title_my_order_detail;
    }

    public void initView() {


        new OrderDetailPresenter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTimeLineRecyclerView = mBinding.rvTimeline;
        mTimeLineAdapter = new TimeLineAdapter(this, 4);
        mTimeLineRecyclerView.setAdapter(mTimeLineAdapter);
        mTimeLineRecyclerView.setLayoutManager(linearLayoutManager);
        mTimeLineAdapter.setCurrentNode(0);

        List dataList = new ArrayList<Object>();

        mChatAdapter = new ChatAdapter(this, dataList);
        mChatAdapter.setView(this);

        swipeRefreshLayout = mBinding.swipeRefreshLayout;
        swipeRefreshLayout.setLoadmoreEnable(false);
        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoading() {

            }

            /**
             * 监听下拉刷新过程中的状态改变
             * @param percent 当前下拉距离的百分比（0-1）
             * @param state 分三种状态{NOT_OVER_TRIGGER_POINT：还未到触发下拉刷新的距离；OVER_TRIGGER_POINT：已经到触发下拉刷新的距离；START：正在下拉刷新}
             */
            @Override
            public void onRefreshPulStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_1));
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_2));
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_3));
                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText(getResources().getString(R.string.txt_shs_load_1));
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText(getResources().getString(R.string.txt_shs_load_2));
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setLoaderViewText(getResources().getString(R.string.txt_shs_load_3));
                        break;
                }
            }
        });
        mChatView = mBinding.chatView;
        mChatView.initModule();
        mChatView.setCameraControllerListener(new CameraControllerListener() {

            @Override
            public void onFullScreenClick() {
                Log.i(TAG, "onFullScreenClick");
                OrderDetailActivity.this.getSupportActionBar().hide();
            }

            @Override
            public void onRecoverScreenClick() {
                Log.i(TAG, "onRecoverScreenClick");
                OrderDetailActivity.this.getSupportActionBar().show();

            }

            @Override
            public void onCloseCameraClick() {
                Log.i(TAG, "onCloseCameraClick");
                OrderDetailActivity.this.getSupportActionBar().show();

            }

            @Override
            public void onSwitchCameraModeClick(boolean isRecordVideoMode) {
                Log.i(TAG, "onSwitchCameraModeClick : " + isRecordVideoMode);

            }
        });

    }

    public void initData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        mOrderId = bundle.getInt(AppConstants.ORDER_ID);
        mIsBuyer = bundle.getBoolean(AppConstants.ORDER_IS_BUYER);
        mIsCreate = bundle.getBoolean(AppConstants.ORDER_IS_CREATE);
        Log.e(TAG, "initData mOrderId: " + mOrderId);
        Log.e(TAG, "initData mIsBuyer: " + mIsBuyer);
        Log.e(TAG, "initData mIsCreate: " + mIsCreate);
        if (mIsBuyer) {
            setOperateText(R.string.txt_complaints_seller);
        } else {
            setOperateText(R.string.txt_complaints_buyer);
        }
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);

    }

    private void getData() {
        mPresenter.getDetailData(mOrderId, mIsBuyer ? 1 : 2);
    }

    private void updateView() {
        mBinding.tvOrderSn.setText("" + mOrderDetail.order_sn);
        mBinding.tvCreateTime.setText(DateUtil.timeStampToStr(mOrderDetail.create_time));
        mBinding.tvNumber.setText("" + mOrderDetail.number);
        mBinding.tvTotalAmount.setText(AmountUtil.getToltalAmountToRMB(mOrderDetail.price, mOrderDetail.number));
        mBinding.tvReceipt.setText(mOrderDetail.pay_type_info);
        mTimeLineAdapter.setCurrentNode(mOrderDetail.order_status - 1);
        mTimeLineAdapter.notifyDataSetChanged();
        //订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价）
        switch (mOrderDetail.order_status) {
            case -1://
                mBinding.llOperation.setVisibility(View.GONE);
                mBinding.llAppraise.setVisibility(View.GONE);
                mBinding.llAppraiseResult.setVisibility(View.GONE);
                break;
            case 0://
                mBinding.llOperation.setVisibility(View.GONE);
                mBinding.llAppraise.setVisibility(View.GONE);
                mBinding.llAppraiseResult.setVisibility(View.GONE);
                break;
            case 1://
                mBinding.llOperation.setVisibility(View.VISIBLE);
                mBinding.llAppraise.setVisibility(View.GONE);
                mBinding.llAppraiseResult.setVisibility(View.GONE);
                if (mIsBuyer) {
                    mBinding.btnMark.setVisibility(View.VISIBLE);
                    mBinding.btnMark.setText(R.string.txt_tag_payed);
                } else {
                    mBinding.btnMark.setVisibility(View.GONE);
                }
                break;
            case 2://
                mBinding.llOperation.setVisibility(View.VISIBLE);
                mBinding.llAppraise.setVisibility(View.GONE);
                mBinding.llAppraiseResult.setVisibility(View.GONE);
                if (mIsBuyer) {
                    mBinding.btnMark.setVisibility(View.GONE);
                } else {
                    mBinding.btnMark.setVisibility(View.VISIBLE);
                    mBinding.btnMark.setText(R.string.txt_tag_Receipt);

                }
                break;
            case 3://
                mBinding.llOperation.setVisibility(View.GONE);
                mBinding.llAppraiseResult.setVisibility(View.GONE);
                mBinding.llAppraise.setVisibility(View.VISIBLE);
                if (mIsBuyer) {
                    mBinding.tvAppraiseResult.setText(R.string.txt_appraise_seller);
                } else {
                    mBinding.tvAppraiseResult.setText(R.string.txt_appraise_buyer);
                }
                break;
            case 4://
                mBinding.llOperation.setVisibility(View.GONE);
                mBinding.llAppraise.setVisibility(View.GONE);
                mBinding.llAppraiseResult.setVisibility(View.VISIBLE);
                if (mOrderDetail.comment_info == null) {
                    return;
                }
                //评价类型（1：好评，2：中评，3：差评）
                if (mOrderDetail.comment_info.comment_type == 1) {
                    mBinding.tvAppraiseResult.setText(R.string.txt_appraise_1);
                } else if (mOrderDetail.comment_info.comment_type == 2) {
                    mBinding.tvAppraiseResult.setText(R.string.txt_appraise_2);
                } else if (mOrderDetail.comment_info.comment_type == 3) {
                    mBinding.tvAppraiseResult.setText(R.string.txt_appraise_3);
                }
                if (!TextUtils.isEmpty(mOrderDetail.comment_info.remark)) {
                    mBinding.tvAppraiseResult.append(" (" + mOrderDetail.comment_info.remark + ")");
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void cancelDeal() {

        ConfirmDialog.intentTo(this, null,
                getString(R.string.text_trade_cancel_tip),
                getString(R.string.text_trade_cancel_continue),
                getString(R.string.text_trade_cancel_sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case BaseDialog.BUTTON_POSITIVE: {
                                //继续交易

                                break;
                            }
                            case BaseDialog.BUTTON_NEGATIVE: {
                                //确定取消
                                showLoadingDialog();
                                mPresenter.updateOrderStatus(mOrderDetail.order_sn, 0);
                                break;
                            }
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JCoreInterface.onPause(this);
        JMessageClient.exitConversation();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        JCoreInterface.onResume(this);
        if (mOrderDetail != null) {
            JMessageClient.enterSingleConversation(mOrderDetail.mobile);

        }
    }

    @Override
    public void mark() {
        showLoadingDialog();
        mPresenter.updateOrderStatus(mOrderDetail.order_sn, mOrderDetail.order_status + 1);

    }

    @Override
    public void appraise() {

        AppraiseDialog.intentTo(this, new AppraiseDialog.AppraiseDialogOnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, int type, String remark) {
                switch (which) {
                    case BaseDialog.BUTTON_POSITIVE: {
                        //提交评价 (1：好评，2：中评，3：差评)
                        showLoadingDialog();
                        mPresenter.comment(mOrderDetail.order_sn, type, remark, mIsBuyer ? 1 : 2);
                        break;
                    }
                    case BaseDialog.BUTTON_NEGATIVE: {
                        //确定取消
                        break;
                    }
                }
            }
        });

    }

    @Override
    public void updateOrderSuc(String msg, int status) {
        dismissLoadingDialog();
        senfSystemNotification(status);
        if (status == 0) {
            finish();
        } else {
            handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH);

        }

    }

    private void senfSystemNotification(int status) {
        String notification = "";
        //订单状态（-1：订单过期，0；取消订单，1：待付款，2：已付款，3：已收货，4：已评价）

        switch (status) {
            case -1://

                break;
            case 0://
                notification = String.format(getResources().getString(R.string.format_chat_sysytem_notification_order_status_0), mOrderDetail.order_sn);

                notification = getResources().getString(R.string.format_chat_sysytem_notification_order_status_0);
                break;
            case 1://
                notification = String.format(getResources().getString(R.string.format_chat_sysytem_notification_order_status_1), mOrderDetail.order_sn);

                break;
            case 2://
                notification = String.format(getResources().getString(R.string.format_chat_sysytem_notification_order_status_2), mOrderDetail.order_sn);

                break;
            case 3://
                notification = String.format(getResources().getString(R.string.format_chat_sysytem_notification_order_status_3), mOrderDetail.order_sn);

                break;
            case 4://
                notification = String.format(getResources().getString(mIsBuyer ? R.string.format_chat_sysytem_notification_order_status_4 : R.string.format_chat_sysytem_notification_order_status_4), mOrderDetail.order_sn);


                break;
            default://

                break;
        }
        mChatView.createSingleCustomMessage(notification);
    }


    @Override
    public void updateOrderFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(OrderDetailActivity.this, msg);
    }

    @Override
    public void getDataSuc(final OrderDetail data) {
        if (mOrderDetail == null) {
            mOrderDetail = data;
//            JMessageClient.deleteSingleConversation(data.mobile);

            if (JMessageUtil.isImLogin) {
                mChatView.init(this, data.mobile);//    18215560956 18680758192
            } else {
                UserInfo userInfo = ModelDao.getInstance().getUserInfo();
                if (userInfo == null) {
                    return;
                }
                JMessageUtil.login(userInfo.getMobile(), new BasicCallback() {
                    @Override
                    public void gotResult(final int status, final String desc) {
                        Log.e("JMessageUtil.login", "status = " + status);

                        if (status == 0) {
                            ToastUtil.showShort(OrderDetailActivity.this, "极光IM登录成功");
                            JMessageUtil.isImLogin = true;
                            mChatView.init(OrderDetailActivity.this, data.mobile);//    18215560956 18680758192
                        } else {
                            JMessageUtil.isImLogin = false;

                        }
                    }
                });
            }

            if (mIsCreate) {
                senfSystemNotification(1);
            }
        }
        mOrderDetail = data;
        handler.sendEmptyMessage(AppConstants.SWIPEREFRESH_REFRESH_FINISH);
        updateView();
    }

    @Override
    public void getDataFail(String msg) {
        swipeRefreshLayout.finishRefresh();
        swipeRefreshLayout.finishLoadmore();
        ToastUtil.showShort(this, msg);
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handleMessage msg.what " + msg.what);

            boolean handled = false;
            switch (msg.what) {
                case AppConstants.SWIPEREFRESH_REFRESH_FINISH:
                    swipeRefreshLayout.finishRefresh();
                    break;
                case AppConstants.SWIPEREFRESH_LOAD_FINISH:
                    swipeRefreshLayout.finishLoadmore();

                    break;
                case AppConstants.SWIPEREFRESH_REFRESH_START_REFRESH:
                    swipeRefreshLayout.startRefresh();
                    swipeRefreshLayout.setRefreshViewText(getResources().getString(R.string.txt_shs_refresh_3));
                    break;
            }

            return handled;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == ChatView.REQUEST_RECORD_VOICE_PERMISSION) {
            if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Permission denied
                Toast.makeText(mContext, "User denied permission, can't use record voice feature.",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ChatView.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Permission denied
                Toast.makeText(mContext, "User denied permission, can't use take photo feature.",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ChatView.REQUEST_PHOTO_PERMISSION) {
            if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Permission denied
                Toast.makeText(mContext, "User denied permission, can't use select photo feature.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


}
