package com.mylowcarbon.app.my.wallet.receipt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityReceiptBinding;
import com.mylowcarbon.app.my.complaints.ComplaintsActivity;
import com.mylowcarbon.app.my.wallet.SpacesItemDecoration;
import com.mylowcarbon.app.my.wallet.bankcard.AddBankCardActivity;
import com.mylowcarbon.app.my.wallet.BankCardAdapter;
import com.mylowcarbon.app.net.response.BankType;
import com.mylowcarbon.app.net.response.Receipt;
import com.mylowcarbon.app.oss.Config;
import com.mylowcarbon.app.oss.OssUtil;
import com.mylowcarbon.app.ui.OkDialog;
import com.mylowcarbon.app.utils.PhotoUtil;
import com.mylowcarbon.app.utils.ToastUtil;
import com.mylowcarbon.app.utils.UriPathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 收款账号信息
 */
public class ReceiptActivity extends ActionBarActivity implements ReceiptContract.View, ActionSheet.ActionSheetListener ,PhotoUtil.OnPhotoResultListener{
    private static final String TAG = "ReceiptActivity";
    private final int REQUEST_CODE_ADD_BANK = 1;

    private ReceiptContract.Presenter mPresenter;
    private ActivityReceiptBinding mBinding;
    private BankCardAdapter mAdapter;
    private List<BankType> mDatas;
    private ActionSheet.Builder mBuilder;
    private String path;//文件上传路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_receipt);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new ReceiptPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_receipt;
    }

    public void initView() {
        mDatas = new ArrayList<BankType>();

        mAdapter = new BankCardAdapter(this, mDatas);
        mAdapter.setView(this);
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvContent.addItemDecoration(new SpacesItemDecoration(20));
        // 设置Item增加、移除动画
        mBinding.rvContent.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvContent.setAdapter(mAdapter);

        setTheme(R.style.ActionSheetStyleiOS7);
        mBuilder = ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("拍照", "从相册中选取")
                .setCancelableOnTouchOutside(true)
                .setListener(this);
    }

    public void initData() {
        mPresenter.getReceiptInfo();

    }

    private Receipt mReceipt;

    @Override
    public void onGetReceiptInfoSuc(Receipt data) {
        Log.e(TAG, "onGetReceiptInfoSuc  :  "+data);

        dismissLoadingDialog();
        if(data == null){
            return;
        }

        mReceipt = data;
        mDatas.clear();
        mDatas.addAll(mReceipt.cards);
        mAdapter.notifyDataSetChanged();
        if(!TextUtils.isEmpty(mReceipt.alipay_account)){
            mBinding.etAlipay.setText(mReceipt.alipay_account);
        }
        if(!TextUtils.isEmpty(mReceipt.wechat_code)){
            Log.e(TAG, "onGetReceiptInfoSuc  setImageURI 00000:  ");

            Uri uri = Uri.parse(mReceipt.wechat_code);
//            Uri uri = Uri.parse("http://img.my.csdn.net/uploads/201407/26/1406383214_7794.jpg");
            mBinding.ivWeichatQr.setImageURI(uri);
        }
        if(mReceipt.pay_type == 1){//1：支付宝，2：微信，3：银行卡
             mBinding.rbReceiptAlipay.setChecked(true);
        } else  if(mReceipt.pay_type == 2){
             mBinding.rbReceiptWechat.setChecked(true);
        }  else if(mReceipt.pay_type == 3){
             mBinding.rbReceiptBankcard.setChecked(true);
        }
        if(mReceipt.show_account == 0){//0：否，1：是
            mBinding.rbDaNo.setChecked(true);
        } else  if(mReceipt.pay_type == 1){
            mBinding.rbDaYes.setChecked(true);
        }
    }

    @Override
    public void onGetReceiptInfoFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ReceiptActivity.this, msg);

    }

    @Override
    public void onModifySuc(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ReceiptActivity.this, msg);
        finish();
    }

    @Override
    public void onModifyFail(String msg) {
        dismissLoadingDialog();
        ToastUtil.showShort(ReceiptActivity.this, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(ReceiptContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            boolean handled = false;
            switch (msg.what) {

                case Config.UPLOAD_SUC:
                    final String url = (String) msg.obj;
                    Log.e(TAG, "UPLOAD_SUC: " + url);
                    modifyPayInfo(url);
                    return true;
                case Config.UPLOAD_FAIL:
                    Log.e(TAG, "UPLOAD_FAIL: ");

                    dismissLoadingDialog();
                    return true;


            }

            return handled;
        }
    });

    @Override
    public void onItemClick(int position) {
        BankType item = mDatas.get(position);
        if(item!=null){
            mDefaultId = item.id ;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://上传微信二维码

                mBuilder.show();
                break;
            case 1://添加银行卡
                 Intent intent0 = new Intent(this, AddBankCardActivity.class);
                startActivityForResult(intent0,REQUEST_CODE_ADD_BANK);

                break;
            case 2://确定
                CharSequence alipay = mBinding.etAlipay.getText();
                if (TextUtils.isEmpty(alipay)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_alipay),
                            getString(R.string.text_sure));
                    return;
                }
                if (!TextUtils.isEmpty(path) && OssUtil.getInstance().getOssService() != null) {
                    showLoadingDialog();
                    OssUtil.getInstance().getOssService().asyncPutImage(path, handler);
                } else if (mReceipt != null) {
                    showLoadingDialog();
                    modifyPayInfo(mReceipt.wechat_code);
                }

                break;


            default:
                break;
        }
    }
    int mDefaultId = 0;
    private void modifyPayInfo(String path) {
        CharSequence alipayAccount = mBinding.etAlipay.getText();
        int pay_type = 0;
        int cardId = mAdapter.getDefaultId();
        if (mReceipt!=null){
            pay_type = mReceipt.pay_type;
        }

        if (mDefaultId!=0){
            cardId = mDefaultId;
        }

        switch (mBinding.rgPayType.getCheckedRadioButtonId()) {

            case R.id.rb_receipt_alipay://
                pay_type = 1 ;
                break;
            case R.id.rb_receipt_wechat://
                pay_type = 2 ;
                break;
            case R.id.rb_receipt_bankcard://
                pay_type = 3 ;
                break;
            default:
                break;
        }
        int show_account = mBinding.rgDisplayaccount.getCheckedRadioButtonId() == R.id.rb_da_yes ? 1 : 0;//0：否，1：是

        mPresenter.modifyPayInfo(alipayAccount, path, cardId, pay_type, show_account);
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理选择银行卡类型
         * 添加成功后重新获取数据
         */
        if (requestCode == REQUEST_CODE_ADD_BANK && resultCode == RESULT_OK) {
            mPresenter.getReceiptInfo();
            return;
        }
        switch (requestCode) {
            case PhotoUtil.NONE:
                return;
            case PhotoUtil.PHOTOGRAPH:
            case PhotoUtil.PHOTOZOOM:
            case PhotoUtil.PHOTORESOULT:
                PhotoUtil.onActivityResult(ReceiptActivity.this,this,PhotoUtil.TYPE_PHOTO, requestCode, resultCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPhotoResult(String path) {
        if (TextUtils.isEmpty(path)){
            return;
        }
        this.path = path;
        Bitmap bitmap = PhotoUtil.convertToBitmap(path, PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH);

        if (bitmap != null) {
            mBinding.ivWeichatQr.setImageBitmap(bitmap);
//                Uri uri = Uri.parse(path);
//                mBinding.ivWeichatQr.setImageURI(uri);

        }
    }

}
