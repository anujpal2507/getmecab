package com.getmecab.customerapp.activity;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.getmecab.customerapp.R;

import java.io.File;
import java.net.URI;

public class InternetNotAvailable extends Activity {
    Context context;
    Button retryButton;
    Animation rotate;
    CheckInternet checkInternet;
    ImageView retryImageView;
    LinearLayout retryLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_internet_not_available);
        retryButton = (Button) findViewById(R.id.retryInternetButton);
        retryButton.setVisibility(View.VISIBLE);
        retryImageView = (ImageView) findViewById(R.id.retryInternetImageView);
        rotate = AnimationUtils.loadAnimation(context, R.anim.clockwise_rotation);
        rotate.setRepeatCount(Animation.INFINITE);
        retryLinearLayout = (LinearLayout) findViewById(R.id.retryLinearLayout);
    }

    public void retryConnecting(View view) {
        retryLinearLayout.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
        checkInternet = new CheckInternet();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            checkInternet.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            checkInternet.execute();
        }
    }

    public void openCustomerCarePopUp(View view) {
        String uri = "tel:" + "8009141547";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private class CheckInternet extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            retryImageView.startAnimation(rotate);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null && (networkInfo.isConnected() || networkInfo.isConnectedOrConnecting()))) {
               return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            retryButton.clearAnimation();
            retryButton.setText("Retry");
            retryButton.setBackgroundResource(R.drawable.rounded_corner);
            retryButton.setVisibility(View.VISIBLE);
            retryLinearLayout.setVisibility(View.GONE);
            if (aBoolean) {
                Intent intent = new Intent(InternetNotAvailable.this, SplashScreen.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
