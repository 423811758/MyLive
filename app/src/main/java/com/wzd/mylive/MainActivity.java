package com.wzd.mylive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wzd.mylive.model.Store;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Store store = new Store();
        store.setId(System.currentTimeMillis());
        store.setName("四季大丰收1");
        store.setAddress("乐都汇(五缘湾)1");
        store.saveData();
//        store.updateData("02dc7ddd9b");
        store.deleteData("3e51f814dc");
    }
}
