package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Pantalla4Cuenta extends AppCompatActivity {

    private TextView saldo;
    private EditText dineroEnviar;
    private EditText dni;

    private Button botonTransferir;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    private String dniB;
    private double operacion;
    private double saldoActual;
    private double dineroAEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla4);

        saldo = findViewById(R.id.editBalanceFour);
        dineroEnviar = findViewById(R.id.editTransferMoneyFour);
        dni = findViewById(R.id.editTransferAccountFour);

        botonTransferir = findViewById(R.id.confirmTransferButtonFour);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        balanceInformation();

        dineroEnviar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    saldoActual = Double.parseDouble(saldo.getText().toString());
                    dineroAEnviar = Double.parseDouble(dineroEnviar.getText().toString());
                    if ((saldoActual >= dineroAEnviar)) {
                        operacion = saldoActual - dineroAEnviar;
                        Toast.makeText(getApplicationContext(), "Saldo ok.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Saldo insuficiente.",
                                Toast.LENGTH_LONG).show();
                    }
                } catch(NumberFormatException numberFormatException) {
                    Toast.makeText(getApplicationContext(),
                            "Error, asegurate de que el campo saldo tiene un formato correcto.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        botonTransferir.setOnClickListener(view -> {
            dniB = dni.getText().toString();
            String id = myAuth.getCurrentUser().getUid();
            Query userQuery = mDatabase.child("Usuarios").orderByChild("Dni").equalTo(dniB);
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.hasChildren()) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error, el DNI insertado no existe.",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }

                    for (DataSnapshot user: snapshot.getChildren()) {
                        String userUid = user.getKey();
                        Long saldoActual = user.child("Saldo").getValue(Long.class);
                        mDatabase.child("Usuarios").child(userUid).child("Saldo").setValue(saldoActual + dineroAEnviar);
                        mDatabase.child("Usuarios").child(id).child("Saldo").setValue(operacion);
                        mDatabase.child("Usuarios").child(id).child("Transferencia").setValue(dineroAEnviar);
                        Intent pantalla3 = new Intent(Pantalla4Cuenta.this, Pantalla3Principal.class);
                        startActivity(pantalla3);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error de conexión con la BBDD.",
                            Toast.LENGTH_LONG).show();
                }
            });
        });

        NavigationBarView menu = findViewById(R.id.navigationMenuFour);
        menu.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void balanceInformation() {
        String id= myAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
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
                    Intent pantalla3 = new Intent(Pantalla4Cuenta.this,
                            Pantalla3Principal.class);
                    startActivity(pantalla3);
                    return true;
                case R.id.item2:
                    Intent pantalla4 = new Intent(Pantalla4Cuenta.this,
                            Pantalla4Cuenta.class);
                    startActivity(pantalla4);
                    return true;
                case R.id.item3:
                    Intent pantalla6 = new Intent(Pantalla4Cuenta.this,
                            Pantalla6Perfil.class);
                    startActivity(pantalla6);
                    return true;
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Sesión cerrada.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Pantalla4Cuenta.this,
                            Pantalla1Inicio.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

}
