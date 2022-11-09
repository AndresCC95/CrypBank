package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.crypbank.coingecko.CoinGeckoAdapter;
import com.example.crypbank.coingecko.CoinGeckoService;
import com.example.crypbank.coingecko.models.Coin;
import com.example.crypbank.coingecko.models.SimplePriceDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pantalla3Principal extends AppCompatActivity {

    Button botonTransferencia;
    Intent pantalla4;
    Button botonCompra;
    Intent pantalla5;
    Button botonPerfil;
    Intent pantalla6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla3);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonTransferencia = findViewById(R.id.BotonTransferencia);
        botonCompra = findViewById(R.id.BotonCompra);
        botonPerfil = findViewById(R.id.BotonPerfil);

        botonTransferencia.setOnClickListener(view -> {
            pantalla4 = new Intent(Pantalla3Principal.this, Pantalla4Cuenta.class);
            startActivity(pantalla4);
        });

        botonCompra.setOnClickListener(view -> {
            pantalla5 = new Intent(Pantalla3Principal.this, Pantalla5Crypto.class);
            startActivity(pantalla5);
        });

        botonPerfil.setOnClickListener(view -> {
            pantalla6 = new Intent(Pantalla3Principal.this, Pantalla6Perfil.class);
            startActivity(pantalla6);
        });

        CoinGeckoService api = CoinGeckoAdapter.getApiService();
        Call<ResponseBody> call = api.coinInfo("bitcoin", "eur");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type collectionType = new TypeToken<List<Coin>>(){}.getType();
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(collectionType, new SimplePriceDeserializer());
                    Gson customGson = builder.create();

                    List<Coin> coins = customGson.fromJson(response.body().string(), collectionType);
                    Log.i("nombre: ", coins.get(0).getNombre());
                    Log.i("precio: ", String.valueOf(coins.get(0).getPrecio()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
