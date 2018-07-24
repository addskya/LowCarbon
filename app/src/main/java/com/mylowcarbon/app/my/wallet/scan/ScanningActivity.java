package com.mylowcarbon.app.my.wallet.scan;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.mylowcarbon.app.ActionBarActivity;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.databinding.ActivityScanningBinding;
import com.mylowcarbon.app.utils.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 扫码界面
 */
public class ScanningActivity extends ActionBarActivity implements ScanningContract.View {
    private static final String TAG = "ScanningActivity";
    private final int REQUEST_CODE_IMAGE = 1 ;

    private CaptureFragment captureFragment;

    private ScanningContract.Presenter mPresenter;
    private ActivityScanningBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_scanning);
        mBinding.setView(this);
        mBinding.executePendingBindings();
//        setSupportActionBar(mBinding.appBarLayout.toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mBinding.appBarLayout.toolbar.setActionBarTitle(R.string.title_scan);
        new ScanningPresenter(this);
        initView();
        initData();
    }

    @Override
    protected int getUiTitle() {
        return R.string.title_scan;
    }

    public void initView() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        requestPermissions(permissions, new Runnable() {
            @Override
            public void run() {
                captureFragment = new CaptureFragment();
                // 为二维码扫描界面设置定制化界面
                CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_camera);
                captureFragment.setAnalyzeCallback(analyzeCallback);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
            }
        });


    }

    public void initData() {


    }

    @Override
    protected void onRequestPermissionsFailure(String[] requestFailurePermissions) {
        super.onRequestPermissionsFailure(requestFailurePermissions);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(ScanningContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewClick(int position) {
        switch (position) {
            case 0://打开相册
                //调用系统API打开图库

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
                break;
            case 1://

                break;


            default:
                break;
        }
    }
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(ScanningActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            Intent resultIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                            bundle.putString(CodeUtils.RESULT_STRING, result);
                            resultIntent.putExtras(bundle);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScanningActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
