package com.example.crypbank;

public class ListElement {
    public String nameView;
    public String priceView;
    public String currencyView;

    public ListElement() {

    }

    public ListElement(String nameView, String priceView, String currencyView) {
        this.nameView = nameView;
        this.priceView = priceView;
        this.currencyView = currencyView;
    }

    public String getNameView() {
        return nameView;
    }

    public void setNameView(String nameView) {
        this.nameView = nameView;
    }

    public String getPriceView() {
        return priceView;
    }

    public void setPriceView(String priceView) {
        this.priceView = priceView;
    }

    public String getCurrencyView() {
        return currencyView;
    }

    public void setCurrencyView(String currencyView) {
        this.currencyView = currencyView;
    }
}
