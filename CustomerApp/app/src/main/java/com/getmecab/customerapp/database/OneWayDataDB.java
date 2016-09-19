package com.getmecab.customerapp.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.LinearLayout;

import com.getmecab.customerapp.global.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 17/9/16.
 */
public class OneWayDataDB extends SQLiteOpenHelper implements GlobalConstants {

    /*Database Columns*/
    private static final String id = "id";
    private static final String source = "source";
    private static final String destination = "destination";

    Context context;

    public OneWayDataDB(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_ONEWAYDATA = "CREATE TABLE IF NOT EXISTS " + TABLE_ONEWAYDATA + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + source + " TEXT,"
                + destination + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_ONEWAYDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ONEWAYDATA);
        onCreate(sqLiteDatabase);
    }

    public void addOneWayData(List<OneWayData> oneWayDataList, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            for (OneWayData oneWayData : oneWayDataList) {
                values.put(source, oneWayData.getSource());
                values.put(destination, oneWayData.getDestination());
                db.insert(TABLE_ONEWAYDATA, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<OneWayData> getAllOneWayData() {
        ArrayList<OneWayData> oneWayDataList = null;
        String selectQuery = "SELECT * FROM " + TABLE_ONEWAYDATA;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                oneWayDataList = new ArrayList<>();
                OneWayData oneWayData = new OneWayData();
                do {
                    oneWayData = setOneWayDataFromCursor(oneWayData, cursor);
                    oneWayDataList.add(oneWayData);
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
        return oneWayDataList;
    }

    private OneWayData setOneWayDataFromCursor(OneWayData oneWayData, Cursor cursor) {
        oneWayData.setSource(cursor.getString(1));
        oneWayData.setDestination(cursor.getString(2));
        return oneWayData;
    }
}
