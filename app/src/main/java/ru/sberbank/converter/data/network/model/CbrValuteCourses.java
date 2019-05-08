package ru.sberbank.converter.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class CbrValuteCourses {
    private String date;
    private String name;
    private List<CbrValute> valutes;

    @Attribute(name = "Date")
    public String getDate() {
        return date;
    }

    @Attribute(name = "Date")
    public void setDate(String date) {
        this.date = date;
    }

    @Attribute(name = "name")
    public String getName() {
        return name;
    }

    @Attribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @ElementList(name = "Valute", inline = true)
    public List<CbrValute> getValutes() {
        return valutes;
    }

    @ElementList(name = "Valute", inline = true)
    public void setValutes(List<CbrValute> valutes) {
        this.valutes = valutes;
    }
}