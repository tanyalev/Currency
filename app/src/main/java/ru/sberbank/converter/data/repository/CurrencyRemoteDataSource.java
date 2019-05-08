package ru.sberbank.converter.data.repository;

import java.util.List;

import ru.sberbank.converter.data.network.CurrencyService;
import ru.sberbank.converter.entity.Currency;

public class CurrencyRemoteDataSource implements CurrencyDataSource {
    private CurrencyService service;

    public CurrencyRemoteDataSource(CurrencyService service) {
        this.service = service;
    }

    @Override
    public void loadCurrencies(CurrenciesCallback callback) {
        service.getCurrencies(callback);
    }

    @Override
    public void saveCurrencies(List<Currency> currencies) {
        throw new UnsupportedOperationException("SaveCurrencies should be called on local data source!");
    }

    @Override
    public void unsubscribe() {
        service.unsubscribe();
    }

    @Override
    public List<Currency> getCurrencies() {
        throw new UnsupportedOperationException("GetCurrencies should be called on local data source!");
    }

    @Override
    public Currency getCurrency(String currencyId) {
        throw new UnsupportedOperationException("GetCurrency should be called on local data source!");
    }

    @Override
    public Currency getDefaultCurrency() {
        throw new UnsupportedOperationException("GetDefaultCurrency should be called on local data source!");
    }
}