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
import android.widget.TextView;
import android.widget.Toast;

import com.example.crypbank.adapter.ListCoinAdapter;
import com.example.crypbank.coingecko.CoinGeckoAdapter;
import com.example.crypbank.coingecko.CoinGeckoService;
import com.example.crypbank.coingecko.models.Coin;
import com.example.crypbank.coingecko.models.SimplePriceDeserializer;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private TextView saldo;
    private TextView ultTransferencia;

    private Button botonTransferencia;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

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
                Type collectionType = new TypeToken<List<Coin>>(){}.getType();
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(collectionType, new SimplePriceDeserializer());
                Gson customGson = builder.create();

                try {
                    CoinGeckoAdapter.setListaCoins(customGson.fromJson(response.body().string(),
                            collectionType));
                    init();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error de conexión con la API.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de conexión con la API.",
                        Toast.LENGTH_LONG).show();
            }
        });

        saldo = findViewById(R.id.editBalanceThree);
        ultTransferencia = findViewById(R.id.editTransferThree);

        botonTransferencia = findViewById(R.id.transferButtonThree);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonTransferencia.setOnClickListener(view -> {
            Intent pantalla4 = new Intent(Pantalla3Principal.this, Pantalla4Cuenta.class);
            startActivity(pantalla4);
        });

        balanceInformation();
        NavigationBarView menu = findViewById(R.id.navigationMenuThree);
        menu.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void init() {
        List<Coin> listaCryptos = CoinGeckoAdapter.getListaCoins();
        ListCoinAdapter listAdapter = new ListCoinAdapter(listaCryptos,
                this, item -> moveToDescription(item));
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void balanceInformation() {
        String id = myAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String balance = snapshot.child("Saldo").getValue().toString();
                    saldo.setText(balance);
                    String transfer = snapshot.child("Transferencia").getValue().toString();
                    if (Double.parseDouble(transfer) == 0) {
                        ultTransferencia.setText(R.string.three_last_transfer_prov);
                    } else {
                        ultTransferencia.setText(transfer);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión con la BBDD.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void moveToDescription(Coin item) {
        Intent pantalla5 = new Intent(Pantalla3Principal.this, Pantalla5Crypto.class);
        pantalla5.putExtra("Coin", item);
        startActivity(pantalla5);
    }

    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    Intent pantalla3 = new Intent(Pantalla3Principal.this,
                            Pantalla3Principal.class);
                    startActivity(pantalla3);
                    finish();
                    return true;
                case R.id.item2:
                    Intent pantalla4 = new Intent(Pantalla3Principal.this,
                            Pantalla4Cuenta.class);
                    startActivity(pantalla4);
                    return true;
                case R.id.item3:
                    Intent pantalla6 = new Intent(Pantalla3Principal.this,
                            Pantalla6Perfil.class);
                    startActivity(pantalla6);
                    return true;
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Sesión cerrada correctamente.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Pantalla3Principal.this,
                            Pantalla1Inicio.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
}
