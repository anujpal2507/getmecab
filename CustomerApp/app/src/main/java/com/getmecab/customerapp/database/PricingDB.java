package com.getmecab.customerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.getmecab.customerapp.global.GlobalConstants;

import java.util.ArrayList;
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
    private static final String daCharge = "daCharge";
    private static final String daNightCharge = "daNightCharge";
    private static final String distance = "distance";
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
                + daCharge + " REAL,"
                + daNightCharge + " REAL,"
                + distance + " REAL,"
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

    public boolean addPricing(List<Pricing> pricingList, SQLiteDatabase db) {
        boolean saveSuccessfully = false;
        try {
            if (db == null) {
                db = this.getWritableDatabase();
                db.beginTransaction();
            }
            ContentValues values = new ContentValues();
            for (Pricing pricing : pricingList) {
                values.put(finalPrice, pricing.getFinal_Price());
                values.put(gmcPrice, pricing.getGMC_Price());
                values.put(perKmChargeUser, pricing.getPer_km_charge_user());
                values.put(ac, pricing.getAc());
                values.put(daCharge, pricing.getDa_charge());
                values.put(daNightCharge, pricing.getDa_night_charge());
                values.put(distance, pricing.getDistance());
                values.put(exclusiveEstimate, pricing.getExclusive_estimate());
                values.put(exclusivePrice, pricing.getExclusive_price());
                values.put(finalStateTax, pricing.getFinal_Price());
                values.put(modelName, pricing.getModel_name());
                values.put(perDayLimit, pricing.getPer_day_limit());
                values.put(pricePerKm, pricing.getPrice_per_km());
                values.put(seats, pricing.getSeats());
                values.put(serviceTax, pricing.getService_tax());
                values.put(serviceTaxPercentage, pricing.getService_tax_percentage());
                values.put(totalDA, pricing.getTotal_DA());
                values.put(totalExclusivePrice, pricing.getTotal_exclusive_price());
                values.put(totalNightDA, pricing.getTotal_night_DA());
                db.insert(TABLE_PRICING, null, values);
                saveSuccessfully = true;
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
                db = null;
            }
        }
        return saveSuccessfully;
    }

    public List<Pricing> getAllPricingList() {
        String selectQuery = "SELECT * FROM " + TABLE_PRICING;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Pricing> pricings = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                pricings = new ArrayList<>();
                do {
                    Pricing pricing = new Pricing();
                    pricing.setFinal_Price(cursor.getDouble(1));
                    pricing.setGMC_Price(cursor.getDouble(2));
                    pricing.setPer_km_charge_user(cursor.getDouble(3));
                    pricing.setAc(cursor.getInt(4));
                    pricing.setDa_charge(cursor.getDouble(5));
                    pricing.setDa_night_charge(cursor.getDouble(6));
                    pricing.setDistance(cursor.getDouble(7));
                    pricing.setExclusive_estimate(cursor.getDouble(8));
                    pricing.setExclusive_price(cursor.getDouble(9));
                    pricing.setFinal_Price(cursor.getDouble(10));
                    pricing.setModel_name(cursor.getString(11));
                    pricing.setPer_day_limit(cursor.getDouble(12));
                    pricing.setPrice_per_km(cursor.getDouble(13));
                    pricing.setSeats(cursor.getInt(14));
                    pricing.setService_tax(cursor.getDouble(15));
                    pricing.setService_tax_percentage(cursor.getDouble(16));
                    pricing.setTotal_DA(cursor.getDouble(17));
                    pricing.setTotal_exclusive_price(cursor.getDouble(18));
                    pricing.setTotal_night_DA(cursor.getDouble(19));
                    pricings.add(pricing);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return pricings;
    }
}
