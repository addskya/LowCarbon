package com.mylowcarbon.app.my.settings;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;

import com.baoyz.actionsheet.ActionSheet;
import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.browser.JsActionBarActivity;
import com.mylowcarbon.app.databinding.ActivityPersonnelSettingBinding;
import com.mylowcarbon.app.home.MainActivity;
import com.mylowcarbon.app.jiguang.JMessageUtil;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.email.BindEmailActivity;
import com.mylowcarbon.app.my.nickname.EditNicknameActivity;
import com.mylowcarbon.app.my.password.deal.CheckCodeResultActivity;
import com.mylowcarbon.app.my.password.login.EditLoginPwdActivity;
import com.mylowcarbon.app.my.question.QuestionActivity;
import com.mylowcarbon.app.my.wallet.receipt.ReceiptActivity;
import com.mylowcarbon.app.oss.Config;
import com.mylowcarbon.app.oss.OssUtil;
import com.mylowcarbon.app.register.gender.GenderActivity;
import com.mylowcarbon.app.register.height.HeightActivity;
import com.mylowcarbon.app.register.weight.WeightActivity;
import com.mylowcarbon.app.ui.ConfirmDialog;
import com.mylowcarbon.app.utils.LogUtil;
import com.mylowcarbon.app.utils.PhotoUtil;

/**
 * 个人信息设置
 */
public class PersonalSettingsActivity extends JsActionBarActivity
        implements PersonalSettingsContract.View , ActionSheet.ActionSheetListener ,PhotoUtil.OnPhotoResultListener{

    private static final String TAG = "PersonalSettings";

    private static final int REQUEST_CODE_NICKNAME = 0x62;
    private static final int REQUEST_CODE_GENDER = 0x64;
    private static final int REQUEST_CODE_HEIGHT = 0x63;
    private static final int REQUEST_CODE_WEIGHT = 0x65;
    private static final int REQUEST_CODE_PASSWORD_LOGIN = 0x66;
    private static final int REQUEST_CODE_PASSWORD_DEAL = 0x67;
    private static final int REQUEST_CODE_PASSWORD_QUESTION = 0x68;

    private PersonalSettingsContract.Presenter mPresenter;
    private ActivityPersonnelSettingBinding mBinding;
    private final int REQUEST_CODE_EDIT = 1;
    private ActionSheet.Builder mBuilder;
    private String mPath;//文件上传路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_personnel_setting);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new PersonalSettingsPresenter(this);

        // 第一次读取UserInfo数据
        mPresenter.loadUserInfo();
    }

    @Override
    protected int getUiTitle() {
        return R.string.text_personnel_settings;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(PersonalSettingsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 1:// avatar

                if(mBuilder==null){
                    setTheme(R.style.ActionSheetStyleiOS7);
                    mBuilder = ActionSheet.createBuilder(this, getSupportFragmentManager())
                            .setCancelButtonTitle("取消")
                            .setOtherButtonTitles("拍照", "从相册中选取")
                            .setCancelableOnTouchOutside(true)
                            .setListener(this);
                }
                mBuilder.show();
                break;
            case 2: { //修改昵称
                EditNicknameActivity.intentTo(
                        this, mBinding.getUserInfo(), REQUEST_CODE_NICKNAME);
                break;
            }
            case 3: { //绑定邮箱
                Intent intent3 = new Intent(this, BindEmailActivity.class);
                startActivity(intent3);
                break;
            }
            case 4: { // 性别
                GenderActivity.intentTo(
                        this, mBinding.getUserInfo(), REQUEST_CODE_GENDER);
                break;
            }
            case 5: { // 身高
                HeightActivity.intentTo(
                        this, mBinding.getUserInfo(), REQUEST_CODE_HEIGHT);
                break;
            }
            case 6: { //体重
                WeightActivity.intentTo(
                        this, mBinding.getUserInfo(), REQUEST_CODE_WEIGHT);
                break;
            }
            case 7: { //修改登录密码
                EditLoginPwdActivity.intentTo(this);
                break;
            }
            case 8: { //修改交易密码
                CheckCodeResultActivity.intentTo(this);
                break;
            }
            case 9: { //密保问题
                QuestionActivity.intentTo(this);
                break;
            }
            case 10://退出登录
                ConfirmDialog.intentTo(this,
                        getString(R.string.text_logout),
                        getString(R.string.warn_logout),
                        getString(R.string.text_sure),
                        getString(R.string.text_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE: {
                                        //退出极光登录
                                        JMessageUtil.logout();
                                        //移除钱包
                                        UserInfo userInfo = ModelDao.getInstance().getUserInfo();
                                        if (userInfo == null) {
                                            return;
                                        }
                                        removeWallet(userInfo.getMobile(),userInfo.getPay_pwd(),userInfo.getWallet_address(),new ValueCallback<String>(){

                                            @Override
                                            public void onReceiveValue(String value) {
                                                LogUtil.e(TAG, "**********removeWallet  onReceiveValue: "+value);

                                            }
                                        });
                                        // 注销登录状态,清除登录数据
                                        mPresenter.logout();

                                        break;
                                    }
                                }
                            }
                        });
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoadUserInfoSuccess(@Nullable UserInfo userInfo) {
        mBinding.setUserInfo(userInfo);
        mBinding.executePendingBindings();
    }

    @Override
    public void onLogoutSuccess() {


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) {
            nm.cancelAll();
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CODE_NICKNAME://刷新nickname
                if(resultCode == Activity.RESULT_OK){
                    setResult(RESULT_OK);
                    return;
                }
            case PhotoUtil.NONE:
                return;
            case PhotoUtil.PHOTOGRAPH:
            case PhotoUtil.PHOTOZOOM:
            case PhotoUtil.PHOTORESOULT:
                PhotoUtil.onActivityResult(PersonalSettingsActivity.this,this,PhotoUtil.TYPE_PHOTO, requestCode, resultCode, data);
                break;
        }
    }


    @Override
    public void onPhotoResult(String path) {
        if (TextUtils.isEmpty(path)){
            return;
        }
        this.mPath = path;

        if (OssUtil.getInstance().getOssService() != null) {
            showLoadingDialog();
            OssUtil.getInstance().getOssService().asyncPutImage(path, handler);
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

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

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            boolean handled = false;
            switch (msg.what) {

                case Config.UPLOAD_SUC:
                    final String url = (String) msg.obj;
                    Log.e(TAG, "UPLOAD_SUC: " + url);
                    //TODO 上传到服务器
                    //TODO 修改成功后调用极光方法修改极光头像 JMessageUtil.updateUserAvatar(path);
                    dismissLoadingDialog();
                    mPresenter.modifyAvatar(url);

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
    public void onModifyAvatarStart() {
        showLoadingDialog();
        LogUtil.i(TAG, "onModifyAvatarStart");
    }

    @Override
    public void onModifyAvatarSuccess(@NonNull CharSequence url) {
        LogUtil.i(TAG, "onModifyAvatarSuccess");
        setResult(RESULT_OK);
        mPresenter.loadUserInfo();
        JMessageUtil.updateUserAvatar(mPath);
    }

    @Override
    public void onModifyAvatarFail(int errorCode) {
        LogUtil.w(TAG, "onModifyAvatarFail,errorCode:" + errorCode);
    }

    @Override
    public void onModifyAvatarError(Throwable error) {
        LogUtil.e(TAG, "onModifyAvatarError" , error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onModifyAvatarCompleted() {
        LogUtil.i(TAG, "onModifyAvatarCompleted");
        dismissLoadingDialog();
    }
}
