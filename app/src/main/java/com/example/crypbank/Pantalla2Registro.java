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

    private EditText EditEmailRegistro;
    private EditText EditPasswordRegistro;
    private EditText Saldo;
    private EditText Nombre;
    private EditText Apellidos;
    private EditText Dni;

    private Button BotonConfirmar;
    private Intent pantalla3;

    FirebaseAuth myAuth;
    DatabaseReference mDatabase;

    private String name = "";
    private String email = "";
    private String password = "";
    private String lastname = "";
    private String dni = "";
    private int balance = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla2);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Nombre=findViewById(R.id.EditNombreRegistro);
        Apellidos=findViewById(R.id.EditApellidosRegistro);
        Dni=findViewById(R.id.EditDNIRegistro);
        Saldo= findViewById(R.id.EditSaldoRegistro);
        EditEmailRegistro = findViewById(R.id.EditEmailRegistro);
        EditPasswordRegistro = findViewById(R.id.EditPasswordRegistro);

        BotonConfirmar = findViewById(R.id.BotonConfirmar);
        pantalla3 = new Intent(Pantalla2Registro.this, Pantalla3Principal.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BotonConfirmar.setOnClickListener(v -> {
            name = Nombre.getText().toString();
            email = EditEmailRegistro.getText().toString();
            password = EditPasswordRegistro.getText().toString();
            lastname = Apellidos.getText().toString();
            dni = Dni.getText().toString();
            balance = Integer.parseInt(Saldo.getText().toString());


            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !lastname.isEmpty() && !dni.isEmpty()) {
                RegistroFirebase();
            } else {
                Toast.makeText(getApplicationContext(), "Debe instertar los datos que faltan.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RegistroFirebase() {
        myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Map<String,Object> map =new HashMap<>();
                map.put("Nombre", name);
                map.put("Email", email);
                map.put("Clave", password);
                map.put("Apellido", lastname);
                map.put("Dni", dni);
                map.put("Saldo", balance);

                String id= myAuth.getCurrentUser().getUid();
                mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(task2 -> {
                    if(task2.isSuccessful()){
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
