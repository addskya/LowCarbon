package com.mylowcarbon.app.home.my;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.bracelet.own.DevicesActivity;
import com.mylowcarbon.app.browser.MyBarBrowserActivity;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.FragmentMyBinding;
import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.UserInfo;
import com.mylowcarbon.app.my.about.AboutActivity;
import com.mylowcarbon.app.my.authentication.AuthenticationActivity;
import com.mylowcarbon.app.my.complaints.ComplaintsActivity;
import com.mylowcarbon.app.my.order.MyOrdersActivity;
import com.mylowcarbon.app.my.recommend.RecommendActivity;
import com.mylowcarbon.app.my.settings.PersonalSettingsActivity;
import com.mylowcarbon.app.my.wallet.MyWalletActivity;
import com.mylowcarbon.app.sts.StsContract;
import com.mylowcarbon.app.sts.StsPresenter;
import com.mylowcarbon.app.utils.LogUtil;


/**
 * 主页-我的
 */
public class MyFragment extends BaseFragment implements MyContract.View {
    private static final String TAG = "MyFragment";

    private MyContract.Presenter mPresenter;
    private FragmentMyBinding mBinding;
    private StsContract.Presenter mSTSPresenter;
    private UserInfo userInfo;
    private final int REQUEST_CODE_EDIT = 1;

    @Override
    public View initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fragment_my, null, false);
        mBinding.setView(this);
        mBinding.executePendingBindings();
        return mBinding.getRoot();
    }

    @Override
    public void initData() {

         userInfo = ModelDao.getInstance().getUserInfo();
        if (userInfo == null) {
            return;
        }

        Log.e(TAG, "MyFragment initData: " + userInfo.getNickname());

        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
             Uri uri = Uri.parse(userInfo.getAvatar());
            mBinding.civHead.setImageURI(uri);
        }
        mBinding.tvNickName.setText(userInfo.getNickname());
        mBinding.tvPhone.setText(userInfo.getMobile());

        Drawable unOdentityDrawable = getResources().getDrawable(R.drawable.ic_unidentity_gray);
        unOdentityDrawable.setBounds(0, 0, unOdentityDrawable.getMinimumWidth(), unOdentityDrawable.getMinimumHeight());
        Drawable identityDrawable = getResources().getDrawable(R.drawable.ic_identity_green);// 找到资源图片
        // 这一步必须要做，否则不会显示。
        identityDrawable.setBounds(0, 0, identityDrawable.getMinimumWidth(), identityDrawable.getMinimumHeight());// 设置图片宽高
        //（－2：用户冻结，-1:异常状态，0：未实名认证，1：实名认证审核中，2：已实名认证,3:拒绝认证）
        switch (userInfo.getStatus()) {
            case -2:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_1);
                mBinding.tvIdStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);// 设置到控件中
                break;
            case -1:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_2);
                mBinding.tvIdStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);// 设置到控件中
                break;
            case 0:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_3);
                mBinding.tvIdStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);// 设置到控件中
                break;
            case 1:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_4);
                mBinding.tvIdStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);// 设置到控件中
                break;
            case 2:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_5);
                mBinding.tvIdStatus.setCompoundDrawables(identityDrawable, null, null, null);// 设置到控件中
                break;
            case 3:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_6);
                mBinding.tvIdStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);// 设置到控件中
                break;
            default:
                mBinding.tvIdStatus.setText(R.string.txt_user_status_3);
                mBinding.tvIdStatus.setCompoundDrawables(unOdentityDrawable, null, null, null);// 设置到控件中

        }


    }


    @Override
    public void initEvent() {

    }

    @Override
    public void setPresenter(MyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://设置
                Intent intent0 = new Intent(mActivity, PersonalSettingsActivity.class);
                startActivityForResult(intent0,REQUEST_CODE_EDIT);

                break;
            case 1://身份认证
                if (userInfo.getStatus() == 0 || userInfo.getStatus() == 3) {
                    Intent intent1 = new Intent(mActivity, AuthenticationActivity.class);
                    startActivity(intent1);
                }

                break;
            case 2://我的钱包
                Intent intent2 = new Intent(mActivity, MyWalletActivity.class);
                startActivity(intent2);
                break;
            case 3://我的设备
                Intent intent3 = new Intent(mActivity, DevicesActivity.class);
                startActivity(intent3);
                break;
            case 4://订单跟踪
                Intent intent4 = new Intent(mActivity, MyOrdersActivity.class);
                startActivity(intent4);
                break;
            case 5://推荐有奖
                Intent intent5 = new Intent(mActivity, RecommendActivity.class);
                startActivity(intent5);
                break;
            case 6://服务协议
                Intent intent6 = new Intent(mActivity, MyBarBrowserActivity.class);
                intent6.putExtra(AppConstants.URL, AppConstants.URL_AGREEMENT);
                startActivity(intent6);
                break;
            case 7://发起投诉
                Intent intent7 = new Intent(mActivity, ComplaintsActivity.class);
                startActivity(intent7);
                break;
            case 8://关于
                Intent intent8 = new Intent(mActivity, AboutActivity.class);
                startActivity(intent8);
                break;


            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *
         */
        if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK) {
            initData();

        }
    }
}
