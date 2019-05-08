package ru.sberbank.converter.data.repository;

import java.util.List;

import ru.sberbank.converter.data.database.CurrencyDao;
import ru.sberbank.converter.entity.Currency;

public class CurrencyLocalDataSource implements CurrencyDataSource {
    private CurrencyDao currencyDao;

    public CurrencyLocalDataSource(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public void loadCurrencies(CurrenciesCallback callback) {
        throw new UnsupportedOperationException("LoadCurrencies should be called on remote data source!");
    }

    @Override
    public void saveCurrencies(List<Currency> currencies) {
        currencyDao.saveList(currencies);
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyDao.getList();
    }

    @Override
    public Currency getCurrency(String currencyId) {
        return currencyDao.getItem(currencyId);
    }

    @Override
    public Currency getDefaultCurrency() {
        return currencyDao.getFirstItem();
    }
}