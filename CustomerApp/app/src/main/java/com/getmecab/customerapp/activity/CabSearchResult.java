package com.getmecab.customerapp.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.getmecab.customerapp.R;
import com.getmecab.customerapp.database.Pricing;
import com.getmecab.customerapp.database.PricingObjectDTO;

import java.util.ArrayList;

public class CabSearchResult extends Activity {

    Context context;
    ListView listView;
    ArrayList<Pricing> pricingArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_search_result);
        context = this;
        Log.d("CabResult","onCreate");
        Bundle bundle = getIntent().getExtras();
        pricingArrayList = (ArrayList<Pricing>) bundle.getSerializable("pricingArrayList");
//        pricingArrayList = getIntent().getSerializableExtra("pricingArrayList");
        Log.d("CabResult","pricingArrayList size >>>"+pricingArrayList.size());
        listView = (ListView) findViewById(R.id.cabList);
        CabSearchResultAdapter cabSearchResultAdapter = new CabSearchResultAdapter(context, pricingArrayList);
        listView.setAdapter(cabSearchResultAdapter);
    }

}
