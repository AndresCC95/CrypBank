package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Pantalla4Cuenta extends AppCompatActivity {

    private Button botonTransferir;
    private Intent pantalla3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla4);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonTransferir = findViewById(R.id.BotonTransferir);

        botonTransferir.setOnClickListener(view -> {
            pantalla3 = new Intent(Pantalla4Cuenta.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
    }
}