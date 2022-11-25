package com.example.crypbank.coingecko;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Interfact de CoinGecko para llamadas a la API
public interface CoinGeckoService {

    @GET("ping")
    Call<String> checkPing();

    //Tipo de llamada a la API
    @GET("simple/price")
    Call<ResponseBody> coinInfo(
            @Query("ids") String coinNames,
            @Query("vs_currencies") String currencies
    );
}
