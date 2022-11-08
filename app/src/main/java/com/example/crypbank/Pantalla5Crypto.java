package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.crypbank.coingecko.CoinGeckoAdapter;
import com.example.crypbank.coingecko.CoinGeckoService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pantalla5Crypto extends AppCompatActivity {

    private Button botonComprar;
    private Intent pantalla3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla5);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonComprar = findViewById(R.id.BotonComprar);

        botonComprar.setOnClickListener(view -> {
            pantalla3 = new Intent(Pantalla5Crypto.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });

        CoinGeckoService api = CoinGeckoAdapter.getApiService();
        Call<String> call = api.checkPing();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Ping: ", response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
