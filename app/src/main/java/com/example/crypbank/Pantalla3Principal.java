package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView monedaUno;
    private TextView precioMonedaUno;

    private TextView monedaDos;
    private TextView precioMonedaDos;

    private TextView monedaTres;
    private TextView precioMonedaTres;

    private TextView monedaCuatro;
    private TextView precioMonedaCuatro;

    private TextView monedaCinco;
    private TextView precioMonedaCinco;

    private Button botonTransferencia;
    private Button botonCompra;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla3);

        monedaUno = findViewById(R.id.editCryptoUno);
        precioMonedaUno = findViewById(R.id.editPrecioUno);

        monedaDos = findViewById(R.id.editCryptoDos);
        precioMonedaDos = findViewById(R.id.editPrecioDos);

        monedaTres = findViewById(R.id.editCryptoTres);
        precioMonedaTres = findViewById(R.id.editPrecioTres);

        monedaCuatro = findViewById(R.id.editCryptoCuatro);
        precioMonedaCuatro = findViewById(R.id.editPrecioCuatro);

        monedaCinco = findViewById(R.id.editCryptoCinco);
        precioMonedaCinco = findViewById(R.id.editPrecioCinco);

        botonTransferencia = findViewById(R.id.transferButton);
        botonCompra = findViewById(R.id.buyButton);

        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonTransferencia.setOnClickListener(view -> {
            Intent pantalla4 = new Intent(Pantalla3Principal.this, Pantalla4Cuenta.class);
            startActivity(pantalla4);
        });

        botonCompra.setOnClickListener(view -> {
            Intent pantalla5 = new Intent(Pantalla3Principal.this, Pantalla5Crypto.class);
            startActivity(pantalla5);
        });

        BottomNavigationView menu = findViewById(R.id.navigationMenuThree);
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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

                    List<Coin> coins = customGson.fromJson(response.body().string(), collectionType);
                    Log.i("nombre: ", coins.get(0).getNombre());
                    Log.i("precio: ", String.valueOf(coins.get(0).getPrecio()));
                    Log.i("nombre: ", coins.get(1).getNombre());
                    Log.i("precio: ", String.valueOf(coins.get(1).getPrecio()));
                    Log.i("nombre: ", coins.get(2).getNombre());
                    Log.i("precio: ", String.valueOf(coins.get(2).getPrecio()));

                    String monedaUnoA = coins.get(0).getNombre();
                    String precioUnoA = String.valueOf(coins.get(0).getPrecio());

                    String monedaDosA = coins.get(1).getNombre();
                    String precioDosA = String.valueOf(coins.get(1).getPrecio());

                    String monedaTresA = coins.get(2).getNombre();
                    String precioTresA = String.valueOf(coins.get(2).getPrecio());

                    String monedaCuatroA = coins.get(3).getNombre();
                    String precioCuatroA = String.valueOf(coins.get(3).getPrecio());

                    String monedaCincoA = coins.get(4).getNombre();
                    String precioCincoA = String.valueOf(coins.get(4).getPrecio());

                    monedaUno.setText(monedaUnoA);
                    precioMonedaUno.setText(precioUnoA);

                    monedaDos.setText(monedaDosA);
                    precioMonedaDos.setText(precioDosA);

                    monedaTres.setText(monedaTresA);
                    precioMonedaTres.setText(precioTresA);

                    monedaCuatro.setText(monedaCuatroA);
                    precioMonedaCuatro.setText(precioCuatroA);

                    monedaCinco.setText(monedaCincoA);
                    precioMonedaCinco.setText(precioCincoA);
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
