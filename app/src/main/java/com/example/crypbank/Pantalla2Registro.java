package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Pantalla2Registro extends AppCompatActivity {

    private EditText usuario;
    private EditText contraseña;
    private EditText nombre;
    private EditText apellidos;
    private EditText dni;
    private EditText saldo;

    private String user = "";
    private String password = "";
    private String name = "";
    private String lastName = "";
    private String userDni = "";
    private double balance = 0 ;

    private Button botonConfirmar;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla2);

        usuario = findViewById(R.id.editRegisterEmail);
        contraseña = findViewById(R.id.editRegisterPassword);
        nombre = findViewById(R.id.editRegisterNaame);
        apellidos = findViewById(R.id.editRegisterLastName);
        dni = findViewById(R.id.editRegisterDni);
        saldo = findViewById(R.id.editRegisterBalance);

        botonConfirmar = findViewById(R.id.confirmButton);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonConfirmar.setOnClickListener(v -> {
            name = nombre.getText().toString();
            user = usuario.getText().toString();
            password = contraseña.getText().toString();
            lastName = apellidos.getText().toString();
            userDni = dni.getText().toString();
            balance = Integer.parseInt(saldo.getText().toString());


            if (!name.isEmpty() && !user.isEmpty() && !password.isEmpty() && !lastName.isEmpty() && !userDni.isEmpty()) {
                registroFirebase();
            } else {
                Toast.makeText(getApplicationContext(), "Debe instertar los datos que faltan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registroFirebase() {
        myAuth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Map<String,Object> map =new HashMap<>();
                map.put("Email", user);
                map.put("Clave", password);
                map.put("Nombre", name);
                map.put("Apellido", lastName);
                map.put("Dni", userDni);
                map.put("Saldo", balance);

                String id= myAuth.getCurrentUser().getUid();
                mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(task2 -> {
                    if(task2.isSuccessful()){
                        Intent pantalla3 = new Intent(Pantalla2Registro.this, Pantalla3Principal.class);
                        startActivity(pantalla3);
                        finish();
                    }
                });
                Toast.makeText(getApplicationContext(), "Registro completado con éxito!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
