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
 * Created by anuj "email : anujpal2507@gmail.com" on 17/9/16.
 */
public class LocalDataDB extends SQLiteOpenHelper implements GlobalConstants {

    /*DataBase Columns */
    private static final String id = "id";
    private static final String source = "source";
    private static final String destination = "destination";

    Context context;

    public LocalDataDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_LOCALDATA = "CREATE TABLE IF NOT EXISTS " + TABLE_LOCALDATA + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + source + " TEXT,"
                + destination + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_LOCALDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALDATA);
        onCreate(sqLiteDatabase);
    }

    public void addLocalData(LocalData localData, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            values.put(source, localData.getSource());
            values.put(destination, localData.getDestination());
            db.insert(TABLE_LOCALDATA, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLocalData(List<LocalData> localDataList, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            for (LocalData localData : localDataList) {
                values.put(source, localData.getSource());
                values.put(destination, localData.getDestination());
                db.insert(TABLE_LOCALDATA, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LocalData> getAllLocalData() {
        ArrayList<LocalData> localDataList = null;
        String selectQuery = "SELECT * FROM " + TABLE_LOCALDATA;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                localDataList = new ArrayList<>();
                LocalData localData = new LocalData();
                do {
                    localData = setLocalDataFromCursor(localData, cursor);
                    localDataList.add(localData);
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
        return localDataList;
    }

    private LocalData setLocalDataFromCursor(LocalData localData, Cursor cursor) {
        localData.setSource(cursor.getString(1));
        localData.setDestination(cursor.getString(2));
        return localData;
    }
}
