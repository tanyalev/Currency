package ru.sberbank.converter.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.entity.Currency;

public class CurrencyDao {
    private CurrencyDatabaseHelper dbHelper;

    public CurrencyDao(@NonNull CurrencyDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Функция получения первого элемента в БД, используется для того,
     * чтобы выбрать валюту по-умолчанию
     * @return Currency, который является первым в таблице
     */
    public Currency getFirstItem() {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Currency currency = null;
        Cursor cursor = db.query(CurrencyContract.CURRENCIES_TABLE,
                new String[]{"*"}, null, null, null, null, null, null);
        try {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int idColumnIndex = cursor.getColumnIndex(CurrencyContract.ID_COLUMN);
                    int charCodeColumnIndex = cursor.getColumnIndex(CurrencyContract.CHARCODE_COLUMN);
                    int numCodeColumnIndex = cursor.getColumnIndex(CurrencyContract.NUMCODE_COLUMN);
                    int nominalColumnIndex = cursor.getColumnIndex(CurrencyContract.NOMINAL_COLUMN);
                    int nameColumnIndex = cursor.getColumnIndex(CurrencyContract.NAME_COLUMN);
                    int valueColumnIndex = cursor.getColumnIndex(CurrencyContract.VALUE_COLUMN);
                    if (cursor.moveToFirst()) {
                        currency = new Currency();
                        currency.setId(cursor.getString(idColumnIndex));
                        currency.setCharCode(cursor.getString(charCodeColumnIndex));
                        currency.setNumCode(cursor.getString(numCodeColumnIndex));
                        currency.setNominal(cursor.getInt(nominalColumnIndex));
                        currency.setName(cursor.getString(nameColumnIndex));
                        currency.setValue(cursor.getFloat(valueColumnIndex));
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return currency;
    }

    public Currency getItem(String itemId) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Currency currency = null;
        final Cursor cursor = db.query(
                CurrencyContract.CURRENCIES_TABLE, null,
                CurrencyContract.ID_COLUMN + "=?", new String[]{itemId}, null, null, null);
        try {
            int idColumnIndex = cursor.getColumnIndex(CurrencyContract.ID_COLUMN);
            int charCodeColumnIndex = cursor.getColumnIndex(CurrencyContract.CHARCODE_COLUMN);
            int numCodeColumnIndex = cursor.getColumnIndex(CurrencyContract.NUMCODE_COLUMN);
            int nominalColumnIndex = cursor.getColumnIndex(CurrencyContract.NOMINAL_COLUMN);
            int nameColumnIndex = cursor.getColumnIndex(CurrencyContract.NAME_COLUMN);
            int valueColumnIndex = cursor.getColumnIndex(CurrencyContract.VALUE_COLUMN);
            if (cursor.moveToFirst()) {
                currency = new Currency();
                currency.setId(cursor.getString(idColumnIndex));
                currency.setCharCode(cursor.getString(charCodeColumnIndex));
                currency.setNumCode(cursor.getString(numCodeColumnIndex));
                currency.setNominal(cursor.getInt(nominalColumnIndex));
                currency.setName(cursor.getString(nameColumnIndex));
                currency.setValue(cursor.getFloat(valueColumnIndex));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return currency;
    }

    @NonNull
    public List<Currency> getList() {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final List<Currency> currencies = new ArrayList<>();
        final Cursor cursor = db.query(
                CurrencyContract.CURRENCIES_TABLE, null, null, null, null, null, null);
        try {
            int idColumnIndex = cursor.getColumnIndex(CurrencyContract.ID_COLUMN);
            int charCodeColumnIndex = cursor.getColumnIndex(CurrencyContract.CHARCODE_COLUMN);
            int numCodeColumnIndex = cursor.getColumnIndex(CurrencyContract.NUMCODE_COLUMN);
            int nominalColumnIndex = cursor.getColumnIndex(CurrencyContract.NOMINAL_COLUMN);
            int nameColumnIndex = cursor.getColumnIndex(CurrencyContract.NAME_COLUMN);
            int valueColumnIndex = cursor.getColumnIndex(CurrencyContract.VALUE_COLUMN);
            if (cursor.moveToFirst()) {
                do {
                    final Currency currency = new Currency();
                    currency.setId(cursor.getString(idColumnIndex));
                    currency.setCharCode(cursor.getString(charCodeColumnIndex));
                    currency.setNumCode(cursor.getString(numCodeColumnIndex));
                    currency.setNominal(cursor.getInt(nominalColumnIndex));
                    currency.setName(cursor.getString(nameColumnIndex));
                    currency.setValue(cursor.getFloat(valueColumnIndex));
                    currencies.add(currency);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return currencies;
    }

    public void saveList(@NonNull List<Currency> currencies) {
        if (!currencies.isEmpty()) {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            for (Currency currency : currencies) {
                ContentValues cv = new ContentValues(6);
                cv.put(CurrencyContract.ID_COLUMN, currency.getId());
                cv.put(CurrencyContract.NUMCODE_COLUMN, currency.getNumCode());
                cv.put(CurrencyContract.CHARCODE_COLUMN, currency.getCharCode());
                cv.put(CurrencyContract.NOMINAL_COLUMN, currency.getNominal());
                cv.put(CurrencyContract.NAME_COLUMN, currency.getName());
                cv.put(CurrencyContract.VALUE_COLUMN, currency.getValue());
                db.replace(CurrencyContract.CURRENCIES_TABLE, null, cv);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }
}