package com.mylowcarbon.app.my.about;

import com.mylowcarbon.app.net.Response;
import com.mylowcarbon.app.net.response.About;

import rx.Observable;

/**
 *
 */
class AboutModel implements AboutContract.Model {

    private static final String TAG = "AboutModel";
    private AboutRequest mRequest;

    AboutModel() {
        mRequest = new AboutRequest();
    }

    @Override
    public Observable<Response<About>> getAboutData() {
          return mRequest.getAboutData();
    }

}
