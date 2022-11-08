package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Pantalla1Inicio extends AppCompatActivity {

    private EditText nombreUsuario;
    private EditText password;

    private Intent pantalla3;
    private Intent pantalla2;

    private FirebaseAuth myAuth;

    private String usuario;
    private String scontraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla1);
    }

    @Override
    protected void onStart() {
        super.onStart();

        nombreUsuario=findViewById(R.id.EditUser);
        password=findViewById(R.id.EditPassword);

        myAuth=FirebaseAuth.getInstance();
        Button botonLogin = findViewById(R.id.BotonLogin);
        botonLogin.setOnClickListener(view -> {
            usuario = nombreUsuario.getText().toString();
            scontraseña= password.getText().toString();

            if (!usuario.isEmpty()&& !scontraseña.isEmpty()) {
                inicioSesion();
            } else {
                Toast.makeText(getApplicationContext(), "Rellena los campos" , Toast.LENGTH_SHORT).show();
            }
        });

        Button botonTransparente = findViewById(R.id.BotonTransparente);
        botonTransparente.setOnClickListener(view -> {
            pantalla2 = new Intent(Pantalla1Inicio.this, Pantalla2Registro.class);
            startActivity(pantalla2);
            finish();
        });
    }

    private void inicioSesion() {
        myAuth.signInWithEmailAndPassword(usuario,scontraseña).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                pantalla3 = new Intent(Pantalla1Inicio.this, Pantalla3Principal.class);
                startActivity(pantalla3);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Comprueba los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
