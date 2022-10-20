package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
    }
}
