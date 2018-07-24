package com.mylowcarbon.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by Orange on 18-4-2.
 * Email:addskya@163.com
 * Fragment的管理窗口实现此方法:
 * 用于管理多个Fragment之间的跳转,
 * 比如: 注册,可能由多个子Fragment来实现不同的的
 * 信息采集(手机号验证,验证码,性别输入,密码设置等).
 * 最终每个子Fragment只负责自己的模块,
 * 处理完成后通知其容器 Activity(要求实现FragmentTransfer)成功处理(可返回结果Intent),
 * <p>
 * 然后FragmentTransfer容器负责跳转到下一个采集数据的Fragment即可.
 * <b>每个子Fragment之间不做关联</b>
 */

public interface FragmentTransfer {

    /**
     * 当前的Fragment处理完本Fragment内部任务后,
     * 调用此方法请求Fragment跳转到下一个Fragment
     *
     * @param tag  下一个Fragment的唯一TAG,由 FragmentTransfer实现类统一管理
     * @param args 可能需要带给下一个Fragment的数据
     */
    void onTranslateTo(@NonNull String tag,
                       @Nullable Bundle args);

    /**
     * Change the title associated with this activity.  If this is a
     * top-level activity, the title for its window will change.  If it
     * is an embedded activity, the parent can do whatever it wants
     * with it.
     *
     * @param titleId the title string id
     */
    void setUiTitle(@StringRes int titleId);

    /**
     * 请求退出
     */
    void onFinish();
}
