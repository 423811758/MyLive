package com.wolf.android.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.wolf.R;

/**
 * <p>Description: 网络工具类</p>
 * Created by wzd on 2016/11/1.
 */
public class NetUtil {

    /**
     * 网络状态和离线的提示
     *
     * @param context
     * @param isToast
     *            是否进行提示
     * @return true:服务器连接正常,false 无网络或者离线
     */
    public static boolean ToastForNetState(Context context, boolean isToast) {
        if (!NetUtil.isNetworkAvailable(context)) {// 无网络
            if (isToast)
                ToastUtil.showShortToast(context, R.string.net_unavailable);
            return false;
        }
        return true;
    }
    /**
     * 判断系统的网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断系统的网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isSystemNetAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        return (info != null && info.isAvailable() && info.isConnected());
    }

    /**
     * 判断是否能使用wifi功能
     *
     * @param mContext 当前环境上下文对象
     * @return 是否能使用wifi功能
     */
    public static boolean isWifi(Context mContext) {
        boolean isWifi = false;
        if (mContext == null)
            return false;
        try {
            ConnectivityManager conn = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected()) {
                int nType = networkInfo.getType();
                if (nType == ConnectivityManager.TYPE_WIFI) {
                    isWifi = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWifi;
    }

    /**
     * 判断是否使用2g功能
     *
     * @param mContext 当前环境上下文对象
     * @return 是否能使用wifi功能
     */
    public static boolean is2G(Context mContext) {
        boolean twoG = false;
        if (mContext == null)
            return false;
        try {
            ConnectivityManager conn = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected()) {
                int nType = networkInfo.getType();
                if (nType == ConnectivityManager.TYPE_MOBILE) {
                    switch (networkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_EDGE:// 移动的2G是EGDE
                        case TelephonyManager.NETWORK_TYPE_GPRS:// 联通的2G是GPRS
                        case TelephonyManager.NETWORK_TYPE_CDMA:// 电信的2G为CDMA
                            twoG = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twoG;
    }
}
