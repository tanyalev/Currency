package ru.sberbank.converter.data.repository;

import java.util.List;

import ru.sberbank.converter.data.storage.CurrencyStorage;
import ru.sberbank.converter.entity.Currency;

public class CurrencyRepository {
    private CurrencyDataSource remoteDataSource;
    private CurrencyDataSource localDataSource;
    private CurrencyStorage currencyStorage;

    public CurrencyRepository(CurrencyDataSource remoteDataSource,
                              CurrencyDataSource localDataSource, CurrencyStorage currencyStorage) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.currencyStorage = currencyStorage;
    }

    public void refreshCurrencies(final LoadCurrenciesCallback callback) {
        remoteDataSource.loadCurrencies(new CurrencyDataSource.CurrenciesCallback() {
            @Override
            public void onLoadCompleted(List<Currency> currencies) {
                localDataSource.saveCurrencies(currencies);
                callback.onLoadCompleted();
            }

            @Override
            public void onLoadError() {
                callback.onLoadError();
            }
        });
    }

    public List<Currency> getCurrencies() {
        return localDataSource.getCurrencies();
    }

    public Currency getCurrency(String currencyId) {
        return localDataSource.getCurrency(currencyId);
    }

    public Currency getSourceCurrency() {
        final String sourceCurrencyId = currencyStorage.getSourceCurrencyId();
        if (sourceCurrencyId != null) {
            return getCurrency(sourceCurrencyId);
        }
        return localDataSource.getDefaultCurrency();
    }

    public Currency getDestCurrency() {
        final String destCurrencyId = currencyStorage.getDestCurrencyId();
        if (destCurrencyId != null) {
            return getCurrency(destCurrencyId);
        }
        return localDataSource.getDefaultCurrency();
    }

    public void selectSourceCurrency(String currencyId) {
        currencyStorage.setSourceCurrencyId(currencyId);
    }

    public void selectDestCurrency(String currencyId) {
        currencyStorage.setDestCurrencyId(currencyId);
    }

    public void unsubscribe() {
        remoteDataSource.unsubscribe();
    }

    public interface LoadCurrenciesCallback {
        void onLoadCompleted();

        void onLoadError();
    }
}