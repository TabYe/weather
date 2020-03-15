package com.ncu.yzj.weather;

import android.app.Application;

import com.ncu.yzj.weather.net.Manage;

/**
 * Created by 叶长建
 * on 2020/3/15 14:42
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Manage.instance().init(getApplicationContext());
    }
}
