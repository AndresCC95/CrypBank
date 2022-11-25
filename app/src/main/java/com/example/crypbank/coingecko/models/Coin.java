package com.example.crypbank.coingecko.models;

import java.io.Serializable;

//Clase coin para indicar los datos de cada moneda crypto
public class Coin implements Serializable  {

    //Declaracion de variables
    public static final String[] COIN_NAMES = {"bitcoin","ethereum","dogecoin","cardano","solana"};
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
