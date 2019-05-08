package ru.sberbank.converter.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute")
public class CbrValute {
    private String id;
    private String numCode;
    private String charCode;
    private int nominal;
    private String name;
    private String value;

    @Attribute(name = "ID")
    public String getId() {
        return id;
    }

    @Attribute(name = "ID")
    public void setId(String id) {
        this.id = id;
    }

    @Element(name = "NumCode")
    public String getNumCode() {
        return numCode;
    }

    @Element(name = "NumCode")
    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    @Element(name = "CharCode")
    public String getCharCode() {
        return charCode;
    }

    @Element(name = "CharCode")
    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    @Element(name = "Nominal")
    public int getNominal() {
        return nominal;
    }

    @Element(name = "Nominal")
    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @Element(name = "Name")
    public String getName() {
        return name;
    }

    @Element(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    @Element(name = "Value")
    public String getValue() {
        return value;
    }

    @Element(name = "Value")
    public void setValue(String value) {
        this.value = value;
    }
}