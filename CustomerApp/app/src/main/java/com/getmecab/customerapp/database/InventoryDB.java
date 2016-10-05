package com.getmecab.customerapp.database;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.getmecab.customerapp.global.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 5/10/16.
 */
public class InventoryDB extends SQLiteOpenHelper implements GlobalConstants {

    /*DataBase Columns*/
    private static final String id = "id";
    private static final String modelName = "modelName";
    private static final String routeId = "routeId";
    private static final String seats = "seats";

    Context context;

    public InventoryDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_INVENTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_INVENTORY + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + modelName + " TEXT,"
                + routeId + " INTEGER,"
                + seats + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_INVENTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        onCreate(sqLiteDatabase);
    }

    public void addInventory(List<Inventory> inventoryList, SQLiteDatabase db) {
        try {
            ContentValues values = new ContentValues();
            for (Inventory inventory : inventoryList) {
                values.put(modelName, inventory.getModelName());
                values.put(routeId, inventory.getRouteId());
                values.put(seats, inventory.getSeats());
                db.insert(TABLE_INVENTORY, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Inventory> getAllInventories() {
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Inventory> inventories = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Inventory inventory = new Inventory();
                    inventory.setModelName(cursor.getString(1));
                    inventory.setRouteId(cursor.getInt(2));
                    inventory.setSeats(cursor.getInt(3));
                    inventories.add(inventory);
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
        return inventories;
    }
}
