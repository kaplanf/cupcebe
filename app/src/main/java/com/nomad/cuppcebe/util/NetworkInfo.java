package com.nomad.cuppcebe.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by kaplanfatt on 19/09/15.
 */
public class NetworkInfo {

    public static boolean isInternetAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
