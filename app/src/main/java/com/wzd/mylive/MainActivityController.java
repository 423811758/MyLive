package com.wzd.mylive;

import com.wzd.mylive.Event.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * <p>Description: </p>
 * Created by wzd on 2017/5/15.
 */

public class MainActivityController {

    private MainActivity mActivity;

    public MainActivityController(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        this.mActivity.setMessageTv(event.getMessage());
    }


}
