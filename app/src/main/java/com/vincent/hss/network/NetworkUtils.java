package com.vincent.hss.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * description ：
 * project name：CoolApp
 * author : Vincent
 * creation date: 2017/3/5 2:19
 *
 * @version 1.0
 */

public class NetworkUtils {

    /**
     * 判断网络连接
     * @param context
     * @return
     */
    public static boolean hasNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            return networkInfo.isAvailable();
        }
        return false;
    }

}
