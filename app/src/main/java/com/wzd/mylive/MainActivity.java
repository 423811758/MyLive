package com.wzd.mylive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.wzd.mylive.model.Store;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    private TextView mMessageTv;
    private MainActivityController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageTv = (TextView)findViewById(R.id.message_tv);

        mController = new MainActivityController(this);
        EventBus.getDefault().register(mController);

        Store store = new Store();
        store.setId(System.currentTimeMillis());
        store.setName("四季大丰收1");
        store.setAddress("乐都汇(五缘湾)1");
        store.saveData();
//        store.updateData("02dc7ddd9b");
//        store.deleteData("3e51f814dc");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(mController);
    }

    public void setMessageTv(String message){
        mMessageTv.setText(message);
    }
}
