package ru.sberbank.converter.di;

import android.content.Context;

import ru.sberbank.converter.data.database.CurrencyDao;
import ru.sberbank.converter.data.database.CurrencyDatabaseHelper;
import ru.sberbank.converter.data.network.CurrencyService;
import ru.sberbank.converter.data.network.CurrencyServiceImpl;
import ru.sberbank.converter.data.network.HttpClient;
import ru.sberbank.converter.data.network.adapter.CurrencyCoursesDeserializer;
import ru.sberbank.converter.data.repository.CurrencyLocalDataSource;
import ru.sberbank.converter.data.repository.CurrencyRemoteDataSource;
import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.data.storage.CurrencyStorageImpl;
import ru.sberbank.converter.environment.PreferencesManager;

public class Injection {
    private static final String SERVER_URL = "http://www.cbr.ru/scripts/xml_daily.asp";
    private static CurrencyRepository currencyRepository;

    public static CurrencyRepository provideCurrencyRepository(Context context) {
        if (currencyRepository == null) {
            final HttpClient httpClient = new HttpClient();
            final CurrencyCoursesDeserializer deserializer = new CurrencyCoursesDeserializer();
            final CurrencyService currencyService = new CurrencyServiceImpl(
                    httpClient, deserializer, SERVER_URL);

            final CurrencyDatabaseHelper dbHelper = new CurrencyDatabaseHelper(context);
            final CurrencyDao currencyDao = new CurrencyDao(dbHelper);

            currencyRepository = new CurrencyRepository(
                    new CurrencyRemoteDataSource(currencyService),
                    new CurrencyLocalDataSource(currencyDao),
                    new CurrencyStorageImpl(new PreferencesManager(context)));
        }
        return currencyRepository;
    }
}