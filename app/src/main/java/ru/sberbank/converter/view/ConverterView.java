package ru.sberbank.converter.view;

import java.util.List;

import ru.sberbank.converter.entity.Currency;

public interface ConverterView {
    void showSelectCurrencyDialog(List<Currency> currencyList, boolean isSourceCurrency);

    void showConvertingResult(float result);

    void showConvertingError();

    void showSourceCurrency(String currency);

    void showDestCurrency(String currency);

    void showSourceRatio(String sourceCurrency, String destCurrency, float ratio);

    void showDestRatio(String sourceCurrency, String destCurrency, float ratio);
}