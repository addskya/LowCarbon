package com.mylowcarbon.app.my.nickname;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityEditNicknameBinding;
import com.mylowcarbon.app.jiguang.JMessageUtil;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.AbstractSettingsActivity;
import com.mylowcarbon.app.utils.LogUtil;

/**
 * 修改昵称
 */
public class EditNicknameActivity extends AbstractSettingsActivity
        implements EditNicknameContract.View {

    private static final String TAG = "EditNicknameActivity";
    private static final String EXTRA_USER_INFO =
            "com.mylowcarbon.app.EXTRA_USER_INFO";

    /**
     * 显示修改昵称UI
     *
     * @param activity    the Host activity
     * @param userInfo    the origin userInfo
     * @param requestCode the requestCode
     */
    public static void intentTo(@NonNull Activity activity,
                                @NonNull UserInfo userInfo,
                                int requestCode) {
        Intent intent = new Intent(activity, EditNicknameActivity.class);
        packArgs(intent, userInfo);
        activity.startActivityForResult(intent, requestCode);
    }


    private EditNicknameContract.Presenter mPresenter;
    private ActivityEditNicknameBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_edit_nickname);
        mBinding.setView(this);
        mBinding.executePendingBindings();
        parseArgs();

        new EditNicknamePresenter(this);
    }

    private void parseArgs() {
        UserInfo userInfo = getArgsUserInfo();
        mBinding.setUserInfo(userInfo);
        mBinding.executePendingBindings();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_edit_nickname;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(EditNicknameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onTextChanged(@NonNull Editable text) {
        mBinding.commit.setEnabled(verifyInput(text));
    }

    private boolean verifyInput(@Nullable CharSequence input) {
        return (!TextUtils.isEmpty(input)) &&
                TextUtils.getTrimmedLength(input) >=
                        getResources().getInteger(R.integer.nickname_min_length);
    }

    @Override
    public void commit() {
        CharSequence nickname = mBinding.nickname.getText();
        if (!verifyInput(nickname)) {
            return;
        }

        mPresenter.modifyNickname(nickname);
    }

    @Override
    public void onModifyNicknameStart() {
        LogUtil.i(TAG, "onModifyNicknameStart");
        showLoadingDialog();
    }

    @Override
    public void onModifyNicknameSuccess(@NonNull CharSequence nickname) {
        //更新极光昵称
        JMessageUtil.updateNickName(nickname.toString());
        LogUtil.i(TAG, "onModifyNicknameSuccess:" + nickname);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onModifyNicknameFail(int errorCode) {
        LogUtil.w(TAG, "onModifyNicknameFail:" + errorCode);
        showCode(errorCode);
    }

    @Override
    public void onModifyNicknameError(Throwable error) {
        LogUtil.e(TAG, "onModifyNicknameError", error);
        showError(error);
        dismissLoadingDialog();
    }

    @Override
    public void onModifyNicknameCompleted() {
        LogUtil.i(TAG, "onModifyNicknameCompleted");
        dismissLoadingDialog();
    }
}
