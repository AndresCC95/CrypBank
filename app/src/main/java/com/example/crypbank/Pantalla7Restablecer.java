package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

//Pantalla de restablecer contraseña de la app
public class Pantalla7Restablecer extends AppCompatActivity {

    //Declaracion de variables
    private TextInputEditText usuario;

    private Button botonRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla7);

        //Variables igualadas a su id del .xml
        usuario = findViewById(R.id.editUserSeven);

        botonRecuperar = findViewById(R.id.recoverButtonSeven);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Asignacion de la accion de click en el boton de recuperar contraseña
        botonRecuperar.setOnClickListener(view -> {
            validate();
        });
    }

    //Metodo para validar si el email introducido es correcto
    public void validate() {
        String email = usuario.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usuario.setError("Error, el e-mail no es correcto!");
            return;
        }

        sendEmail(email);
    }

    //Metodo para volver a la pantalla 1 si no quieres recuperar la contraseña
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent pantalla1 = new Intent(
                Pantalla7Restablecer.this,
                Pantalla1Inicio.class
        );
        startActivity(pantalla1);
        finish();
    }

    //Metodo para enviar email de recuperacion de contraseña y volver a la pantalla 1
    public void sendEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       Toast.makeText(
                               getApplicationContext(),
                               "Correo de restablecimiento enviado correctamente.",
                               Toast.LENGTH_LONG
                       ).show();
                       Intent pantalla1 = new Intent(
                               Pantalla7Restablecer.this,
                               Pantalla1Inicio.class
                       );
                       startActivity(pantalla1);
                       finish();
                   } else {
                       Toast.makeText(
                               getApplicationContext(),
                               "Error, el correo no es válido o no existe.",
                               Toast.LENGTH_LONG
                       ).show();
                   }
                });
    }
}
