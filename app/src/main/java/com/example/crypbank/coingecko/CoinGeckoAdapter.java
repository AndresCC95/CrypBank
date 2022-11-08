package com.example.crypbank.coingecko;

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
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        API_SERVICE = retrofit.create(CoinGeckoService.class);
        return API_SERVICE;
    }
}
