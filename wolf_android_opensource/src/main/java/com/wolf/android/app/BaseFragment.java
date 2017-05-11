package com.wolf.android.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p>Description: </p>
 * Created by wzd on 2016/10/14.
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        if (getMainContentViewId() != 0) {
            mRoot = mInflater.inflate(getMainContentViewId(), null);
        }
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();
        initComponent();
        initData();
    }

    protected View findViewById(int resid) {
        return mActivity.findViewById(resid);
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
