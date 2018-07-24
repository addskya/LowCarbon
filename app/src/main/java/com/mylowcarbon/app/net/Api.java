package com.mylowcarbon.app.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Orange on 18-2-26.
 * Email:chenghe.zhang@ck-telecom.com
 * 网络请求API
 */

interface Api {

    /**
     * 请求网络接口
     * 具体的api接口,
     * 如: https://www.xxx.com.cn/get_version
     * https://www.xxx.com.cn/ 为BaseUrl
     *
     * @param api  get_version
     * @param para 请求指定接口时,需要的参数
     * @return Observable包装的响应
     */
    @POST("{api}")
    Observable<String> request(@Path("api") @NonNull String api,
                               @Body @Nullable Map<String, Object> para);

    @GET("{api}")
    Observable<String> get(@Path("api") @NonNull String api);

    @PUT
    @Multipart
    @POST("{api}")
    Observable<String> upload(@Path("api") @NonNull String api,
                                  @PartMap @NonNull Map<String, RequestBody> para);
}
