package com.getmecab.customerapp.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.getmecab.customerapp.global.GlobalConstants;
import com.getmecab.customerapp.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 17/9/16.
 */
public class RoundTripDataDB extends SQLiteOpenHelper implements GlobalConstants{

    /*DataBase Columns*/
    private static final String id = "id";
    private static final String source = "source";
    private static final String destination = "destination";

    Context context;

    public RoundTripDataDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_ROUNDTRIPDATA = "CREATE TABLE IF NOT EXISTS " + TABLE_ROUNDTRIPDATA + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + source + " TEXT,"
                + destination + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_ROUNDTRIPDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUNDTRIPDATA);
        onCreate(sqLiteDatabase);
    }

    public void addRoundTripData(List<RoundTripData> roundTripDataList, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            for (RoundTripData roundTripData : roundTripDataList) {
                values.put(source, roundTripData.getSource());
                values.put(destination, roundTripData.getDestination());
                db.insert(TABLE_ROUNDTRIPDATA, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RoundTripData> getAllRoundTripData() {
        ArrayList<RoundTripData> roundTripDataList = null;
        String selectQuery = "SELECT * FROM " + TABLE_ROUNDTRIPDATA;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                roundTripDataList = new ArrayList<>();
                RoundTripData roundTripData = new RoundTripData();
                do {
                    roundTripData = setROundTripDataFromCursor(roundTripData, cursor);
                    roundTripDataList.add(roundTripData);
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
        return roundTripDataList;
    }

    public ArrayList<String> getAllSourceCity() {
        String selectQuery = "SELECT DISTINCT " + source + " FROM " + TABLE_ROUNDTRIPDATA;
        Log.i("RoundTripDataDB ", "getAllSourceCity selectQuery >>>"+selectQuery);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<String> sourceCities = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                sourceCities = new ArrayList<>();
                do {
                    sourceCities.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
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
        return sourceCities;
    }

    public ArrayList<String> getAllDestinationCityForSourceCity(String sourceCity) {
        String selectQuery = "SELECT DISTINCT " + destination + " FROM " + TABLE_ROUNDTRIPDATA + " WHERE " + source + " = " + '"' + sourceCity + '"' + " COLLATE NOCASE";
        Log.i("RoundTripDataDB ", "getAllDestinationCityForSourceCity selectQuery >>>"+selectQuery);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<String> destinationCities = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                destinationCities = new ArrayList<>();
                do {
                    destinationCities.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
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
        return destinationCities;
    }

    private RoundTripData setROundTripDataFromCursor(RoundTripData roundTripData, Cursor cursor) {
        roundTripData.setSource(cursor.getString(1));
        roundTripData.setDestination(cursor.getString(2));
        return roundTripData;
    }
}
