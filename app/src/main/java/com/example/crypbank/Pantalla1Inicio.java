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

    private Button botonLogin;
    private Button botonTransparente;

    private FirebaseAuth myAuth;

    private String user;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla1);

        usuario = findViewById(R.id.editUserOne);
        contraseña = findViewById(R.id.editPasswordOne);

        botonLogin = findViewById(R.id.loginButtonOne);
        botonTransparente = findViewById(R.id.transparentButtonOne);

        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonLogin.setOnClickListener(view -> {
            user = usuario.getText().toString();
            password = contraseña.getText().toString();

            if (!user.isEmpty() && !password.isEmpty()) {
                login();
            } else {
                Toast.makeText(getApplicationContext(), "Error, rellena los campos obligatorios.", Toast.LENGTH_SHORT).show();
            }
        });

        botonTransparente.setOnClickListener(view -> {
            Intent pantalla2 = new Intent(Pantalla1Inicio.this, Pantalla2Registro.class);
            startActivity(pantalla2);
        });
    }

    private void login() {
        myAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent pantalla3 = new Intent(Pantalla1Inicio.this, Pantalla3Principal.class);
                startActivity(pantalla3);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error, comprueba los datos.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
