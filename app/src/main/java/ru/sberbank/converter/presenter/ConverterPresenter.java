package ru.sberbank.converter.presenter;

import ru.sberbank.converter.interactor.ConverterInteractor;
import ru.sberbank.converter.interactor.CurrenciesInteractor;
import ru.sberbank.converter.view.ConverterView;

public class ConverterPresenter extends BasePresenter<ConverterView> {
    private CurrenciesInteractor currenciesInteractor;
    private ConverterInteractor converterInteractor;

    public ConverterPresenter(CurrenciesInteractor currenciesInteractor,
                              ConverterInteractor converterInteractor) {
        this.currenciesInteractor = currenciesInteractor;
        this.converterInteractor = converterInteractor;
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        showCurrencyInfo();
    }

    private void showCurrencyInfo() {
        final String sourceCurrencyCode = currenciesInteractor.getSourceCurrency().getCharCode();
        final String destCurrencyCode = currenciesInteractor.getDestCurrency().getCharCode();

        getView().showSourceCurrency(sourceCurrencyCode);
        getView().showDestCurrency(destCurrencyCode);

        getView().showSourceRatio(
                sourceCurrencyCode, destCurrencyCode, currenciesInteractor.getSourceRatio());
        getView().showDestRatio(
                sourceCurrencyCode, destCurrencyCode, currenciesInteractor.getDestRatio());
    }

    public void convert(String sumStr) {
        final Float result = converterInteractor.convert(sumStr);
        if (result != null) {
            getView().showConvertingResult(result);
        } else {
            getView().showConvertingError();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currenciesInteractor.unsubscribe();
    }

    public void swapCurrencies(String sumStr) {
        currenciesInteractor.swapCurrencies();
        showCurrencyInfo();
        convert(sumStr);
    }

    public void onCurrencyClicked(final boolean isSourceCurrency) {
        getView().showSelectCurrencyDialog(currenciesInteractor.getCurrencies(), isSourceCurrency);
    }

    public void onSourceCurrencySelected(String sumStr, String currencyId) {
        getView().showSourceCurrency(currenciesInteractor.getCurrency(currencyId).getCharCode());
        converterInteractor.setSourceCurrencyId(currencyId);
        showCurrencyInfo();
        convert(sumStr);
    }

    public void onDestCurrencySelected(String sumStr, String currencyId) {
        getView().showDestCurrency(currenciesInteractor.getCurrency(currencyId).getCharCode());
        converterInteractor.setDestCurrencyId(currencyId);
        showCurrencyInfo();
        convert(sumStr);
    }
}