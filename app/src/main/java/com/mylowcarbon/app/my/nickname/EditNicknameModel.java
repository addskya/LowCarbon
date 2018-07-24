package com.mylowcarbon.app.my.nickname;

import android.support.annotation.NonNull;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.net.Response;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Orange on 18-4-6.
 * Email:addskya@163.com
 */
class EditNicknameModel implements EditNicknameContract.Model {

    private EditNicknameRequest mRequest;

    EditNicknameModel() {
        mRequest = new EditNicknameRequest();
    }

    @Override
    public Observable<Response<?>> modifyNickname(@NonNull final CharSequence nickname) {
        return mRequest.modifyNickname(nickname)
                .doOnNext(new Action1<Response<?>>() {
                    @Override
                    public void call(Response<?> response) {
                        if (response.isSuccess()) {
                            // 更新本地数据库中昵称
                            ModelDao.getInstance().modifyNickname(nickname);
                        }
                    }
                });
    }
}
