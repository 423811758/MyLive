package com.wolf.android.app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * <p>Description: 所有Activity必须继承该类</p>
 * Created by wzd on 2016/10/13.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected Context mContext;
    protected boolean isLive = true; //activity是否活动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getMainContentViewId() != 0) {
            setContentView(getMainContentViewId());
        }
        mActivity = this;
        mContext = getApplicationContext();

        initComponent();

        initData();

    }

    /**
     * 初始化组件
     */
    protected abstract void initComponent();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getMainContentViewId();
}
