package com.getmecab.customerapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.getmecab.customerapp.R;
import com.getmecab.customerapp.database.LocalData;
import com.getmecab.customerapp.database.LocalDataDB;
import com.getmecab.customerapp.database.OneWayData;
import com.getmecab.customerapp.database.OneWayDataDB;
import com.getmecab.customerapp.database.RoundTripData;
import com.getmecab.customerapp.database.RoundTripDataDB;
import com.getmecab.customerapp.global.GlobalConstants;
import com.getmecab.customerapp.global.GlobalFunctions;
import com.getmecab.customerapp.global.RequestHandler;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 16/9/16.
 */
public class SignUp extends Activity {

    EditText fullName, email, mobileNumber, password, confirmPassword;
    CheckBox termsConditions;
    Context context;
    String message ="", result;
    int flag = 0;
    LocalDataDB localDataDB;
    OneWayDataDB oneWayDataDB;
    RoundTripDataDB roundTripDataDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        fullName = (EditText) findViewById(R.id.fullNameEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        mobileNumber = (EditText) findViewById(R.id.mobileNumberEditText);
        password = (EditText) findViewById(R.id.signUpPasswordEditText);
        confirmPassword = (EditText) findViewById(R.id.signUpConfirmPasswordEditText);
        termsConditions = (CheckBox) findViewById(R.id.termsConditionCheckBox);
    }

    @Override
    protected void onResume() {
        super.onResume();
        localDataDB = new LocalDataDB(context);
        oneWayDataDB = new OneWayDataDB(context);
        roundTripDataDB = new RoundTripDataDB(context);
    }

    public void rememberMeClicked(View view) {

    }

    public void registerNewUser(View view) {
       /* Intent intent = new Intent(SignUp.this, Home.class);
        startActivity(intent);
        finish();*/
        if (termsConditions.isChecked()) {
            String name, emailId, phoneNumber, userPassword, confirmUserPassword;
            boolean isCorrectEntries = true;
            name = fullName.getText().toString();
            emailId = email.getText().toString();
            phoneNumber = mobileNumber.getText().toString();
            userPassword = password.getText().toString();
            confirmUserPassword = confirmPassword.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                email.setText("");
                isCorrectEntries = false;
                GlobalFunctions.showToast(context, "Invalid email id.");
            }
            if (!(userPassword.length() >= 6)) {
                GlobalFunctions.showToast(context, "Password must be minimum 6 characters long.");
                password.setText("");
                confirmPassword.setText("");
                isCorrectEntries = false;
            }
            if (!userPassword.equals(confirmUserPassword)) {
                GlobalFunctions.showToast(context, "Confirm Password not matches with Password.");
                password.setText("");
                confirmPassword.setText("");
                isCorrectEntries = false;
            }
            if (isCorrectEntries) {
                RegisterUser registerUser = new RegisterUser(name, emailId, phoneNumber, userPassword);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    registerUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    registerUser.execute();
            }
        } else {
            GlobalFunctions.showToast(context, "Please confirm terms and conditions!!!");
        }
    }

    private class RegisterUser extends AsyncTask<String, String, Boolean> {
        String newUserfullName, newUserEmailId, newUserPhoneNumber, newUserPassword;
        int statusCode = 401;
        String masterData = null;
        ProgressDialog progressDialog;

        public RegisterUser(String name, String emailId, String phoneNumber, String userPassword) {
            newUserfullName = name;
            newUserEmailId = emailId;
            newUserPhoneNumber = phoneNumber;
            newUserPassword = userPassword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Registering New User");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<>();
            boolean saveData = false;
            params.add(new BasicNameValuePair("name", newUserfullName.trim()));
            params.add(new BasicNameValuePair("email", newUserEmailId.trim()));
            params.add(new BasicNameValuePair("mobile", newUserPhoneNumber.trim()));
            params.add(new BasicNameValuePair("upassword", newUserPassword.trim()));
            HttpResponse httpResponse = RequestHandler.makeRequest(context, GlobalFunctions.getUrl("SIGN_UP_USER"), params);
            if (httpResponse != null) {
                statusCode = httpResponse.getStatusLine().getStatusCode();
                SQLiteDatabase db = context.openOrCreateDatabase(GlobalConstants.DATABASE_NAME, MODE_PRIVATE, null);
                db.beginTransaction();
                try {
                    masterData = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
                    saveData = masterJsonToObject(masterData, db);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                }
            }
            Log.d("TESTING", "statusCode >>>" + statusCode);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (statusCode == 200) {
                fullName.setText("");
                email.setText("");
                mobileNumber.setText("");
                password.setText("");
                confirmPassword.setText("");
                termsConditions.setChecked(false);
                SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.global_shared_preference_key_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.isLogging_session_alive), true);
                editor.apply();
                GlobalFunctions.showToast(context, "Congratulations your are successfully registered.");
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                Intent intent = new Intent(SignUp.this, Home.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private Boolean masterJsonToObject(String masterDataJson, SQLiteDatabase sqlDb) {
        try {
            JSONObject jsonObject = new JSONObject(masterDataJson);
            if (!(jsonObject.length() == 0)) {

                if (jsonObject.has("flag")) {
                    flag = jsonObject.getInt("flag");
                }
                if (jsonObject.has("message")) {
                    message = jsonObject.getString("message");
                }
                if (jsonObject.has("result")) {
                    result = jsonObject.getString("result");
                }
                Boolean saveLocalData = false;
                Boolean saveOneWayData = false;
                Boolean saveRoundTripData = false;
                saveLocalData = saveLocalMasterData(jsonObject.getJSONObject("local"), sqlDb);
                saveOneWayData = saveOneWayMasterData(jsonObject.getJSONObject("one-way"), sqlDb);
                saveRoundTripData = saveRoundTripMasterData(jsonObject.getJSONObject("round-way"), sqlDb);
                if (!saveLocalData) {
                    System.out.print("======= 1 ======");
                    return false;
                }
                if (!saveOneWayData) {
                    System.out.print("======= 2 ======");
                    return false;
                }
                if (!saveRoundTripData) {
                    System.out.print("======= 3 ======");
                    return false;
                }
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean saveLocalMasterData(JSONObject jsonObject, SQLiteDatabase sqlDb) {
        try {
            if (!(jsonObject.length() == 0)) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (jsonArray.length() != 0) {
                    List<LocalData> localDataList = new ArrayList<>();
                    JSONObject localDataJsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        LocalData localData = new LocalData();
                        localDataJsonObject = jsonArray.getJSONObject(i);
                        localData.setSource(localDataJsonObject.getString("source"));
                        localData.setDestination(localDataJsonObject.getString("destination"));
                        localDataList.add(localData);
                    }
                    localDataDB.onUpgrade(sqlDb, 0 , 0);
                    localDataDB.addLocalData(localDataList, sqlDb);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean saveOneWayMasterData(JSONObject jsonObject, SQLiteDatabase sqlDb) {
        try {
            if (jsonObject.length() != 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (jsonArray.length() != 0) {
                    List<OneWayData> oneWayDataList = new ArrayList<>();
                    JSONObject oneWayDatatJsonObject;
                    for (int i = 0 ; i < jsonArray.length(); i++) {
                        OneWayData oneWayData = new OneWayData();
                        oneWayDatatJsonObject = jsonArray.getJSONObject(i);
                        oneWayData.setSource(oneWayDatatJsonObject.getString("source"));
                        oneWayData.setDestination(oneWayDatatJsonObject.getString("destination"));
                        oneWayDataList.add(oneWayData);
                    }
                    oneWayDataDB.onUpgrade(sqlDb, 0, 0);
                    oneWayDataDB.addOneWayData(oneWayDataList, sqlDb);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean saveRoundTripMasterData(JSONObject jsonObject, SQLiteDatabase sqlDb) {
        try {
            if (jsonObject.length() != 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (jsonArray.length() != 0) {
                    ArrayList<RoundTripData> roundTripDataList = new ArrayList<>();
                    JSONObject roundTripDataJsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RoundTripData roundTripData = new RoundTripData();
                        roundTripDataJsonObject = jsonArray.getJSONObject(i);
                        roundTripData.setSource(roundTripDataJsonObject.getString("source"));
                        roundTripData.setDestination(roundTripDataJsonObject.getString("destination"));
                        roundTripDataList.add(roundTripData);
                    }
                    roundTripDataDB.onUpgrade(sqlDb, 0, 0);
                    roundTripDataDB.addRoundTripData(roundTripDataList, sqlDb);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
