package ru.sberbank.converter.di;

import android.content.Context;

import ru.sberbank.converter.data.CurrencyFakeDataSource;
import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.data.storage.CurrencyStorageImpl;
import ru.sberbank.converter.environment.PreferencesManager;

public class Injection {
    private static CurrencyRepository currencyRepository;

    public static CurrencyRepository provideCurrencyRepository(Context context) {
        if (currencyRepository == null) {
            currencyRepository = new CurrencyRepository(
                    new CurrencyFakeDataSource(),
                    new CurrencyFakeDataSource(),
                    new CurrencyStorageImpl(new PreferencesManager(context)));
        }
        return currencyRepository;
    }
}