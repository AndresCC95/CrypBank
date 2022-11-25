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

//Pantalla de registro de la app
public class Pantalla2Registro extends AppCompatActivity {

    //Declaracion de variables
    private EditText usuario;
    private EditText contraseña;
    private EditText nombre;
    private EditText apellidos;
    private EditText dni;
    private EditText saldo;

    private Button botonConfirmar;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    //Declaracion de variables auxiliares
    private String name = "";
    private String lastName = "";
    private String userDni = "";
    private String user = "";
    private double balance = 0 ;
    private String password = "";
    private final double transfer = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla2);

        //Variables igualadas a su id del .xml
        nombre = findViewById(R.id.editRegisterNameTwo);
        apellidos = findViewById(R.id.editRegisterLastNameTwo);
        dni = findViewById(R.id.editRegisterDniTwo);
        usuario = findViewById(R.id.editRegisterEmailTwo);
        saldo = findViewById(R.id.editRegisterBalanceTwo);
        contraseña = findViewById(R.id.editRegisterPasswordTwo);

        botonConfirmar = findViewById(R.id.confirmButtonTwo);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Asignacion de la accion de click en el boton de confirmar registro
        botonConfirmar.setOnClickListener(v -> {
            try {
                name = nombre.getText().toString();
                lastName = apellidos.getText().toString();
                userDni = dni.getText().toString();
                user = usuario.getText().toString();
                balance = Double.parseDouble(saldo.getText().toString());
                password = contraseña.getText().toString();
                signin();
            } catch(Exception e) {
                Toast.makeText(
                        getApplicationContext(),
                        "Error, todos los campos son obligatorios.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    //Metodo para el registro, mapeo para enviar los datos a Firebase y pasar a la pantalla 3
    private void signin() {
        myAuth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String,Object> map = new HashMap<>();
                map.put("Email", user);
                map.put("Clave", password);
                map.put("Nombre", name);
                map.put("Apellido", lastName);
                map.put("Dni", userDni);
                map.put("Saldo", balance);
                map.put("Transferencia", transfer);

                String id = myAuth.getCurrentUser().getUid();
                mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(task2 -> {
                    if(task2.isSuccessful()) {
                        Intent pantalla3 = new Intent(
                                Pantalla2Registro.this,
                                Pantalla3Principal.class
                        );
                        startActivity(pantalla3);
                        finish();
                    }
                });
                Toast.makeText(
                        getApplicationContext(),
                        "Registro completado con éxito!",
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        task.getException().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
