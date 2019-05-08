package ru.sberbank.converter.interactor;

import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.entity.Currency;

public class ConverterInteractor {
    private CurrencyRepository repository;

    public ConverterInteractor(CurrencyRepository repository) {
        this.repository = repository;
    }

    public void setSourceCurrencyId(String currencyId) {
        repository.selectSourceCurrency(currencyId);
    }

    public void setDestCurrencyId(String currencyId) {
        repository.selectDestCurrency(currencyId);
    }

    public Float convert(String sumStr) {
        Float sum = parseFloat(sumStr);
        if (sum == null) {
            return null;
        }

        final Currency sourceCurrency = repository.getSourceCurrency();
        final Currency destCurrency = repository.getDestCurrency();
        if (sourceCurrency == null || destCurrency == null) {
            return null;
        }

        float normSourceCoeff = sourceCurrency.getValue() / sourceCurrency.getNominal();
        float normDestCoeff = destCurrency.getValue() / destCurrency.getNominal();
        float sourceToDestCoeff = normSourceCoeff / normDestCoeff;
        return sum * sourceToDestCoeff;
    }

    private Float parseFloat(String sumStr) {
        try {
            return Float.parseFloat(sumStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}