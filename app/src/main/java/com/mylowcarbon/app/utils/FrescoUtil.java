package com.mylowcarbon.app.utils;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.concurrent.Executor;

/**
 *
 */
public class FrescoUtil {

    private static final Executor UiExecutor = new Executor() {
        final Handler sHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            sHandler.post(runnable);
        }
    };

    public static void displayImageView(final ImageView view, String url) {
        final ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .build();
        Fresco.getImagePipeline()
                .fetchDecodedImage(request, view.getContext().getApplicationContext())
                .subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        if (bitmap != null) {
                            view.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                    }
                }, UiExecutor);
    }
}
