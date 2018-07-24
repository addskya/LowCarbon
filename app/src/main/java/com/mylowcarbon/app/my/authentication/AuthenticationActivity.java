package com.mylowcarbon.app.my.authentication;

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
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.MyBarBrowserActivity;
import com.mylowcarbon.app.databinding.ActivityAuthenticationBinding;
import com.mylowcarbon.app.my.complaints.ComplaintsActivity;
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
 * 身份认证
 */
public class AuthenticationActivity extends ActionBarActivity implements AuthenticationContract.View ,ActionSheet.ActionSheetListener,PhotoUtil.OnPhotoResultListener {
    private static final String TAG = "ComplaintsActivity";
    private AuthenticationContract.Presenter mPresenter;
    private ActivityAuthenticationBinding mBinding;
    private ActionSheet.Builder mBuilder;
    private String fontPath,backPath,holdPath;
    private List<String> pathList;
    private List<String> sucPathList;//上传成功路径列表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        mBinding.setView(this);
        mBinding.executePendingBindings();


        new AuthenticationPresenter(this);
        initView();
        initData();
     }
    @Override
    protected int getUiTitle() {
        return R.string.title_authentication;
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

        pathList =  new ArrayList<String>();
        sucPathList =  new ArrayList<String>();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(AuthenticationContract.Presenter presenter) {
        mPresenter = presenter;
    }
    private int ivPositon;

    @Override
    public void onViewClick(int position) {
        ivPositon = position;
        switch (position) {
            case 0://身份证正面

                mBuilder.show();
                break;

            case 1://身份证背面

                mBuilder.show();
                break;
            case 2://手持身份证

                mBuilder.show();
                 break;
            case 3://上传证件照片要求
                Intent intent6 = new Intent(this, MyBarBrowserActivity.class);
                intent6.putExtra("url","http://www.baidu.com");
                startActivity(intent6);
                break;
            case 4://上传证件
                CharSequence name = mBinding.etName.getText();
                CharSequence id = mBinding.etId.getText();
                if (TextUtils.isEmpty(name)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_name),
                            getString(R.string.text_sure));
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    OkDialog.intentTo(this,
                            getString(R.string.hint_id),
                            getString(R.string.text_sure));
                    return;
                }
                if (TextUtils.isEmpty(fontPath)){
                    OkDialog.intentTo(this,
                            getString(R.string.tip_upload_id_photo_front),
                            getString(R.string.text_sure));
                    return;
                }
                if (TextUtils.isEmpty(backPath)){
                    OkDialog.intentTo(this,
                            getString(R.string.tip_upload_id_photo_back),
                            getString(R.string.text_sure));
                    return;
                }
                if (TextUtils.isEmpty(holdPath)){
                    OkDialog.intentTo(this,
                            getString(R.string.tip_upload_id_photo_hold),
                            getString(R.string.text_sure));
                    return;
                }
                pathList.add(fontPath);
                pathList.add(backPath);
                pathList.add(holdPath);

                if (OssUtil.getInstance().getOssService()!=null){
                    OssUtil.getInstance().getOssService().uploadList(pathList,handler);
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

                    if(!sucPathList.isEmpty() &&  sucPathList.size()==pathList.size()){
                        ToastUtil.showShort(AuthenticationActivity.this,R.string.text_upload_suc);

                        mPresenter.identityAuth(mBinding.etName.getText() ,mBinding.etId.getText(), sucPathList);

                     } else {
                        ToastUtil.showShort(AuthenticationActivity.this,R.string.text_upload_fail);
                    }
                    return true;
                case Config.UPLOAD_LIST_SUC:

                    final String url = (String) msg.obj;
                    Log.e(TAG, "UPLOAD_LIST_SUC: " + url);
                    if (!sucPathList.contains(url)){
                        sucPathList.add(url);
                    }
                    return true;
                case Config.UPLOAD_FAIL:
                    Log.e(TAG, "UPLOAD_LIST_SUC: " );

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
    public void onAuthSuc(String msg){
        ToastUtil.showShort(AuthenticationActivity.this,msg);
        finish();

    }
    @Override
    public void onAuthFail(String msg){
        ToastUtil.showShort(AuthenticationActivity.this,msg );

    }

    private String path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtil.NONE:
                return;
            case PhotoUtil.PHOTOGRAPH:
            case PhotoUtil.PHOTOZOOM:
            case PhotoUtil.PHOTORESOULT:
                PhotoUtil.onActivityResult(AuthenticationActivity.this,this,PhotoUtil.TYPE_ID, requestCode, resultCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPhotoResult(String path) {

        if (TextUtils.isEmpty(path)){
            return;
        }
        Bitmap bitmap = PhotoUtil.convertToBitmap(path,PhotoUtil.ID_PICTURE_WIDTH, PhotoUtil.ID_PICTURE_HEIGHT);

        if(bitmap != null){
            switch (ivPositon) {
                case 0://身份证正面
                    fontPath = path;
                    mBinding.ivPhotoFront.setImageBitmap(bitmap);
                    break;

                case 1://身份证背面
                    backPath = path;
                    mBinding.ivPhotoBack.setImageBitmap(bitmap);
                    break;
                case 2://手持身份证
                    holdPath = path;
                    mBinding.ivPhoto.setImageBitmap(bitmap);
                    break;

                default:
                    break;
            }
        }
    }
}
