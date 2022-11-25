package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Pantalla7Restablecer extends AppCompatActivity {

    private TextInputEditText usuario;

    private Button botonRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla7);

        usuario = findViewById(R.id.editUserSeven);

        botonRecuperar = findViewById(R.id.recoverButtonSeven);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonRecuperar.setOnClickListener(view -> {
            validate();
        });
    }

    public void validate() {
        String email = usuario.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usuario.setError("Error, el e-mail no es correcto!");
            return;
        }

        sendEmail(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent pantalla1 = new Intent(Pantalla7Restablecer.this, Pantalla1Inicio.class);
        startActivity(pantalla1);
        finish();
    }

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
                       Intent pantalla1 = new Intent(Pantalla7Restablecer.this, Pantalla1Inicio.class);
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
