package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

//Pantalla de login de la app
public class Pantalla1Inicio extends AppCompatActivity {

    //Declaracion de variables
    private TextInputEditText usuario;
    private TextInputEditText contraseña;
    private TextView olvidarContraseña;
    private TextView registro;

    private Button botonLogin;

    private FirebaseAuth myAuth;

    //Declaracion de variables auxiliares
    private String user;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla1);

        //Variables igualadas a su id del .xml
        usuario = findViewById(R.id.editUserOne);
        contraseña = findViewById(R.id.editPasswordOne);
        olvidarContraseña = findViewById(R.id.editForgotPasswordOne);
        registro = findViewById(R.id.editRegisterOne);

        botonLogin = findViewById(R.id.loginButtonOne);

        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Asignacion de la accion de click en el boton de confirmar login
        botonLogin.setOnClickListener(view -> {
            user = usuario.getText().toString();
            password = contraseña.getText().toString();

            if (!user.isEmpty() && !password.isEmpty()) {
                login();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Error, rellena los campos obligatorios.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        //Asignacion de la accion de click en olvidar contraseña
        olvidarContraseña.setOnClickListener(view -> {
            Intent pantalla7 = new Intent(
                    Pantalla1Inicio.this,
                    Pantalla7Restablecer.class
            );
            startActivity(pantalla7);
            finish();
        });

        //Asignacion de la accion de click en registro y pasar a la pantalla 2
        registro.setOnClickListener(view -> {
            Intent pantalla2 = new Intent(
                    Pantalla1Inicio.this,
                    Pantalla2Registro.class
            );
            startActivity(pantalla2);
        });
    }

    //Metodo para el login, para comprobar el inicio de sesion correcto y pasar a la pantalla 3
    private void login() {
        myAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent pantalla3 = new Intent(
                        Pantalla1Inicio.this,
                        Pantalla3Principal.class
                );
                startActivity(pantalla3);
                finish();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Error, comprueba los datos.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
