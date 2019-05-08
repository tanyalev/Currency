package ru.sberbank.converter.data;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.data.repository.CurrencyDataSource;
import ru.sberbank.converter.entity.Currency;

public class CurrencyFakeDataSource implements CurrencyDataSource {
    private List<Currency> currencyList;

    public CurrencyFakeDataSource() {
        currencyList = getFakeCurrencyList();
    }

    @Override
    public void loadCurrencies(CurrenciesCallback callback) {
        callback.onLoadCompleted(currencyList);
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyList;
    }

    @Override
    public Currency getCurrency(String currencyId) {
        for (Currency c : currencyList) {
            if (c.getId().equals(currencyId)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Currency getDefaultCurrency() {
        return currencyList.get(0);
    }

    @Override
    public void saveCurrencies(List<Currency> currencies) {
        // Do nothing
    }

    @Override
    public void unsubscribe() {
        // Do nothing
    }

    private List<Currency> getFakeCurrencyList() {
        final List<Currency> currencyList = new ArrayList<>();

        Currency currency = new Currency();
        currency.setId("R01335");
        currency.setNumCode("398");
        currency.setCharCode("KZT");
        currency.setNominal(100);
        currency.setName("Казахстанских тенге");
        currency.setValue(17.9259f);
        currencyList.add(currency);

        currency = new Currency();
        currency.setId("R01020A");
        currency.setNumCode("944");
        currency.setCharCode("AZN");
        currency.setNominal(1);
        currency.setName("Азербайджанский манат");
        currency.setValue(34.8982f);
        currencyList.add(currency);

        currency = new Currency();
        currency.setId("R01090B");
        currency.setNumCode("933");
        currency.setCharCode("BYN");
        currency.setNominal(1);
        currency.setName("Белорусский рубль");
        currency.setValue(29.6225f);
        currencyList.add(currency);

        currency = new Currency();
        currency.setId("R01215");
        currency.setNumCode("208");
        currency.setCharCode("DKK");
        currency.setNominal(10);
        currency.setName("Датских крон");
        currency.setValue(93.8171f);
        currencyList.add(currency);

        currency = new Currency();
        currency.setId("R01235");
        currency.setNumCode("840");
        currency.setCharCode("USD");
        currency.setNominal(1);
        currency.setName("Доллар США");
        currency.setValue(59.2746f);
        currencyList.add(currency);

        currency = new Currency();
        currency.setId("R01239");
        currency.setNumCode("978");
        currency.setCharCode("EUR");
        currency.setNominal(1);
        currency.setName("Евро ");
        currency.setValue(69.6654f);
        currencyList.add(currency);

        return currencyList;
    }
}