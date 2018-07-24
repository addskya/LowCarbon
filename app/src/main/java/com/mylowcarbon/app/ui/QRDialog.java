package com.mylowcarbon.app.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.mylowcarbon.app.BaseDialog;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.net.response.MyWallet;
import com.mylowcarbon.app.utils.ImageUtil;
import com.mylowcarbon.app.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * 二维码弹框
 */
public class QRDialog extends BaseDialog {
    private  View mView;
    private String mQR;
    private ImageView mQRview;
    private Bitmap mBitmap;
    private CharSequence mTitle;
    private CharSequence mMessage;
    private TextView mTitleView;
    private TextView mMessageView;
    private TextView mSaveView;
    private ImageButton mCloseView;
    private Context context;
    public QRDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    public static Dialog intentTo(@NonNull Context context, @Nullable String qr) {
        QRDialog dialog = new QRDialog(context);
        dialog.setQrCode(qr);
        dialog.show();
        return dialog;
    }

    public static Dialog intentTo(@NonNull Context context, @Nullable String qr, @Nullable CharSequence title,
                                  @Nullable CharSequence message) {
        QRDialog dialog = new QRDialog(context);
        dialog.setQrCode(qr);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }


    @Override
    protected View onCreateView(LayoutInflater inflater) {
        mView= inflater.inflate(R.layout.dialog_qr, null, false);
        initViews(mView);
        return mView;
    }

    private void initViews(@Nullable View view) {
        if (view == null) {
            return;
        }
        mQRview = view.findViewById(R.id.iv_qr);
        mTitleView = view.findViewById(R.id.title);
        mMessageView = view.findViewById(R.id.message);
        mSaveView = view.findViewById(R.id.save);
        mCloseView = view.findViewById(R.id.close);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(context, "请在应用管理中打开“读写存储”访问权限！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mBitmap!=null){
                    String  fileName = System.currentTimeMillis()  +".png";
                    String path = ImageUtil.saveBitmap(context,fileName,mBitmap);
                    ToastUtil.showLong(context,String.format(context.getResources().getString(R.string.format_save_pic_suc), path));
                }
            }
        });
        mCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bindView(mTitleView,mTitle);
        bindView(mMessageView,mMessage);
        bindView(mQRview,mBitmap);
    }

    /**
     * @param qrCode
     */
    public void setQrCode(@Nullable String qrCode) {
        if (TextUtils.isEmpty(qrCode)) {
            return;
        }
        mQR = qrCode;
                    /*
                    * qrCode：字符串内容
                    * w：图片的宽
                    * h：图片的高
                    * logo：不需要logo的话直接传null
                    * */

        mBitmap = CodeUtils.createImage(qrCode, 400, 400, null);
        if (mBitmap == null){
            return;
        }


    }



    /**
     * Set the Dialog title
     *
     * @param title the dialog title
     */
    @Override
    public final void setTitle(@Nullable CharSequence title) {
        mTitle = title;
        bindView(mTitleView, title);
    }

    /**
     * Set the Dialog message
     *
     * @param message the dialog message content
     */
    public void setMessage(@Nullable CharSequence message) {
        mMessage = message;
        bindView(mMessageView, message);
    }

    /**
     * bind the text into view
     *
     * @param view the view which will set text
     * @param text the text content
     */
    private void bindView(@Nullable TextView view,
                          @Nullable CharSequence text) {
        if (view == null) {
            return;
        }
        if (TextUtils.isEmpty(text)) {
            view.setVisibility(View.GONE);
            return;
        }
        view.setVisibility(View.VISIBLE);
        view.setText(text);
    }
    /**
     * bind the bitmap into view
     *
     *
     */
    private void bindView(@Nullable ImageView view,
                          @Nullable Bitmap bitmap) {
        if (view == null) {
            return;
        }
        if (bitmap == null) {
            return;
        }

        view.setVisibility(View.VISIBLE);
        view.setImageBitmap(bitmap);
    }
    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }

}
