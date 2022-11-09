package com.example.crypbank.coingecko.models;

public class Coin {

    public static final String[] COIN_NAMES = {"bitcoin", "ethereum", "dogecoin"};
    public static final String[] CURRENCIES = {"eur"};

    private String nombre;
    private double precio;

    public Coin(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
