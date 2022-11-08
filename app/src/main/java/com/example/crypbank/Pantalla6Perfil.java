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

    private Button botonVolver;
    private Intent pantalla3;

    // Valores obtenidos de la base de datos
    private TextView Email;
    private TextView SaldoUsuario;
    private TextView Nombre;
    private TextView Apellidos;
    private TextView Dni;

    // Datos Cliente
    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla6);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();

        Nombre=findViewById(R.id.EditNombrePerfil);
        Apellidos=findViewById(R.id.EditApellidosPerfil);
        Dni=findViewById(R.id.EditDNIPerfil);
        SaldoUsuario = findViewById(R.id.EditSaldo);
        Email =findViewById(R.id.EditEmailPerfil);
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonVolver = findViewById(R.id.BotonVolver);

        botonVolver.setOnClickListener(view -> {
            pantalla3 = new Intent(Pantalla6Perfil.this, Pantalla3Principal.class);
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
                    String name = snapshot.child("Nombre").getValue().toString();
                    Nombre.setText(name);

                    String apellido = snapshot.child("Apellido").getValue().toString();
                    Apellidos.setText(apellido);

                    String email = snapshot.child("Email").getValue().toString();
                    Email.setText(email);

                    String dni = snapshot.child("Dni").getValue().toString();
                    Dni.setText(dni);

                    // Arreglar porque con Double da problemas
                    String price = (String) snapshot.child("Saldo").getValue().toString();
                    SaldoUsuario.setText(price);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}