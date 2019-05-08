package ru.sberbank.converter.data.storage;

import ru.sberbank.converter.environment.PreferencesManager;

public class CurrencyStorageImpl implements CurrencyStorage {
    private static final String SOURCE_CURRENCY_PREF = "source_currency_pref";
    private static final String DEST_CURRENCY_PREF = "dest_currency_pref";

    private PreferencesManager preferencesManager;

    public CurrencyStorageImpl(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @Override
    public void setSourceCurrencyId(String currencyId) {
        preferencesManager.putString(SOURCE_CURRENCY_PREF, currencyId);
    }

    @Override
    public String getSourceCurrencyId() {
        return preferencesManager.getString(SOURCE_CURRENCY_PREF);
    }

    @Override
    public void setDestCurrencyId(String currencyId) {
        preferencesManager.putString(DEST_CURRENCY_PREF, currencyId);
    }

    @Override
    public String getDestCurrencyId() {
        return preferencesManager.getString(DEST_CURRENCY_PREF);
    }
}