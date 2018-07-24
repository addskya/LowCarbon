package com.mylowcarbon.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.DefaultSubscriber;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.bracelet.own.DevicesActivity;
import com.mylowcarbon.app.browser.BrowserActivity;
import com.mylowcarbon.app.databinding.DialogExchangeBinding;
import com.mylowcarbon.app.exchange.Device;
import com.mylowcarbon.app.exchange.fragment.ExchangeResp;
import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.utils.DateUtil;
import com.mylowcarbon.app.utils.LogUtil;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 兑换对话框
 */
public class ExchangeDialog extends BaseDialog {

    private static final String TAG = "ExchangeDialog";

    private ExchangeDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Display the Exchange Result Dialog
     *
     * @param context activity context
     * @param device  the exchange device
     * @param resp    the exchange response
     * @return the Dialog Entry
     */
    public static Dialog intentTo(@NonNull Context context,
                                  @NonNull final Device device,
                                  @NonNull final ExchangeResp resp) {
        final ExchangeDialog dialog = new ExchangeDialog(context);
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dl) {
                dialog.setExchangeParams(device, resp);
            }
        });
        dialog.show();
        return dialog;
    }

    private DialogExchangeBinding mBinding;

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        mBinding = DialogExchangeBinding.inflate(
                inflater, null, false);
        mBinding.setView(this);
        return mBinding.getRoot();
    }

    private void setExchangeParams(@NonNull final Device device,
                                   @NonNull final ExchangeResp resp) {
        // 你昨天的运动获得总能量值：
        mBinding.setData(resp);
        mBinding.setDevice(device);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }

    public String getExchangeResultDesc(@Nullable Device device) {
        return getContext().getResources().getString(
                R.string.format_exchange_result,
                device != null ?device.getDate() : DateUtil.getCalendar(-1),
                String.valueOf(device != null ? device.getDate_totoal_energy() : 0));
    }

    /**
     * 去钱包
     */
    public void view() {
        LogUtil.i(TAG, "view");
        Intent intent = new Intent(getBaseContext(), DevicesActivity.class);
        getBaseContext().startActivity(intent);
    }

    public void showRules() {
        // 在 UI层直接对网络层读取数据, 也是够懒了, 不过,这样快.
        new RulesRequest().getMiningDescription()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Response<Rules>>() {
                    @Override
                    public void onNext(Response<Rules> response) {
                        if (response.isSuccess()) {
                            Rules rules = response.getValue();
                            BrowserActivity.intentTo(getContext(), rules.url.value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e(TAG, "onError", e);
                    }

                });
    }

    private final class RulesRequest extends BaseRequest {

        Observable<Response<Rules>> getMiningDescription() {
            Map<String, Object> params = newMap();
            return request("mining/get_mining_description", params, Rules.class);
        }
    }

    private static final class Rules {
        // {"url":"{"value":"http://lcf.test.viecs.com/help/query/mining_device_rule.html","status":1}"}
        private Entry url;

        static class Entry {
            private String value;
            private int status;
        }
    }
}
