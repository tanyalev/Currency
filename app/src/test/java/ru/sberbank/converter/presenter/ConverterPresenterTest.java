package ru.sberbank.converter.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.sberbank.converter.entity.Currency;
import ru.sberbank.converter.interactor.ConverterInteractor;
import ru.sberbank.converter.interactor.CurrenciesInteractor;
import ru.sberbank.converter.view.ConverterView;

public class ConverterPresenterTest {
    private static final String SOURCE_CURRENCY_CHAR_CODE = "RUB";
    private static final String DEST_CURRENCY_CHAR_CODE = "EUR";

    @Mock
    private ConverterView view;
    @Mock
    private CurrenciesInteractor currenciesInteractor;
    @Mock
    private ConverterInteractor converterInteractor;

    private ConverterPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ConverterPresenter(currenciesInteractor, converterInteractor);
        Currency sourceCurrency = getCurrency("id1", SOURCE_CURRENCY_CHAR_CODE);
        Currency destCurrency = getCurrency("id2", DEST_CURRENCY_CHAR_CODE);
        Mockito.when(currenciesInteractor.getSourceCurrency()).thenReturn(sourceCurrency);
        Mockito.when(currenciesInteractor.getDestCurrency()).thenReturn(destCurrency);
    }

    @After
    public void tearDown() {
        presenter = null;
    }

    @Test
    public void shouldShowCurrencyInfo_AfterAttach() {
        Mockito.when(currenciesInteractor.getSourceRatio()).thenReturn(2.0f);
        Mockito.when(currenciesInteractor.getDestRatio()).thenReturn(0.5f);

        presenter.attachView(view);

        Mockito.verify(view).showSourceCurrency(SOURCE_CURRENCY_CHAR_CODE);
        Mockito.verify(view).showDestCurrency(DEST_CURRENCY_CHAR_CODE);
        Mockito.verify(view).showSourceRatio(
                SOURCE_CURRENCY_CHAR_CODE, DEST_CURRENCY_CHAR_CODE, 2.0f);
        Mockito.verify(view).showDestRatio(
                SOURCE_CURRENCY_CHAR_CODE, DEST_CURRENCY_CHAR_CODE, 0.5f);
    }

    @Test
    public void convert_shouldShowConvertingError_WhenNumberInvalid() {
        Mockito.when(converterInteractor.convert("error")).thenReturn(null);

        presenter.attachView(view);
        presenter.convert("error");

        Mockito.verify(view).showConvertingError();
    }

    @Test
    public void convert_shouldShowConvertingResult_WhenConvertSucceed() {
        Mockito.when(converterInteractor.convert("200")).thenReturn(200f);

        presenter.attachView(view);
        presenter.convert("200");

        Mockito.verify(converterInteractor).convert("200");

        Mockito.verify(view).showConvertingResult(200f);
    }

    @Test
    public void swapCurrencies_shouldUpdateUI() {
        presenter.attachView(view);
        presenter.swapCurrencies("10");

        Mockito.verify(currenciesInteractor).swapCurrencies();
        Mockito.verify(view, Mockito.times(2)).showSourceCurrency(Mockito.anyString());
        Mockito.verify(view, Mockito.times(2)).showDestCurrency(Mockito.anyString());
        Mockito.verify(view, Mockito.times(2)).showSourceRatio(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyFloat());
        Mockito.verify(view, Mockito.times(2)).showDestRatio(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyFloat());
    }

    private Currency getCurrency(String currencyId, String charCode) {
        final Currency currency = new Currency();
        currency.setId(currencyId);
        currency.setCharCode(charCode);
        return currency;
    }
}