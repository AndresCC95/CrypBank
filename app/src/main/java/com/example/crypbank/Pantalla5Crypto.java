package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Pantalla5Crypto extends AppCompatActivity {

    private Button botonComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla5);

        botonComprar = findViewById(R.id.confirmBuyButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonComprar.setOnClickListener(view -> {
            Intent pantalla3 = new Intent(Pantalla5Crypto.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
    }
}
