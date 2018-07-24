package com.mylowcarbon.app.my.complaints;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;

/**
 * 投诉结果
 */
public class ComplaintsResultActivity extends ActionBarActivity {
    private static final String TAG = "ComplaintsResultActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_resoult);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_complaints_result;
    }

    public void initView() {

    }

    public void initData() {

    }

    public void close(View view) {
        finish();
    }


}
