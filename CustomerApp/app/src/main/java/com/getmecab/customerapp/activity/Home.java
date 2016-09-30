package com.getmecab.customerapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.getmecab.customerapp.R;
import com.getmecab.customerapp.global.BookingCalendar;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText sourceCityEditText, destinationCityEditText, pickUpDateEditText, returnDateEditText;
    TextView sourceCityTextView, destinationCityTextView, pickUpDateTextView, returnDateTextView;
    Context context;
    AutoCompleteTextView sourceCityAutoCompleteTextView;
    ArrayAdapter<String> sourceCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
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
        final List<String> sourceCityList = new ArrayList<>();
        sourceCityList.add("DELHI");
        sourceCityList.add("LUCKNOW");
        sourceCityList.add("KANPUR");
        sourceCityList.add("NOIDA");
        sourceCityList.add("GURGOAN");
        sourceCityList.add("BANGALORE");
        sourceCityList.add("MUMBAI");
        sourceCityList.add("JAIPUR");
        sourceCityList.add("PUNE");
        sourceCityList.add("MYSORE");
        sourceCityList.add("GHAZIABAD");
        sourceCityList.add("AGRA");
        sourceCityList.add("CHANDIGARH");
        sourceCityList.add("CHENNAI");
        sourceCityAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.sourceCityAutoCompleteTextView);
        sourceCityAdapter = new ArrayAdapter<String>(context , R.layout.support_simple_spinner_dropdown_item);
        sourceCityAdapter.addAll(sourceCityList);
        sourceCityAutoCompleteTextView.setThreshold(1);
        sourceCityAutoCompleteTextView.setAdapter(sourceCityAdapter);
        sourceCityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                sourceCityAutoCompleteTextView.setText(sourceCityList.get(position));
            }
        });
        sourceCityAutoCompleteTextView.setOnItemSelectedListener(this);
        sourceCityAutoCompleteTextView.setOnClickListener(this);
        sourceCityEditText = (EditText) findViewById(R.id.sourceCityEditText);
        sourceCityTextView = (TextView) findViewById(R.id.sourceCityTextView);
        sourceCityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(sourceCityTextView.getText()))
                    sourceCityTextView.setText("Pick Up City");
                sourceCityEditText.setHint("");
                Intent intent = new Intent(Home.this, SourceCityList.class);
                startActivityForResult(intent, 1003);
            }
        });
        destinationCityEditText = (EditText) findViewById(R.id.destinationCityEditText);
        destinationCityTextView = (TextView) findViewById(R.id.destinationCityTextView);
        destinationCityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(destinationCityTextView.getText()))
                    destinationCityTextView.setText("Destination City");
                destinationCityEditText.setHint("");
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
        Log.d("(data != null) "," >>>>"+(data != null));
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = null;
        String receivedData = "";
        Log.d("(data != null) "," >>>>"+(data != null));
        if (data != null) {
            bundle = data.getExtras();
        }
        if (bundle != null) {
            receivedData =  bundle.getString("sentData");
        }
        if (requestCode == 1003) {
            if (resultCode == Activity.RESULT_OK) {
                sourceCityEditText.setText(receivedData);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                sourceCityEditText.setText("");
                sourceCityEditText.setHint("PickUp City");
            }
        }
        if (requestCode == 1007) {
                if ("".equals(pickUpDateTextView.getText()))
                    pickUpDateTextView.setText("Pick Up Date");
                if (resultCode == Activity.RESULT_OK) {
                    pickUpDateEditText.setText(receivedData);
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
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                returnDateEditText.setText("");
                pickUpDateEditText.setHint("Return Date");
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
        Intent intent = new Intent(Home.this, CabSearchResult.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {

    }
}
