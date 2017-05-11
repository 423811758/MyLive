package com.wolf.android.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * <p>Description: Toast提示工具类</p>
 * Created by wzd on 2016/11/1.
 */
public class ToastUtil {
    static Toast t = null;

    public static void showShortToast(Context context, String text) {
        if (AppHelper.isClientRunTop(context)) {// 前台运行
            if (t == null) {
                t = Toast.makeText(context, text, Toast.LENGTH_LONG);
            } else {
                t.setText(text);
            }
            t.show();
        }
    }

    public static void showShortToast_All(Context context, String text) {
        if (t == null) {
            t = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            t.setText(text);
        }
        t.show();
    }

    public static void showLongToast_All(Context context, String text) {
        if (t == null) {
            t = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            t.setText(text);
        }
        t.show();
    }

    public static void showShortToast(Context context, int textRid) {
        if (AppHelper.isClientRunTop(context)) {// 前台运行
            if (t == null) {
                t = Toast.makeText(context, textRid, Toast.LENGTH_LONG);
            } else {
                t.setText(textRid);
            }
            t.show();
        }
    }

    public static void showShortToast_All(Context context, int textRid) {
        if (t == null) {
            t = Toast.makeText(context, textRid, Toast.LENGTH_LONG);
        } else {
            t.setText(textRid);
        }
        t.show();
    }

    public static void cancelToast() {
        if (t != null) {
            t.cancel();
        }
    }
}
