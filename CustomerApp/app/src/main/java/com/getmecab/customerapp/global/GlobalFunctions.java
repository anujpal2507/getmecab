package com.getmecab.customerapp.global;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 16/9/16.
 */
public class GlobalFunctions {

    public static String PROTOCOL = "http";
    public static String DOMAIN = "52.74.19.64";
    public static String PORT = "85";

    public static String getUrl(String urlName) {
        switch (urlName) {
            case "SIGN_UP_USER" :
                return GlobalFunctions.PROTOCOL + "://" + GlobalFunctions.DOMAIN + ":" + GlobalFunctions.PORT + "/api/mobile/user/put";
            default:
                return null;
        }
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
