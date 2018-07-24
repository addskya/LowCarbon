package com.mylowcarbon.app.my.recommend;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.TimePickerView;
import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.constant.AppConstants;
import com.mylowcarbon.app.databinding.ActivityPickdateBinding;
import com.mylowcarbon.app.ui.OkDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 选择时间
 */
public class PickDateActivity extends ActionBarActivity implements PickDateContract.View {
    private static final String TAG = "BindEmailActivity";
    private PickDateContract.Presenter mPresenter;
    private ActivityPickdateBinding mBinding;
    private TimePickerView mPvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pickdate);
        mBinding.setView(this);
        mBinding.executePendingBindings();

        new PickDatePresenter(this);
        initView();
        initData();

    }

    @Override
    protected int getUiTitle() {
        return R.string.title_pick_date;
    }

    public void initView() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
        mPvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                EditText mEt = (EditText) v;
                mEt.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setSubmitColor(getResources().getColor(R.color.orange3))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.white))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.colorPrimaryDark))//标题背景颜色 Night mode
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();



    }

    public void initData() {


    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(PickDateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://开始时间
                mPvTime.show(mBinding.etStartTime);//弹出时间选择器

                break;
            case 1://结束时间
                mPvTime.show(mBinding.etEndTime);//弹出时间选择器

                break;
            case 2://确定
                String startTime = mBinding.etStartTime.getText().toString();
                if (TextUtils.isEmpty(startTime)) {
                    OkDialog.intentTo(this,
                            getString(R.string.tip_pick_startime),
                            getString(R.string.text_sure));
                    return;
                }
                String endTime = mBinding.etEndTime.getText().toString();
                if (TextUtils.isEmpty(endTime)) {
                    OkDialog.intentTo(this,
                            getString(R.string.tip_pick_endtime),
                            getString(R.string.text_sure));
                    return;
                }
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.START_TIME,startTime );
                bundle.putString(AppConstants.END_TIME, endTime);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
                 break;


            default:
                break;
        }
    }
}
