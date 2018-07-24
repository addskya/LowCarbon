package com.mylowcarbon.app.exchange.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylowcarbon.app.BaseFragment;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.FragmentExchangeBinding;
import com.mylowcarbon.app.exchange.Device;
import com.mylowcarbon.app.exchange.Mining;
import com.mylowcarbon.app.ui.ExchangeDialog;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.List;

/**
 * 兑换fragment
 */
public class ExchangeFragment extends BaseFragment implements ExchangeContract.View {

    private static final String TAG = "ExchangeFragment";

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    private static final String EXTRA_DEVICE =
            "com.mylowcarbon.app.EXTRA_DEVICE";

    public static BaseFragment getArgsFragment(@NonNull Device device) {
        BaseFragment fragment = new ExchangeFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DEVICE, device);
        fragment.setArguments(args);
        return fragment;
    }


    private ExchangeContract.Presenter mPresenter;
    private FragmentExchangeBinding mBinding;
    private ExchangeAdapter mAdapter;
    private ExchangeDialog mExchangeDialog; //兑换框

    private Device mDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
        Bundle args = getArguments();
        assert args != null;
        Device device = args.getParcelable(EXTRA_DEVICE);
        assert device != null;
        mDevice = device;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentExchangeBinding.inflate(inflater, container, false);

        mAdapter = new ExchangeAdapter(inflater, this);
        mBinding.list.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Mining> minings = mDevice.getMining();
        mAdapter.addData(minings);

        mBinding.setView(this);
        mBinding.executePendingBindings();
        new ExchangePresenter(this);
    }


    @Override
    public void setPresenter(ExchangeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void exchangeAll() {
        //TODO 请求奖励(调用钱包)
//        exchangeToken(userInfo.getMobile(), userInfo.getPay_pwd(), userInfo.getWallet_address(), 99, new ValueCallback<String>() {
//
//            @Override
//            public void onReceiveValue(String value) {
//                LogUtil.e(TAG, "**********exchangeToken  onReceiveValue: " + value);
//
//            }
//        });


        LogUtil.i(TAG, "exchangeAll");
        String date = mDevice.getDate();
        mPresenter.exchangeAll(date);
    }

    @Override
    public void exchange(@Nullable Mining mining) {
        LogUtil.i(TAG, "exchange,mining:" + mining);
        if (mining == null) {
            return;
        }
        mPresenter.exchange(mDevice, mining);
    }

    @Override
    public String getDeviceCountDesc() {
        return getString(R.string.format_number_device, mAdapter.getDataCount());
    }

    @Override
    public String getEnergyDesc() {
        int totalEnergy = mDevice.getDate_totoal_energy();
        double totalLcl = mDevice.getDate_totoal_lcl();

        return getString(R.string.format_energy, totalEnergy, totalLcl);
    }

    @Override
    public void onExchangeAllStart() {
        LogUtil.i(TAG, "onExchangeAllStart");
        showLoadingDialog();
    }

    @Override
    public void onExchangeAllSuccess(@Nullable ExchangeResp resp) {
        ExchangeDialog.intentTo(getContext(), mDevice, resp);
    }

    @Override
    public void onExchangeAllFail(int errorCode) {
        LogUtil.w(TAG, "onExchangeFail,errorCode:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onExchangeAllError(Throwable error) {
        LogUtil.e(TAG, "onExchangeAllError", error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onExchangeAllCompleted() {
        LogUtil.i(TAG, "onExchangeAllCompleted");
        dismissLoadingDialog();
    }

    @Override
    public void onExchangeStart() {
        LogUtil.i(TAG, "onExchangeStart");
        showLoadingDialog();
    }

    @Override
    public void onExchangeSuccess(@Nullable ExchangeResp resp) {
        LogUtil.i(TAG, "onExchangeSuccess:" + resp);
        ExchangeDialog.intentTo(getContext(), mDevice, resp);
    }

    @Override
    public void onExchangeFail(int errorCode) {
        LogUtil.w(TAG, "onExchangeFail,errorCode:" + errorCode);
        showErrorCode(errorCode);
    }

    @Override
    public void onExchangeError(Throwable error) {
        LogUtil.e(TAG, "onExchangeError", error);
        dismissLoadingDialog();
        showError(error);
    }

    @Override
    public void onExchangeCompleted() {
        LogUtil.i(TAG, "onExchangeCompleted");
        dismissLoadingDialog();
    }
}

