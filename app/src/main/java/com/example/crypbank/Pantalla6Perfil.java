package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pantalla6Perfil extends AppCompatActivity {

    private TextView nombre;
    private TextView apellidos;
    private TextView usuario;
    private TextView dni;
    private TextView saldo;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla6);

        nombre =findViewById(R.id.editProfileNameSix);
        apellidos =findViewById(R.id.editProfileLastNameSix);
        dni =findViewById(R.id.editProfileDniSix);
        usuario =findViewById(R.id.editProfileEmailSix);
        saldo = findViewById(R.id.editProfileBalanceSix);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        userInformation();

        NavigationBarView menu = findViewById(R.id.navigationMenuSix);
        menu.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void userInformation() {
        String id= myAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("Nombre").getValue().toString();
                    nombre.setText(name);

                    String lastName = snapshot.child("Apellido").getValue().toString();
                    apellidos.setText(lastName);

                    String userDni = snapshot.child("Dni").getValue().toString();
                    dni.setText(userDni);

                    String user = snapshot.child("Email").getValue().toString();
                    usuario.setText(user);

                    String balance = snapshot.child("Saldo").getValue().toString();
                    saldo.setText(balance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión con la BBDD.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    Intent pantalla3 = new Intent(Pantalla6Perfil.this,
                            Pantalla3Principal.class);
                    startActivity(pantalla3);
                    return true;
                case R.id.item2:
                    Intent pantalla4 = new Intent(Pantalla6Perfil.this,
                            Pantalla4Cuenta.class);
                    startActivity(pantalla4);
                    return true;
                case R.id.item3:
                    Intent pantalla6 = new Intent(Pantalla6Perfil.this,
                            Pantalla6Perfil.class);
                    startActivity(pantalla6);
                    return true;
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(
                            getApplicationContext(),
                            "Sesión cerrada.",
                            Toast.LENGTH_SHORT
                    ).show();
                    startActivity(new Intent(Pantalla6Perfil.this,
                            Pantalla1Inicio.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
}