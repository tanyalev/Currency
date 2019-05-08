package ru.sberbank.converter.interactor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.entity.Currency;

public class CurrenciesInteractorTest extends Assert {
    private static final String SOURCE_CURRENCY_ID = "id1";
    private static final String DEST_CURRENCY_ID = "id2";

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrenciesInteractor currenciesInteractor;
    private Currency sourceCurrency;
    private Currency destCurrency;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currenciesInteractor = new CurrenciesInteractor(currencyRepository);
        sourceCurrency = getCurrency(SOURCE_CURRENCY_ID, 50.5f, 1);
        destCurrency = getCurrency(DEST_CURRENCY_ID, 91f, 100);
    }

    @After
    public void tearDown() {
        currenciesInteractor = null;
    }

    @Test
    public void getSourceRatio_shouldReturnRightRatio() {
        Mockito.when(currencyRepository.getSourceCurrency()).thenReturn(sourceCurrency);
        Mockito.when(currencyRepository.getDestCurrency()).thenReturn(destCurrency);

        assertEquals(55.4945, currenciesInteractor.getSourceRatio(), 0.0001);
    }

    @Test
    public void getDestRatio_shouldReturnRightRatio() {
        Mockito.when(currencyRepository.getSourceCurrency()).thenReturn(sourceCurrency);
        Mockito.when(currencyRepository.getDestCurrency()).thenReturn(destCurrency);

        assertEquals(0.018, currenciesInteractor.getDestRatio(), 0.0001);
    }

    @Test
    public void swapCurrencies_shouldSwapSourceAndDestCurrency() {
        Mockito.when(currencyRepository.getSourceCurrency()).thenReturn(sourceCurrency);
        Mockito.when(currencyRepository.getDestCurrency()).thenReturn(destCurrency);

        currenciesInteractor.swapCurrencies();

        Mockito.verify(currencyRepository).selectSourceCurrency(DEST_CURRENCY_ID);
        Mockito.verify(currencyRepository).selectDestCurrency(SOURCE_CURRENCY_ID);
    }

    private Currency getCurrency(String currencyId, float value, int nominal) {
        final Currency currency = new Currency();
        currency.setId(currencyId);
        currency.setValue(value);
        currency.setNominal(nominal);
        return currency;
    }
}