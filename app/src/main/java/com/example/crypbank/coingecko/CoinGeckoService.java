package com.example.crypbank.coingecko;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinGeckoService {

    @GET("ping")
    Call<String> checkPing();

    @GET("simple/price")
    Call<ResponseBody> coinInfo(
            @Query("ids") String coinNames,
            @Query("vs_currencies") String currencies
    );

}
