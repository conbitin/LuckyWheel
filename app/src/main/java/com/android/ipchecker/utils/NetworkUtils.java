package com.android.ipchecker.utils;

/*
 * Created by huongnd2 on 10/13/21 6:58 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 5/31/21 3:27 PM
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


public class NetworkUtils {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isNetworkAvailable(@NonNull Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            boolean isNetworkAvailable = (networkCapabilities != null && networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED));
            Log.i("NetworkUtils", "isNetworkAvailable=" + isNetworkAvailable);
            return isNetworkAvailable;
        } catch (Exception e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isConnectedWifi(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        return networkCapabilities != null && networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isConnectedCellular(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(
                network);
        return networkCapabilities != null && networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR);
    }
}
