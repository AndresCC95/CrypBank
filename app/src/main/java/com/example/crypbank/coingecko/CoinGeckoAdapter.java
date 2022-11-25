package com.example.crypbank.coingecko;

import com.example.crypbank.coingecko.models.Coin;

import java.util.List;

import retrofit2.Retrofit;

//Adaptador para CoinGecko
public class CoinGeckoAdapter {

    //Declaración de variables
    private static CoinGeckoService API_SERVICE;
    private static final String BASE_URL = "https://api.coingecko.com/api/v3/";

    private static List<Coin> listaCoins;

    //Metodo para la recogida del servicio de la API a traves de Retrofit
    public static CoinGeckoService getApiService() {
        if (API_SERVICE != null) {
            return API_SERVICE;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        API_SERVICE = retrofit.create(CoinGeckoService.class);
        return API_SERVICE;
    }

    public static List<Coin> getListaCoins() {
        return listaCoins;
    }

    public static void setListaCoins(List<Coin> newListaCoins) {
        listaCoins = newListaCoins;
    }
}
