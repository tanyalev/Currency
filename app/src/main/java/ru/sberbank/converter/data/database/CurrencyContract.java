package ru.sberbank.converter.data.database;

class CurrencyContract {
    static final String DB_NAME = "currency_converter";
    static final int DB_VERSION = 1;

    static final String CURRENCIES_TABLE = "currencies";
    static final String ID_COLUMN = "id";
    static final String NUMCODE_COLUMN = "numcode";
    static final String CHARCODE_COLUMN = "charcode";
    static final String NOMINAL_COLUMN = "nominal";
    static final String NAME_COLUMN = "name";
    static final String VALUE_COLUMN = "value";
}