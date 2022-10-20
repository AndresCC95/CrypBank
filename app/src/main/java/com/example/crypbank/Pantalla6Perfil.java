package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Pantalla6Perfil extends AppCompatActivity {

    private Button botonVolver;
    private Intent pantalla3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla6);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonVolver = findViewById(R.id.BotonVolver);

        botonVolver.setOnClickListener(view -> {
            pantalla3 = new Intent(Pantalla6Perfil.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
    }
}