package com.majeedr.tanwih.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.majeedr.tanwih.database.contract.TanwihContract.TanwihEntry;

/**
 * Main database helper to keep tanwih entry data.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TanwihEntry.TABLE_NAME + " (" +
                    TanwihEntry._ID + " INTEGER PRIMARY KEY," +
                    TanwihEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TanwihEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TanwihEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    TanwihEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                    TanwihEntry.COLUMN_NAME_LINKS + TEXT_TYPE + COMMA_SEP +
                    TanwihEntry.COLUMN_NAME_INSYNC + INT_TYPE + COMMA_SEP +
                    TanwihEntry.COLUMN_NAME_OPERATION + INT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TanwihEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TanwihData.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
