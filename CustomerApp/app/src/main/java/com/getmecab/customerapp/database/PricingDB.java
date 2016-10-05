package com.getmecab.customerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.getmecab.customerapp.global.GlobalConstants;
import com.google.gson.FieldNamingPolicy;

import java.util.List;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 6/10/16.
 */
public class PricingDB extends SQLiteOpenHelper implements GlobalConstants {

    Context context;
    private static final String id = "id";
    private static final String finalPrice = "finalPrice";
    private static final String gmcPrice = "gmcPrice";
    private static final String perKmChargeUser = "perKmChargeUser";
    private static final String ac = "ac";
    private static final String cabList = "cabList";
    private static final String daCharge = "daCharge";
    private static final String daNightCharge = "daNightCharge";
    private static final String distance = "distance";
    private static final String driverCharge = "driverCharge";
    private static final String exclusiveEstimate = "exclusiveEstimate";
    private static final String exclusivePrice = "exclusivePrice";
    private static final String finalStateTax = "finalStateTax";
    private static final String modelName = "modelName";
    private static final String perDayLimit = "perDayLimit";
    private static final String pricePerKm = "pricePerKm";
    private static final String seats = "seats";
    private static final String serviceTax = "serviceTax";
    private static final String serviceTaxPercentage = "serviceTaxPercentage";
    private static final String totalDA = "totalDA";
    private static final String totalExclusivePrice = "totalExclusivePrice";
    private static final String totalNightDA = "totalNightDA";

    public PricingDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_PRICE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRICING + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + finalPrice + " REAL,"
                + gmcPrice + " REAL,"
                + perKmChargeUser + " REAL,"
                + ac + " INT,"
                + cabList + " TEXT,"
                + daCharge + " REAL,"
                + daNightCharge + " REAL,"
                + distance + " REAL,"
                + driverCharge + " REAL,"
                + exclusiveEstimate + " REAL,"
                + exclusivePrice + " REAL,"
                + finalStateTax + " REAL,"
                + modelName + " TEXT,"
                + perDayLimit + " REAL,"
                + pricePerKm + " REAL,"
                + seats + " INTEGER,"
                + serviceTax + " REAL,"
                + serviceTaxPercentage + " REAL,"
                + totalDA + " REAL,"
                + totalExclusivePrice + " REAL,"
                + totalNightDA + " REAL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_PRICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_PRICING);
        onCreate(sqLiteDatabase);
    }

    public void addPrincing(List<Pricing> pricingList, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            for (Pricing pricing : pricingList) {
                values.put(finalPrice, pricing.getFinalPrice());
                values.put(gmcPrice, pricing.getGmcPrice());
                values.put(perKmChargeUser, pricing.getPerKmChargeUser());
                values.put(ac, pricing.getAc());
                values.put(cabList, pricing.getCabList());
                values.put(daCharge, pricing.getDaCharge());
                values.put(daNightCharge, pricing.getDaNightCharge());
                values.put(distance, pricing.getDistance());
                values.put(driverCharge, pricing.getDriverCharge());
                values.put(exclusiveEstimate, pricing.getExclusiveEstimate());
                values.put(exclusivePrice, pricing.getExclusivePrice());
                values.put(finalStateTax, pricing.getFinalStateTax());
                values.put(modelName, pricing.getModelName());
                values.put(perDayLimit, pricing.getPerDayLimit());
                values.put(pricePerKm, pricing.getPricePerKm());
                values.put(seats, pricing.getSeats());
                values.put(serviceTax, pricing.getServiceTax());
                values.put(serviceTaxPercentage, pricing.getServiceTaxPercentage());
                values.put(totalDA, pricing.getTotalDA());
                values.put(totalExclusivePrice, pricing.getTotalExclusivePrice());
                values.put(totalNightDA, pricing.getTotalNightDA());
                db.insert(TABLE_PRICING, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
