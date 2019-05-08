package ru.sberbank.converter.data.storage;

public interface CurrencyStorage {
    void setSourceCurrencyId(String currencyId);

    String getSourceCurrencyId();

    void setDestCurrencyId(String currencyId);

    String getDestCurrencyId();
}