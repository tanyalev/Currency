package ru.sberbank.converter.interactor;

import java.util.List;

import ru.sberbank.converter.data.repository.CurrencyDataSource;
import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.entity.Currency;

public class CurrenciesInteractor {
    private CurrencyRepository currencyRepository;

    public CurrenciesInteractor(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void refreshCurrencies(CurrencyRepository.LoadCurrenciesCallback callback) {
        currencyRepository.refreshCurrencies(callback);
    }

    public List<Currency> getCurrencies() {
        return currencyRepository.getCurrencies();
    }

    public Currency getCurrency(String currencyId) {
        return currencyRepository.getCurrency(currencyId);
    }

    public float getSourceRatio() {
        final Currency sourceCurrency = currencyRepository.getSourceCurrency();
        final Currency destCurrency = currencyRepository.getDestCurrency();
        final float normSourceCoeff = sourceCurrency.getValue() / sourceCurrency.getNominal();
        final float normDestCoeff = destCurrency.getValue() / destCurrency.getNominal();
        return normSourceCoeff / normDestCoeff;
    }

    public float getDestRatio() {
        final Currency sourceCurrency = currencyRepository.getSourceCurrency();
        final Currency destCurrency = currencyRepository.getDestCurrency();
        final float normSourceCoeff = sourceCurrency.getValue() / sourceCurrency.getNominal();
        final float normDestCoeff = destCurrency.getValue() / destCurrency.getNominal();
        return normDestCoeff / normSourceCoeff;
    }

    public Currency getSourceCurrency() {
        return currencyRepository.getSourceCurrency();
    }

    public Currency getDestCurrency() {
        return currencyRepository.getDestCurrency();
    }

    /**
     * Функция смены исходной и конечной валюты местами
     */
    public void swapCurrencies() {
        final String sourceCurrencyId = currencyRepository.getSourceCurrency().getId();
        final String destCurrencyId = currencyRepository.getDestCurrency().getId();
        currencyRepository.selectSourceCurrency(destCurrencyId);
        currencyRepository.selectDestCurrency(sourceCurrencyId);
    }

    /**
     * Функция отписки от всех запросов к сети
     */
    public void unsubscribe() {
        currencyRepository.unsubscribe();
    }
}