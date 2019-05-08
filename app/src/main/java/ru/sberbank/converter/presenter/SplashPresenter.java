package ru.sberbank.converter.presenter;

import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.interactor.CurrenciesInteractor;
import ru.sberbank.converter.view.SplashView;

public class SplashPresenter extends BasePresenter<SplashView> {
    private CurrenciesInteractor currenciesInteractor;

    public SplashPresenter(CurrenciesInteractor currenciesInteractor) {
        this.currenciesInteractor = currenciesInteractor;
    }

    @Override
    protected void onViewAttach() {
        currenciesInteractor.refreshCurrencies(new CurrencyRepository.LoadCurrenciesCallback() {
            @Override
            public void onLoadCompleted() {
                getView().navigateToConverterScreen();
            }

            @Override
            public void onLoadError() {
                getView().showRefreshErrorMessage();
                getView().navigateToConverterScreen();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currenciesInteractor.unsubscribe();
    }
}