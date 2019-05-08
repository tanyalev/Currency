package ru.sberbank.converter.adapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import ru.sberbank.converter.data.network.adapter.CurrencyCoursesDeserializer;

public class CurrencyCoursesDeserializerTest extends Assert {
    private CurrencyCoursesDeserializer deserializer;
    private Map<Float, String> valuesMap = new HashMap<>();

    @Before
    public void setUp() {
        deserializer = new CurrencyCoursesDeserializer();
        valuesMap.put(10f, "10,0");
        valuesMap.put(22.452f, "22,452");
        valuesMap.put(44.8649f, "44,8649");
        valuesMap.put(78.4914f, "78,4914");
        valuesMap.put(0f, "0,0");
        valuesMap.put(0f, "0");
    }

    @Test
    public void parseFloat_shouldReturnRightValue() {
        for (Map.Entry<Float, String> entry : valuesMap.entrySet()) {
            assertEquals(entry.getKey(), (Float) deserializer.parseFloat(entry.getValue()));
        }
    }
}