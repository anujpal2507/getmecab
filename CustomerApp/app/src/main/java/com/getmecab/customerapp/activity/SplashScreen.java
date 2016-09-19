package com.getmecab.customerapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.getmecab.customerapp.R;
/**
 * Created by anuj "email : anujpal2507@gmail.com" on 12/9/16.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent(SplashScreen.this, Login.class);
//                startActivity(intent);
//                finish();
            }
        }, 2000);
    }

    public void callLoginActivity(View view) {
        LinearLayout contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        contentLayout.setVisibility(View.VISIBLE);
        LinearLayout loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        loginLayout.setVisibility(View.GONE);
    }

    public void register(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

    public void rememberMeClicked(View view) {

    }
}
