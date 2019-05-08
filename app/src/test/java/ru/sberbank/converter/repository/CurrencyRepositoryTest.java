package ru.sberbank.converter.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.data.repository.CurrencyDataSource;
import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.data.storage.CurrencyStorage;
import ru.sberbank.converter.entity.Currency;

public class CurrencyRepositoryTest extends Assert {
    private static final String SOURCE_CURRENCY_ID = "id1";
    private static final String DEST_CURRENCY_ID = "id2";
    private static final String DEFAULT_CURRENCY_ID = "id1";

    @Mock
    private CurrencyDataSource localDataSource;
    @Mock
    private CurrencyDataSource remoteDataSource;
    @Mock
    private CurrencyRepository.LoadCurrenciesCallback callback;
    @Mock
    private CurrencyStorage storage;
    @Captor
    private ArgumentCaptor<CurrencyDataSource.CurrenciesCallback> callbackCaptor;

    private CurrencyRepository repository;
    private List<Currency> currencyList;
    private Currency sourceCurrency;
    private Currency destCurrency;
    private Currency defaultCurrency;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new CurrencyRepository(remoteDataSource, localDataSource, storage);
        currencyList = new ArrayList<>();
        sourceCurrency = getCurrency(SOURCE_CURRENCY_ID);
        destCurrency = getCurrency(DEST_CURRENCY_ID);
        defaultCurrency = getCurrency(DEFAULT_CURRENCY_ID);
    }

    @After
    public void tearDown() {
        repository = null;
    }

    @Test
    public void refreshCurrencies_ShouldCallRemoteDataSource() {
        repository.refreshCurrencies(callback);
        Mockito.verify(remoteDataSource).loadCurrencies(callbackCaptor.capture());

        callbackCaptor.getValue().onLoadCompleted(currencyList);
        Mockito.verify(localDataSource).saveCurrencies(currencyList);
    }

    @Test
    public void getSourceCurrency_ShouldReturnSourceCurrency_WhenSourceCurrencyExist() {
        Mockito.when(storage.getSourceCurrencyId()).thenReturn(SOURCE_CURRENCY_ID);
        Mockito.when(localDataSource.getCurrency(SOURCE_CURRENCY_ID)).thenReturn(sourceCurrency);

        assertEquals(sourceCurrency, repository.getSourceCurrency());
    }

    @Test
    public void getSourceCurrency_ShouldReturnDefaultCurrency_WhenSourceCurrencyDoesntExist() {
        Mockito.when(storage.getSourceCurrencyId()).thenReturn(null);
        Mockito.when(localDataSource.getDefaultCurrency()).thenReturn(defaultCurrency);

        assertEquals(defaultCurrency, repository.getSourceCurrency());
    }

    @Test
    public void getDestCurrency_ShouldReturnDestCurrency_WhenDestCurrencyExist() {
        Mockito.when(storage.getDestCurrencyId()).thenReturn(DEST_CURRENCY_ID);
        Mockito.when(localDataSource.getCurrency(DEST_CURRENCY_ID)).thenReturn(destCurrency);

        assertEquals(destCurrency, repository.getDestCurrency());
    }

    @Test
    public void getDestCurrency_ShouldReturnDefaultCurrency_WhenDestCurrencyDoesntExist() {
        Mockito.when(storage.getDestCurrencyId()).thenReturn(null);
        Mockito.when(localDataSource.getDefaultCurrency()).thenReturn(defaultCurrency);

        assertEquals(defaultCurrency, repository.getDestCurrency());
    }

    private Currency getCurrency(String currencyId) {
        final Currency currency = new Currency();
        currency.setId(currencyId);
        return currency;
    }
}