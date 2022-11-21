package com.example.crypbank;

import static java.lang.Double.parseDouble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private TextView dni;
    private EditText nombreBeneficiario;

    private Button botonTransferir;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    private String dniB;
    private boolean existeU = false;
    private double dinerito;
    private double saldoActualizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla4);

        saldo = findViewById(R.id.editBalanceFour);
        dni = findViewById(R.id.editTransferAccount);
        dineroEnviar = findViewById(R.id.editTransferMoney);
        nombreBeneficiario = findViewById(R.id.four_beneficiary_hint);

        botonTransferir = findViewById(R.id.confirmTransferButton);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        balanceInformation();

        botonTransferir.setOnClickListener(view -> {
            Intent pantalla3 = new Intent(Pantalla4Cuenta.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
        BottomNavigationView menu = findViewById(R.id.navigationMenuFour);
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

            }
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    Intent pantalla4 = new Intent(Pantalla4Cuenta.this, Pantalla4Cuenta.class);
                    startActivity(pantalla4);
                    return true;
                case R.id.item2:
                    Intent pantalla5 = new Intent(Pantalla4Cuenta.this, Pantalla5Crypto.class);
                    startActivity(pantalla5);
                    return true;
                case R.id.item3:
                    Intent pantalla6 = new Intent(Pantalla4Cuenta.this, Pantalla6Perfil.class);
                    startActivity(pantalla6);
                    return true;
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Sesión cerrada.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Pantalla4Cuenta.this, Pantalla1Inicio.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
}