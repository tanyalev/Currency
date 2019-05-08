package ru.sberbank.converter.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.interactor.CurrenciesInteractor;
import ru.sberbank.converter.view.SplashView;

public class SplashPresenterTest {
    @Mock
    private CurrencyRepository repository;
    @Mock
    private SplashView view;
    @Captor
    private ArgumentCaptor<CurrencyRepository.LoadCurrenciesCallback> callbackCaptor;

    private CurrenciesInteractor interactor;
    private SplashPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new CurrenciesInteractor(repository);
    }

    @After
    public void tearDown() {
        presenter = null;
    }

    @Test
    public void shouldNavigateToConverter_WhenLoadCompleted() {
        presenter = new SplashPresenter(interactor);

        presenter.attachView(view);

        Mockito.verify(repository).refreshCurrencies(callbackCaptor.capture());
        callbackCaptor.getValue().onLoadCompleted();
        Mockito.verify(view).navigateToConverterScreen();
    }

    @Test
    public void shouldNavigateToConverterAndShowError_WhenLoadError() {
        presenter = new SplashPresenter(interactor);

        presenter.attachView(view);

        Mockito.verify(repository).refreshCurrencies(callbackCaptor.capture());
        callbackCaptor.getValue().onLoadError();
        Mockito.verify(view).showRefreshErrorMessage();
        Mockito.verify(view).navigateToConverterScreen();
    }
}