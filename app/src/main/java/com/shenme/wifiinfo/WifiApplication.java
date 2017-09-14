package com.shenme.wifiinfo;

import android.app.Application;

import com.tencent.bugly.Bugly;

/**
 * Created by CANC on 2017/9/14.
 */

public class WifiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), "13f11bcccbbe", false);
    }
}
