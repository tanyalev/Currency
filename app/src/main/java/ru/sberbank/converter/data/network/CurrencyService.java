package ru.sberbank.converter.data.network;

import ru.sberbank.converter.data.repository.CurrencyDataSource;

public interface CurrencyService {
    void getCurrencies(CurrencyDataSource.CurrenciesCallback callback);

    void unsubscribe();
}