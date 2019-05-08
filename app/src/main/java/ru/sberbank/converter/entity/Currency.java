package ru.sberbank.converter.entity;

/**
 * Класс валюты
 */
public class Currency {
    // Id валюты
    private String id;
    // Числовой код
    private String numCode;
    // Символьный код
    private String charCode;
    // Номинал
    private int nominal;
    // Название
    private String name;
    // Курс к рублю
    private float value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}