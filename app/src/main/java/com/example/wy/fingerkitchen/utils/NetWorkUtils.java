package com.example.wy.fingerkitchen.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * @author LiLang
 * @date 2019/3/25
 */
public class NetWorkUtils {

    public static boolean isNetWorkAvailble(Context context) {
        return getAvailableNetWorkInfo(context) != null;
    }

    public static NetworkInfo getAvailableNetWorkInfo(Context context) {
        if (context == null) {
            return null;
        }
        try {
            @SuppressLint("WrongConstant") ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return null;
            }
            @SuppressLint("MissingPermission")
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetInfo != null && activeNetInfo.isConnected()) {
                return activeNetInfo;
            } else {
                return getExtraNetworkInfo(connectivityManager);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    public static NetworkInfo getExtraNetworkInfo(ConnectivityManager connectivityManager) {
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo activeNetInfo = null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                //api<23 required
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                if (networkInfos != null && networkInfos.length > 0) {
                    for (NetworkInfo networkInfo : networkInfos) {
                        if (networkInfo != null && networkInfo.isConnected()) {
                            return networkInfo;
                        }
                    }
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //api>=21 reqired
                Network[] networks = connectivityManager.getAllNetworks();
                if (networks != null && networks.length > 0) {
                    for (Network network : networks) {
                        //忽略vpn
                        NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(network);
                        boolean transportVpn = caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
                        if (transportVpn) {
                            continue;
                        }
                        activeNetInfo = connectivityManager.getNetworkInfo(network);
                        if (activeNetInfo != null && activeNetInfo.isConnected()) {
                            if (caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                                return activeNetInfo;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
