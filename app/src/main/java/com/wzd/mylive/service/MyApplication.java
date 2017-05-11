package com.wzd.mylive.service;

import android.app.Application;

import com.wzd.mylive.bmob.BmobUtil;

/**
 * <p>Description: </p>
 * Created by wzd on 2017/5/11.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BmobUtil.initBomb(this);
    }
}
