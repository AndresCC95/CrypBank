package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.crypbank.adapter.ListCoinAdapter;
import com.example.crypbank.coingecko.CoinGeckoAdapter;
import com.example.crypbank.coingecko.CoinGeckoService;
import com.example.crypbank.coingecko.models.Coin;
import com.example.crypbank.coingecko.models.SimplePriceDeserializer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
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

    private Button botonTransferencia;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla3);

        CoinGeckoService api = CoinGeckoAdapter.getApiService();
        Call<ResponseBody> call = api.coinInfo(
                TextUtils.join(",", Coin.COIN_NAMES),
                TextUtils.join(",", Coin.CURRENCIES)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type collectionType = new TypeToken<List<Coin>>(){}.getType();
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(collectionType, new SimplePriceDeserializer());
                    Gson customGson = builder.create();
                    CoinGeckoAdapter.setListaCoins(customGson.fromJson(response.body().string(), collectionType));
                    init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        botonTransferencia = findViewById(R.id.transferButton);
        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonTransferencia.setOnClickListener(view -> {
            Intent pantalla4 = new Intent(Pantalla3Principal.this, Pantalla4Cuenta.class);
            startActivity(pantalla4);
        });
        BottomNavigationView menu = findViewById(R.id.navigationMenuThree);
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void init() {
        List<Coin> arias = CoinGeckoAdapter.getListaCoins();

        ListCoinAdapter listAdapter = new ListCoinAdapter(arias, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    Intent pantalla4 = new Intent(Pantalla3Principal.this, Pantalla4Cuenta.class);
                    startActivity(pantalla4);
                    return true;
                case R.id.item2:
                    Intent pantalla5 = new Intent(Pantalla3Principal.this, Pantalla5Crypto.class);
                    startActivity(pantalla5);
                    return true;
                case R.id.item3:
                    Intent pantalla6 = new Intent(Pantalla3Principal.this, Pantalla6Perfil.class);
                    startActivity(pantalla6);
                    return true;
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Sesión cerrada.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Pantalla3Principal.this, Pantalla1Inicio.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

}
