package com.mylowcarbon.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mylowcarbon.app.utils.LogUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mylowcarbon.app", appContext.getPackageName());
    }

    @Test
    public void testRetrofit() throws Exception {
        RequestTest test = new RequestTest();
        LogUtil.i("TAG", "---------------------------");
        test.testGetAdList();
        LogUtil.i("TAG", "---------------------------");
    }
}
