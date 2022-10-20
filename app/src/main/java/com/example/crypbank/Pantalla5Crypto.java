package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
    }
}