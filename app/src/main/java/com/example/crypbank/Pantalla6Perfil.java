package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pantalla6Perfil extends AppCompatActivity {

    // Valores obtenidos de la base de datos
    private TextView usuario;
    private TextView nombre;
    private TextView apellidos;
    private TextView dni;
    private TextView saldo;

    private Button botonVolver;

    // Datos Cliente
    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla6);

        usuario =findViewById(R.id.editProfileEmail);
        nombre =findViewById(R.id.editProfileName);
        apellidos =findViewById(R.id.editProfileLastName);
        dni =findViewById(R.id.editProfileDni);
        saldo = findViewById(R.id.editProfileBalance);

        botonVolver = findViewById(R.id.returnButton);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonVolver.setOnClickListener(view -> {
            Intent pantalla3 = new Intent(Pantalla6Perfil.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
        userInformation();
    }

    public void userInformation(){
        String id= myAuth.getCurrentUser().getUid();

        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String user = snapshot.child("Email").getValue().toString();
                    usuario.setText(user);

                    String name = snapshot.child("Nombre").getValue().toString();
                    nombre.setText(name);

                    String lastName = snapshot.child("Apellido").getValue().toString();
                    apellidos.setText(lastName);

                    String userDni = snapshot.child("Dni").getValue().toString();
                    Pantalla6Perfil.this.dni.setText(userDni);

                    // Arreglar porque con Double da problemas
                    String balance = snapshot.child("Saldo").getValue().toString();
                    saldo.setText(balance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}