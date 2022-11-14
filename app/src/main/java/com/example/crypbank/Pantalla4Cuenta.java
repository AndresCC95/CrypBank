package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Pantalla4Cuenta extends AppCompatActivity {

    private Button botonTransferir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla4);

        botonTransferir = findViewById(R.id.confirmTransferButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonTransferir.setOnClickListener(view -> {
            Intent pantalla3 = new Intent(Pantalla4Cuenta.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
    }
}