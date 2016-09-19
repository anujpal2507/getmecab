package com.getmecab.customerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.getmecab.customerapp.global.GlobalConstants;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 20/9/16.
 */
public class UserDB extends SQLiteOpenHelper implements GlobalConstants {

    /*DataBase Columns*/
    private static final String id = "id";
    private static final String name = "name";
    private static final String email = "email";
    private static final String mobileNumber = "mobileNumber";

    Context context;

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + name + " TEXT,"
                + email + " TEXT,"
                + mobileNumber + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            values.put(name, user.getName());
            values.put(email, user.getEmail());
            values.put(mobileNumber, user.getMobileNumber());
            db.insert(TABLE_USER, null, values);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        String selectQuery = "SELECT * FROM USER";
        SQLiteDatabase db = null;
        Cursor cursor = null;
        User user = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setMobileNumber(cursor.getString(3));
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
        return user;
    }
}
