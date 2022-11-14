package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Pantalla1Inicio extends AppCompatActivity {

    private EditText usuario;
    private EditText contraseña;

    private String user;
    private String password;

    private Button botonLogin;
    private Button botonTransparente;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla1);

        usuario = findViewById(R.id.editUserOne);
        contraseña = findViewById(R.id.editPasswordOne);

        botonLogin = findViewById(R.id.loginButton);
        botonTransparente = findViewById(R.id.transparentButton);

        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonLogin.setOnClickListener(view -> {
            user = usuario.getText().toString();
            password = contraseña.getText().toString();

            if (!user.isEmpty() && !password.isEmpty()) {
                inicioSesion();
            } else {
                Toast.makeText(getApplicationContext(), "Rellena los campos.", Toast.LENGTH_SHORT).show();
            }
        });

        botonTransparente.setOnClickListener(view -> {
            Intent pantalla2 = new Intent(Pantalla1Inicio.this, Pantalla2Registro.class);
            startActivity(pantalla2);
            finish();
        });
    }

    private void inicioSesion() {
        myAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent pantalla3 = new Intent(Pantalla1Inicio.this, Pantalla3Principal.class);
                startActivity(pantalla3);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Comprueba los datos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
