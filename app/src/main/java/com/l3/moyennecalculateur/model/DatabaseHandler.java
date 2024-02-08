package com.l3.moyennecalculateur.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String MODULE_KEY = "id";
    public static final String MODULE_INTITULE = "intitule";
    public static final String MODULE_COEFFICIENT = "coefficient";
    public static final String MODULE_EMD = "emd";
    public static final String MODULE_TD = "td";
    public static final String MODULE_TP = "tp";
    public static final String MODULE_MOYENNE = "moyenne";
    public static final String MODULE_TABLE_NAME = "module";

    public static final String MODULE_TABLE_CREATE =
            "CREATE TABLE " + MODULE_TABLE_NAME + " (" +
                    MODULE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MODULE_INTITULE + " TEXT UNIQUE, " +
                    MODULE_COEFFICIENT + " INT, " +
                    MODULE_EMD + " REAL, " +
                    MODULE_TD + " REAL, " +
                    MODULE_TP + " REAL, " +
                    MODULE_MOYENNE + " INT);";

    public static final String MODULE_TABLE_DROP = "DROP TABLE IF EXISTS " + MODULE_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MODULE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MODULE_TABLE_DROP);
        onCreate(db);
    }
}
