package com.example.crypbank.coingecko;

import com.example.crypbank.coingecko.models.Coin;
import com.example.crypbank.coingecko.models.SimplePriceDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CoinGeckoAdapter {

    private static CoinGeckoService API_SERVICE;
    private static final String BASE_URL = "https://api.coingecko.com/api/v3/";

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
}
