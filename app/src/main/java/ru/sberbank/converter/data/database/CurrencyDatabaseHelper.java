package ru.sberbank.converter.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrencyDatabaseHelper extends SQLiteOpenHelper {
    public CurrencyDatabaseHelper(Context context) {
        super(context, CurrencyContract.DB_NAME, null, CurrencyContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CurrencyContract.CURRENCIES_TABLE + " ("
                + CurrencyContract.ID_COLUMN + " VARCHAR primary key,"
                + CurrencyContract.NUMCODE_COLUMN + " VARCHAR(3),"
                + CurrencyContract.CHARCODE_COLUMN + " VARCHAR(3),"
                + CurrencyContract.NOMINAL_COLUMN + " INTEGER,"
                + CurrencyContract.NAME_COLUMN + " VARCHAR,"
                + CurrencyContract.VALUE_COLUMN + " DECIMAL" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}