package com.getmecab.customerapp.global;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 20/9/16.
 */
public class NetworkServiceListener {
    Context context;

    public NetworkServiceListener(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected () {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && (networkInfo.isConnected() || networkInfo.isConnectedOrConnecting())) {
            return true;
        }
        return false;
    }
}
