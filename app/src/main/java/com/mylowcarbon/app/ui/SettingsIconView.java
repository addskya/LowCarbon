package com.mylowcarbon.app.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mylowcarbon.app.R;
/* 使用示例
<com.mylowcarbon.app.ui.SettingsIconView
    style="@style/SettingsView"
    android:layout_marginTop="@dimen/layout_height_10"
    android:onClick="@{() -> view.onViewClick(1)}"
    app:arrow="true"
    app:icon="@drawable/ic_header_default"
    app:name="@string/text_avatar" />
*/

/**
 * Created by Orange on 18-4-14.
 * Email:addskya@163.com
 */
public class SettingsIconView extends SettingsView {
    public SettingsIconView(@NonNull Context context) {
        this(context, null);
    }

    public SettingsIconView(@NonNull Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingsIconView);
        if (array.hasValue(R.styleable.SettingsIconView_imageUri)) {
            setImageUri(array.getString(R.styleable.SettingsIconView_imageUri));
        }

        array.recycle();
    }

    @Override
    protected int getSettingsViewLayout() {
        return R.layout.settings_icon_view;
    }

    private SimpleImageView mImageView;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mImageView = view.findViewById(R.id.settings_image);
    }

    public void setImageUri(@Nullable String imageUri) {
        if (mImageView != null) {
            mImageView.setImageURI(imageUri);
            //mImageView.
        }
    }
}
