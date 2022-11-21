package com.example.crypbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crypbank.coingecko.models.Coin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pantalla5Crypto extends AppCompatActivity {

    private TextView saldo;
    private TextView nombreCrypto;
    private TextView precioCrypto;
    private EditText cantidad;
    private TextView precioTotal;

    private Button botonComprar;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    private double operacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla5);

        Coin coin = (Coin) getIntent().getSerializableExtra("Coin");
        saldo = findViewById(R.id.editBalanceFive);
        nombreCrypto = findViewById(R.id.editCrypto);
        precioCrypto = findViewById(R.id.editpriceCrypto);
        cantidad = findViewById(R.id.editTransferQuantity);
        precioTotal = findViewById(R.id.editTotalAmount);

        botonComprar = findViewById(R.id.confirmBuyButton);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        balanceInformation();

        nombreCrypto.setText(coin.getNombre());
        precioCrypto.setText(String.valueOf(coin.getPrecio()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        botonComprar.setOnClickListener(view -> {
            Intent pantalla3 = new Intent(Pantalla5Crypto.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });

        precioCrypto.getText().toString();
        cantidad.setText(String.valueOf(0));

        BottomNavigationView menu = findViewById(R.id.navigationMenuFive);
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
                    Intent pantalla4 = new Intent(Pantalla5Crypto.this, Pantalla4Cuenta.class);
                    startActivity(pantalla4);
                    return true;
                case R.id.item2:
                    Intent pantalla5 = new Intent(Pantalla5Crypto.this, Pantalla5Crypto.class);
                    startActivity(pantalla5);
                    return true;
                case R.id.item3:
                    Intent pantalla6 = new Intent(Pantalla5Crypto.this, Pantalla6Perfil.class);
                    startActivity(pantalla6);
                    return true;
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Sesión cerrada.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Pantalla5Crypto.this, Pantalla1Inicio.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
}
