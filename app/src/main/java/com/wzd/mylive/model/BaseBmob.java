package com.wzd.mylive.model;

import android.util.Log;

import com.wzd.mylive.Event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.push.lib.util.LogUtil;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * <p>Description: </p>
 * Created by wzd on 2017/5/11.
 */

public class BaseBmob extends BmobObject {

    public void saveData(){
        save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    MessageEvent msg = new MessageEvent();
                    msg.setMessage(s);
                    EventBus.getDefault().post(msg);
                    Log.i("test", "添加数据成功，返回objectId为："+s);
                }
                else{
                    Log.e("test", "创建数据失败："+e.getMessage());
                }
            }
        });
    }

    public void updateData(String objectId){
        update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("test", "更新成功");
                }
                else {
                    Log.e("test", "更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void deleteData(String objectId){
        delete(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("test", "删除成功");
                }
                else {
                    Log.e("test", "删除失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
