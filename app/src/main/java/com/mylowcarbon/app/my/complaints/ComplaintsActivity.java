package com.mylowcarbon.app.my.complaints;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.baoyz.actionsheet.ActionSheet;
import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.MyBarBrowserActivity;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityComplaintsBinding;
import com.mylowcarbon.app.net.response.About;
import com.mylowcarbon.app.net.response.Complain;
import com.mylowcarbon.app.oss.Config;
import com.mylowcarbon.app.oss.OssUtil;
import com.mylowcarbon.app.ui.OkDialog;
import com.mylowcarbon.app.utils.PhotoUtil;
import com.mylowcarbon.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 投诉
 */
public class ComplaintsActivity extends ActionBarActivity implements ComplaintsContract.View, ActionSheet.ActionSheetListener, PhotoUtil.OnPhotoResultListener {
    private static final String TAG = "ComplaintsActivity";
    private ComplaintsContract.Presenter mPresenter;
    private ActivityComplaintsBinding mBinding;
    private ActionSheet.Builder mBuilder;
    private String fontPath, backPath, holdPath;
    private List<String> pathList;
    private List<String> sucPathList;//上传成功路径列表

    private Complain mComplain;
    private String mNickName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_complaints);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        setOperateText(R.string.title_complaints_need_know);
        setOperateOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplaintsActivity.this, MyBarBrowserActivity.class);
                intent.putExtra(AppConstants.URL, AppConstants.URL_COMPLAINTS);
                startActivity(intent);
            }
        });
        new ComplaintsPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_complaints;
    }

    public void initView() {
        setTheme(R.style.ActionSheetStyleiOS7);
        mBuilder = ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("拍照", "从相册中选取")
                .setCancelableOnTouchOutside(true)
                .setListener(this);
    }

    public void initData() {

        pathList = new ArrayList<String>();
        sucPathList = new ArrayList<String>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mNickName = bundle.getString(AppConstants.ORDER_NICK_NAME);
            mBinding.etName.setText(mNickName);
        }
        showLoadingDialog();
        mPresenter.getComplainData();
    }

    @Override
    public void onGetDataSuc(Complain complain) {
        Log.e(TAG, "onGetDataSuc   " + complain.toString());
        dismissLoadingDialog();
        mComplain = complain;
        mBinding.tvQq.setText(String.format(getResources().getString(R.string.format_contact_qq), complain.contact.qq));
        mBinding.tvWechat.setText(String.format(getResources().getString(R.string.format_contact_wechat), complain.contact.wechat));
        mBinding.tvWeibo.setText(String.format(getResources().getString(R.string.format_contact_email), complain.contact.email));

        if(!mComplain.complain.isEmpty()){
            for(Complain.Reason reason : mComplain.complain){
                RadioButton tempButton = new RadioButton(this);
//                tempButton.setBackgroundResource(R.drawable.selector_rg);   // 设置RadioButton的背景图片
                tempButton.setButtonDrawable(R.drawable.selector_rg);           // 设置按钮的样式
                tempButton.setPadding(15, 10, 0, 10);                 // 设置文字距离按钮四周的距离
                tempButton.setTextColor(getResources().getColor(R.color.text_black2));
                tempButton.setText(reason.reason);
                tempButton.setId(reason.id);
                 mBinding.rgReason.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
             }

        }
    }

    @Override
    public void onDataFail(String msg) {
        ToastUtil.showShort(this, msg);
        dismissLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(ComplaintsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private int ivPositon;

    @Override
    public void onViewClick(int position) {
        ivPositon = position;
        switch (position) {
            case 0://
                mBuilder.show();
                break;

            case 1://
                mBuilder.show();
                break;
            case 2://
                mBuilder.show();
                break;
            case 3://上传证件照片要求


                CharSequence name = mBinding.etName.getText();
                if (TextUtils.isEmpty(name)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_complaints_user),
                            getString(R.string.text_sure));
                    return;
                }

                if (!TextUtils.isEmpty(fontPath)) {
                    pathList.add(fontPath);
                }
                if (!TextUtils.isEmpty(backPath)) {
                    pathList.add(backPath);

                }
                if (!TextUtils.isEmpty(holdPath)) {
                    pathList.add(holdPath);

                }
                if (!pathList.isEmpty() && OssUtil.getInstance().getOssService() != null) {
                    OssUtil.getInstance().getOssService().uploadList(pathList, handler);
                }



                break;


            default:
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            boolean handled = false;
            switch (msg.what) {
                case Config.UPLOAD_START:
                    showLoadingDialog();
                    return true;
                case Config.UPLOAD_LIST_END:
                    dismissLoadingDialog();
                    Log.e(TAG, "UPLOAD_LIST_END: " + sucPathList.toString());

                    if (!sucPathList.isEmpty() && sucPathList.size() == pathList.size()) {
                        ToastUtil.showShort(ComplaintsActivity.this, R.string.text_upload_suc);

                        mPresenter.addComplain(mBinding.etName.getText() ,""+mBinding.rgReason.getCheckedRadioButtonId(), sucPathList);

                    } else {
                        ToastUtil.showShort(ComplaintsActivity.this, R.string.text_upload_fail);
                    }
                    return true;
                case Config.UPLOAD_LIST_SUC:

                    final String url = (String) msg.obj;
                    Log.e(TAG, "UPLOAD_LIST_SUC: " + url);
                    if (!sucPathList.contains(url)) {
                        sucPathList.add(url);
                    }
                    return true;
                case Config.UPLOAD_FAIL:
                    Log.e(TAG, "UPLOAD_LIST_SUC: ");

                    dismissLoadingDialog();
                    return true;


            }

            return handled;
        }
    });


    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        switch (index) {
            case 0://拍照
                PhotoUtil.photograph(this);
                break;
            case 1://从相册中选取
                PhotoUtil.selectPictureFromAlbum(this);
                break;

            default:
                break;
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancle) {

    }

    @Override
    public void onAuthSuc(String msg) {
        ToastUtil.showShort(ComplaintsActivity.this, msg);
        finish();

    }

    @Override
    public void onAuthFail(String msg) {
        ToastUtil.showShort(ComplaintsActivity.this, msg);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtil.NONE:
                return;
            case PhotoUtil.PHOTOGRAPH:
            case PhotoUtil.PHOTOZOOM:
            case PhotoUtil.PHOTORESOULT:
                PhotoUtil.onActivityResult(ComplaintsActivity.this, this, PhotoUtil.TYPE_PHOTO, requestCode, resultCode, data);
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onPhotoResult(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        Bitmap bitmap = PhotoUtil.convertToBitmap(path, PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH);

        if (bitmap != null) {
            switch (ivPositon) {
                case 0://
                    fontPath = path;
                    mBinding.ivPhoto1.setImageBitmap(bitmap);
                    break;

                case 1://
                    backPath = path;
                    mBinding.ivPhoto2.setImageBitmap(bitmap);
                    break;
                case 2://
                    holdPath = path;
                    mBinding.ivPhoto3.setImageBitmap(bitmap);
                    break;
                case 3://提交

                    break;

                default:
                    break;
            }
        }
    }
}
