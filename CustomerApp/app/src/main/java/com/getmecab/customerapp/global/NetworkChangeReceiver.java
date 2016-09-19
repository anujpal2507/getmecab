package com.getmecab.customerapp.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 20/9/16.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkServiceListener networkServiceListener = new NetworkServiceListener(context);
        if (!networkServiceListener.isNetworkConnected()) {
            Log.d("NETWORK","NO INTERNET CONNECTIVITY");
        } else {
            Log.d("NETWORK","INTERNET CONNECTIVITY");
        }
    }
}
