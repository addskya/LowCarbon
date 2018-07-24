package com.mylowcarbon.app.weather;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by Orange on 18-4-24.
 * Email:addskya@163.com
 * <p>
 * code    int    错误代码
 * msg    string    接口消息提示
 * value    array    返回数据数组
 * wendu    string    ijya
 * type    string    天气
 * icon    string    天气图标URL
 */

class WeatherRequest extends BaseRequest {

    Observable<Response<Weather>> loadWeather() {
        Map<String, Object> params = newMap();
        return request("mining/weather", params, Weather.class);
    }
}
