package ru.sberbank.converter.data.network.adapter;

import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.sberbank.converter.data.network.model.CbrValute;
import ru.sberbank.converter.data.network.model.CbrValuteCourses;
import ru.sberbank.converter.entity.Currency;

public class CurrencyCoursesDeserializer {
    public List<Currency> transform(String content) {
        final Reader reader = new StringReader(content);
        try {
            final Persister persister = new Persister();
            CbrValuteCourses cbrValuteCourses = persister.read(CbrValuteCourses.class, reader, false);
            List<CbrValute> cbrValutes = cbrValuteCourses.getValutes();
            return convertToCurrencies(cbrValutes);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Currency> convertToCurrencies(List<CbrValute> cbrValutes) {
        final List<Currency> currencies = new ArrayList<>();
        for (CbrValute cbrValute : cbrValutes) {
            currencies.add(convertToCurrency(cbrValute));
        }
        return currencies;
    }

    private Currency convertToCurrency(CbrValute cbrValute) {
        Currency currency = new Currency();
        currency.setId(cbrValute.getId());
        currency.setNumCode(cbrValute.getNumCode());
        currency.setCharCode(cbrValute.getCharCode());
        currency.setNominal(cbrValute.getNominal());
        currency.setName(cbrValute.getName());
        currency.setValue(parseFloat(cbrValute.getValue()));
        return currency;
    }

    public float parseFloat(String numberStr) {
        final NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try {
            final Number number = format.parse(numberStr);
            return number.floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1f;
    }
}