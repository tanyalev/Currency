package ru.sberbank.converter.interactor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.entity.Currency;

public class ConverterInteractorTest extends Assert {
    private static final String CURRENCY_ID_1 = "id1";
    private static final String CURRENCY_ID_2 = "id2";
    private static final String CURRENCY_ID_3 = "id3";
    private static final String CURRENCY_ID_4 = "id4";

    @Mock
    private CurrencyRepository repository;

    private ConverterInteractor interactor;
    private Currency currency1, currency2, currency3, currency4;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new ConverterInteractor(repository);
        currency1 = getCurrency1();
        currency2 = getCurrency2();
        currency3 = getCurrency3();
        currency4 = getCurrency4();
    }

    @Test
    public void convert_shouldReturnNull_WhenSumIsInvalid() {
        assertNull(null, interactor.convert("abbabaca"));
    }

    @Test
    public void convert_shouldReturnNull_WhenCurrencyNotFound() {
        Mockito.when(repository.getSourceCurrency()).thenReturn(null);
        Mockito.when(repository.getDestCurrency()).thenReturn(currency2);

        assertNull(interactor.convert("2.5"));

        Mockito.verify(repository).getSourceCurrency();
    }

    @Test
    public void convert_shouldReturnSum_WhenSumIsDecimal() {
        Mockito.when(repository.getSourceCurrency()).thenReturn(currency1);
        Mockito.when(repository.getDestCurrency()).thenReturn(currency2);

        assertEquals(1658.20, interactor.convert("25.5"), 000.1);

        Mockito.verify(repository).getSourceCurrency();
        Mockito.verify(repository).getDestCurrency();
    }

    @Test
    public void convert_shouldReturnSum_WhenSumIsInteger() {
        Mockito.when(repository.getSourceCurrency()).thenReturn(currency3);
        Mockito.when(repository.getDestCurrency()).thenReturn(currency4);

        assertEquals(1616.017, interactor.convert("120"), 0.001);

        Mockito.verify(repository).getSourceCurrency();
        Mockito.verify(repository).getDestCurrency();
    }

    @Test
    public void convert_shouldReturnSum_WhenSumIsBigInteger() {
        Mockito.when(repository.getSourceCurrency()).thenReturn(currency4);
        Mockito.when(repository.getDestCurrency()).thenReturn(currency1);

        assertEquals(411354.78125, interactor.convert("3500000"), 0.001);

        Mockito.verify(repository).getSourceCurrency();
        Mockito.verify(repository).getDestCurrency();
    }

    private Currency getCurrency1() {
        Currency currency = new Currency();
        currency.setId(CURRENCY_ID_1);
        currency.setNominal(1);
        currency.setValue(59.2746f);
        return currency;
    }

    private Currency getCurrency2() {
        Currency currency = new Currency();
        currency.setId(CURRENCY_ID_2);
        currency.setNominal(100);
        currency.setValue(91.1531f);
        return currency;
    }

    private Currency getCurrency3() {
        Currency currency = new Currency();
        currency.setId(CURRENCY_ID_3);
        currency.setNominal(1);
        currency.setValue(93.8171f);
        return currency;
    }

    private Currency getCurrency4() {
        Currency currency = new Currency();
        currency.setId(CURRENCY_ID_4);
        currency.setNominal(10);
        currency.setValue(69.6654f);
        return currency;
    }
}