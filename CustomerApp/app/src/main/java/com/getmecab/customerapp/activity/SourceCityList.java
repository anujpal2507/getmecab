package com.getmecab.customerapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.getmecab.customerapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SourceCityList extends AppCompatActivity {

    Context context;
    TextView cityNameTextView;
    ImageView sourceCityYellowImageView, sourceCityBlueImageView;
    LinearLayout parentLinearLayout,rowLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_city_list);
        context = this;
        List<String> sourceCityList = new ArrayList<>();
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
//        renderSourceCities(sourceCityList);
        renderSourceCity(sourceCityList);
        cityNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Log.d("CityName ",cityNameTextView.getText().toString());
                intent.putExtra("sentData",cityNameTextView.getText());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
       /* cityNameTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent();
                    intent.putExtra("CityName",cityNameTextView.getText());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                return true;
            }
        });*/
    }

    private void renderSourceCity(List<String> sourceCityList) {
        parentLinearLayout = (LinearLayout) findViewById(R.id.sourceCityListLinearLayout);
        LayoutInflater inflater;
        int i = 0;
        for (String sourceCity : sourceCityList) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowLinearLayout = (LinearLayout) inflater.inflate(R.layout.source_city_row,  null, false);
            cityNameTextView = (TextView) rowLinearLayout.findViewById(R.id.sourceCityRowTextView);
            sourceCityYellowImageView = (ImageView) rowLinearLayout.findViewById(R.id.sourceCityYellowImageView);
            sourceCityBlueImageView = (ImageView) rowLinearLayout.findViewById(R.id.sourceCityBlueImageView);
            if ((i%2) == 0) {
                rowLinearLayout.setBackgroundColor(Color.LTGRAY);
                sourceCityBlueImageView.setVisibility(View.VISIBLE);
                sourceCityYellowImageView.setVisibility(View.INVISIBLE);
            } else {
                rowLinearLayout.setBackgroundColor(Color.GRAY);
                sourceCityBlueImageView.setVisibility(View.INVISIBLE);
                sourceCityYellowImageView.setVisibility(View.VISIBLE);
            }
            i++;
            cityNameTextView.setText(sourceCity);
            parentLinearLayout.addView(rowLinearLayout);
        }
    }

    private void renderSourceCities(List<String> sourceCityList) {
        ListView listView = (ListView) findViewById(R.id.sourceCityListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.source_city_row, sourceCityList);
        listView.setAdapter(adapter);
    }
}
