package com.mylowcarbon.app;

import com.mylowcarbon.app.net.BaseRequest;
import com.mylowcarbon.app.net.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Orange on 18-3-23.
 * Email:addskya@163.com
 */

public class RequestTest extends BaseRequest {

    public void testGetAdList() {
        Map<String, Object> map = new HashMap<>();

        request("get_cause_list", map)
                .subscribe(new DefaultSubscriber<Response<?>>() {
                    @Override
                    public void onStart() {
                        System.out.println("start");
                    }

                    @Override
                    public void onNext(Response<?> response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }

                });
    }
}
