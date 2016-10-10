package com.getmecab.customerapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getmecab.customerapp.R;
import com.getmecab.customerapp.database.CabDTO;
import com.getmecab.customerapp.database.LocalDataDB;
import com.getmecab.customerapp.database.OneWayDataDB;
import com.getmecab.customerapp.database.Pricing;
import com.getmecab.customerapp.database.PricingDB;
import com.getmecab.customerapp.database.PricingObjectDTO;
import com.getmecab.customerapp.database.RoundTripDataDB;
import com.getmecab.customerapp.global.BookingCalendar;
import com.getmecab.customerapp.global.GlobalFunctions;
import com.getmecab.customerapp.global.RequestHandler;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    Context context;
    EditText sourceCityEditText, destinationCityEditText, pickUpDateEditText, returnDateEditText;
    TextView sourceCityTextView, destinationCityTextView, pickUpDateTextView, returnDateTextView;
    AutoCompleteTextView sourceCityAutoCompleteTextView, destinationCityAutoCompleteTextView;
    RadioButton roundTripRadioButton, oneWayRadioButton, localTripRadioButton;
    ArrayAdapter<String> sourceCityAdapter, destinationCityAdapter;
    List<String> sourceCityList = new ArrayList<>();
    List<String> destinationCityList = new ArrayList<>();
    ArrayList<Pricing> pricingArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        roundTripRadioButton = (RadioButton) findViewById(R.id.home_roundTripRadioButton);
        oneWayRadioButton = (RadioButton) findViewById(R.id.home_oneWayTripRadioButton);
        localTripRadioButton = (RadioButton) findViewById(R.id.home_localTripRadioButton);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (roundTripRadioButton.isChecked()) {
            sourceCityList = new RoundTripDataDB(context).getAllSourceCity();
        } else if (oneWayRadioButton.isChecked()) {
            sourceCityList = new OneWayDataDB(context).getAllSourceCity();
        } else if (localTripRadioButton.isChecked()) {
            sourceCityList = new LocalDataDB(context).getAllSourceCity();
        }
        sourceCityAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.sourceCityAutoCompleteTextView);
        sourceCityTextView = (TextView) findViewById(R.id.sourceCityTextView);
        sourceCityAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item);
        sourceCityAdapter.addAll(sourceCityList);
        for (String city : sourceCityList) {
            Log.d("sourceCityList", "city >>>" + city);
        }
        sourceCityAutoCompleteTextView.setThreshold(1);
        sourceCityAutoCompleteTextView.setAdapter(sourceCityAdapter);
        /*sourceCityAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                sourceCityAutoCompleteTextView.requestFocus();
                sourceCityAutoCompleteTextView.setCursorVisible(true);
                if (!"".equals(sourceCityAutoCompleteTextView.getText()))
                    sourceCityAutoCompleteTextView.setSelection(sourceCityAutoCompleteTextView.getText().length());
                if ("".equals(sourceCityTextView.getText()))
                    sourceCityTextView.setText("Pick Up City");
                sourceCityAutoCompleteTextView.setHint("");
                return true;
            }
        });*/
        sourceCityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                sourceCityAutoCompleteTextView.setText(adapterView.getItemAtPosition(position).toString());
                sourceCityAutoCompleteTextView.setError(null);
                sourceCityAutoCompleteTextView.setCursorVisible(false);
                if (roundTripRadioButton.isChecked()) {
                    destinationCityList = new RoundTripDataDB(context).getAllDestinationCityForSourceCity(sourceCityAutoCompleteTextView.getText().toString());
                } else if (oneWayRadioButton.isChecked()) {
                    destinationCityList = new OneWayDataDB(context).getAllDestinationCityForSource(sourceCityAutoCompleteTextView.getText().toString());
                }
                for (String city : destinationCityList) {
                    Log.d("destinationCityList", "city >>>" + city);
                }
                if (destinationCityList != null && destinationCityList.size() > 0)
                    destinationCityAdapter.addAll(destinationCityList);
                destinationCityAutoCompleteTextView.setAdapter(destinationCityAdapter);
            }
        });
        sourceCityAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sourceCityAutoCompleteTextView.setCursorVisible(true);
                if ("".equals(sourceCityTextView.getText()))
                    sourceCityTextView.setText("Pick Up City");
                sourceCityAutoCompleteTextView.setHint("");
            }
        });
        destinationCityTextView = (TextView) findViewById(R.id.destinationCityTextView);
        destinationCityAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.destinationCityAutoCompleteTextView);
        destinationCityAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item);
        destinationCityAutoCompleteTextView.setThreshold(1);
        Log.d("destinationCityAdapter", "getCount >>>" + destinationCityAdapter.getCount());
        /*destinationCityAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                destinationCityAutoCompleteTextView.requestFocus();
                destinationCityAutoCompleteTextView.setCursorVisible(true);
                if ("".equals(destinationCityTextView.getText())) {
                    destinationCityTextView.setText("Destination City");
                }
                if (!"".equals(destinationCityAutoCompleteTextView.getText())) {
                    destinationCityAutoCompleteTextView.setSelection(destinationCityAutoCompleteTextView.getText().length());
                }
                destinationCityAutoCompleteTextView.setHint("");
                return true;
            }
        });*/
        destinationCityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                destinationCityAutoCompleteTextView.setText(adapterView.getItemAtPosition(position).toString());
                destinationCityAutoCompleteTextView.setError(null);
                destinationCityAutoCompleteTextView.setCursorVisible(false);
            }
        });
        destinationCityAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationCityAutoCompleteTextView.setCursorVisible(true);
                if ("".equals(destinationCityTextView.getText())) {
                    destinationCityTextView.setText("Destination City");
                }
                destinationCityAutoCompleteTextView.setHint("");
            }
        });
        pickUpDateEditText = (EditText) findViewById(R.id.pickUpDateEditText);
        pickUpDateTextView = (TextView) findViewById(R.id.pickUpDateTextView);
        pickUpDateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if ("".equals(pickUpDateTextView.getText()))
                        pickUpDateTextView.setText("Pick Up Date");
                    pickUpDateEditText.setHint("");
                    Intent intent = new Intent(Home.this, BookingCalendar.class);
                    startActivityForResult(intent, 1007);
                }
                return true;
            }
        });
        returnDateEditText = (EditText) findViewById(R.id.returnDateEditText);
        returnDateTextView = (TextView) findViewById(R.id.returnDateTextView);
        returnDateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if ("".equals(returnDateTextView.getText()))
                        returnDateTextView.setText("Return Date");
                    returnDateEditText.setHint("");
                    Intent intent = new Intent(Home.this, BookingCalendar.class);
                    startActivityForResult(intent, 1009);
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("(data != null) ", " >>>>" + (data != null));
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = null;
        String receivedData = "";
        Log.d("(data != null) ", " >>>>" + (data != null));
        if (data != null) {
            bundle = data.getExtras();
        }
        if (bundle != null) {
            receivedData = bundle.getString("sentData");
        }
        /*if (requestCode == 1003) {
            if (resultCode == Activity.RESULT_OK) {
                sourceCityEditText.setText(receivedData);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                sourceCityEditText.setText("");
                sourceCityEditText.setHint("PickUp City");
            }
        }*/
        if (requestCode == 1007) {
            if ("".equals(pickUpDateTextView.getText()))
                pickUpDateTextView.setText("Pick Up Date");
            if (resultCode == Activity.RESULT_OK) {
                pickUpDateEditText.setText(receivedData);
                pickUpDateEditText.setError(null);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                pickUpDateEditText.setText("");
                pickUpDateEditText.setHint("Pick Up Date");
            }
        } else if (requestCode == 1009) {
            if ("".equals(returnDateTextView.getText()))
                returnDateTextView.setText("Return Date");
            if (resultCode == Activity.RESULT_OK) {
                returnDateEditText.setText(receivedData);
                returnDateEditText.setError(null);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                returnDateEditText.setText("");
                returnDateEditText.setHint("Return Date");
            }
        }
    }

    public void pickUpDateCalendar(View view) {
        Intent intent = new Intent(Home.this, BookingCalendar.class);
        startActivityForResult(intent, 1007);
    }

    public void returnDateCalendar(View view) {
        Intent intent = new Intent(Home.this, BookingCalendar.class);
        startActivityForResult(intent, 1009);
    }

    public void searchCab(View view) {
       /* Bundle bundle = new Bundle();
        Intent intent = new Intent(Home.this, CabSearchResult.class);
        bundle.putSerializable("pricingArrayList", new ArrayList<Pricing>());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String[] pickUpDateExtractor = pickUpDateEditText.getText().toString().split(",");
        String[] returnDateExtractor = returnDateEditText.getText().toString().split(",");
        Date pickUpDate = new Date();
        Date returnDate = new Date();
        String startDate;
        boolean errorCode = false;
        SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long numberOfDays = 0;
        try {
            if (pickUpDateExtractor.length > 1)
                pickUpDate = dateFormat.parse(pickUpDateExtractor[1].toString());
            if (returnDateExtractor.length > 1)
                returnDate = dateFormat.parse(returnDateExtractor[1].toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        numberOfDays = TimeUnit.DAYS.convert((returnDate.getTime() - pickUpDate.getTime()), TimeUnit.MILLISECONDS);
        Log.d("Home", "numberOfDays >>>" + numberOfDays);
        startDate = startDateFormat.format(pickUpDate);
        Log.d("Home", "startDate >>>" + startDate);
        if ((sourceCityAutoCompleteTextView.getVisibility() == View.VISIBLE && "".equals(sourceCityAutoCompleteTextView.getText().toString().trim())) &&
                (destinationCityAutoCompleteTextView.getVisibility() == View.VISIBLE && "".equals(destinationCityAutoCompleteTextView.getText().toString().trim())) &&
                (pickUpDateEditText.getVisibility() == View.VISIBLE && "".equals(pickUpDateEditText.getText().toString().trim())) &&
                (returnDateEditText.getVisibility() == View.VISIBLE && "".equals(returnDateEditText.getText().toString().trim()))) {
            sourceCityAutoCompleteTextView.setError("Pickup city required.");
            destinationCityAutoCompleteTextView.setError("");
            pickUpDateEditText.setError("");
            returnDateEditText.setError("");
            GlobalFunctions.showToast(context, "Please fill the required fields.");
            errorCode = true;
        } else {
            if (sourceCityAutoCompleteTextView.getVisibility() == View.VISIBLE && "".equals(sourceCityAutoCompleteTextView.getText().toString().trim())) {
                sourceCityAutoCompleteTextView.setFocusable(true);
                sourceCityAutoCompleteTextView.setError("Pickup city required.");
                sourceCityAutoCompleteTextView.requestFocus();
                errorCode = true;
            }
            if (destinationCityAutoCompleteTextView.getVisibility() == View.VISIBLE && "".equals(destinationCityAutoCompleteTextView.getText().toString().trim())) {
                destinationCityAutoCompleteTextView.setFocusable(true);
                destinationCityAutoCompleteTextView.setError("Destination city required.");
                destinationCityAutoCompleteTextView.requestFocus();
                errorCode = true;
            }
            if (pickUpDateEditText.getVisibility() == View.VISIBLE && "".equals(pickUpDateEditText.getText().toString().trim())) {
                pickUpDateEditText.setFocusable(true);
                pickUpDateEditText.setError("Pickup date required.");
                pickUpDateEditText.requestFocus();
                errorCode = true;
            }
            if (returnDateEditText.getVisibility() == View.VISIBLE && "".equals(returnDateEditText.getText().toString().trim())) {
                returnDateEditText.setFocusable(true);
                returnDateEditText.setError("Return date required.");
                returnDateEditText.requestFocus();
                errorCode = true;
            }
        }
        if (!errorCode) {
            SearchCabs searchCabs;
            if (roundTripRadioButton.isChecked()) {
                searchCabs = new SearchCabs(sourceCityAutoCompleteTextView.getText().toString().trim(), destinationCityAutoCompleteTextView.getText().toString().trim(),
                       Long.toString(numberOfDays), pickUpDateEditText.getText().toString().trim());
            } else if (oneWayRadioButton.isChecked()) {
                searchCabs = new SearchCabs(sourceCityAutoCompleteTextView.getText().toString().trim(), destinationCityAutoCompleteTextView.getText().toString().trim(),
                        "1", pickUpDateEditText.getText().toString().trim());
            } else {                            //local trip
                searchCabs = new SearchCabs(sourceCityAutoCompleteTextView.getText().toString().trim(), null, "1", pickUpDateEditText.getText().toString().trim());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                searchCabs.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                searchCabs.execute();
            }
        }
    }

    private class SearchCabs extends AsyncTask<String, Boolean, Boolean> {
        String pickUpCity, destinationCity, numberOfDays, pickUpDate, tripType, availableCabs = "";
        ProgressDialog progressDialog;
        HttpResponse httpResponse;

        SearchCabs(String pickUpCity, String destinationCity, String numberOfDays, String pickUpDate) {
            this.pickUpCity = pickUpCity;
            this.destinationCity = destinationCity;
            this.numberOfDays = numberOfDays;
            this.pickUpDate = pickUpDate;
            if (roundTripRadioButton.isChecked()) {
                tripType = "round-trip";
            } else if (oneWayRadioButton.isChecked()) {
                tripType = "one-way";
            } else if (localTripRadioButton.isChecked()) {
                tripType = "local";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Searching CabDTO");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<>();
            int statusCode = 403;
            params.add(new BasicNameValuePair("source", pickUpCity));
            params.add(new BasicNameValuePair("destination", destinationCity));
            params.add(new BasicNameValuePair("type", tripType));
            params.add(new BasicNameValuePair("days", numberOfDays));
            params.add(new BasicNameValuePair("start_date", pickUpDate));
            httpResponse = RequestHandler.makeRequest(context, GlobalFunctions.getUrl("SEARCH_CABS"), params);
            if (httpResponse != null) {
                statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    try {
                        availableCabs = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                        if (!availableCabs.equals("")) {
                            pricingArrayList = searchCabJsonToObject(availableCabs);
                            Log.d("Home", "pricingArrayList size >>>" + pricingArrayList.size());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        availableCabs = null;
                    }
                    Log.d("Home", "availableCabs >>>" + availableCabs);
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            if (aBoolean) {
                if (availableCabs == null || availableCabs.equals("")) {
                    GlobalFunctions.showToast(context, "Sorry no cabs available for searched route. Please retry with different route.");
                } else {
                    Intent intent = new Intent(Home.this, CabSearchResult.class);
//                    Bundle bundle  = new Bundle();
//                    bundle.putSerializable("pricingArrayList", pricingArrayList);
                    intent.putExtra("pricingArrayList", pricingArrayList);
//                    intent.putExtra("Bundle", bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
    }

    private ArrayList<Pricing> searchCabJsonToObject(String jsonString) {
        ArrayList<Pricing> pricings = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject cabsObject = jsonObject.getJSONObject("cabs");
            CabDTO cabDTO = new CabDTO();
            cabDTO.setSource(cabsObject.getString("source"));
            cabDTO.setDistance(cabsObject.getString("distance"));
            cabDTO.setRoute_id(cabsObject.getString("route_id"));
            cabDTO.setTime(cabsObject.getString("time"));
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray pricingJsonArray = jsonObject.getJSONArray("pricing");
            Pricing[] pricingObjectDTOs = objectMapper.readValue(pricingJsonArray.toString(), Pricing[].class);
            for (Pricing pricing : pricingObjectDTOs) {
                if (!(pricing.getExclusive_estimate() == 0d))
                    pricings.add(pricing);
            }
            Log.d("Home", "pricings size >>>" + pricings.size());
/*boolean savePricingResponse = new PricingDB(context).addPricing(pricingObjectDTO.getPricing(), null);
            if (!savePricingResponse) {
                Log.i("Error", "Unable to save pricing object");
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pricings;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("PricingArrayList", pricingArrayList);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
