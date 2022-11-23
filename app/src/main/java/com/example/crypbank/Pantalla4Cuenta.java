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
    private EditText dni;

    private Button botonTransferir;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    private String dniB;
    private boolean existeUser = false;

    private double operacion;
    private double saldoActual;
    private double dineroAEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla4);

        saldo = findViewById(R.id.editBalanceFour);
        dineroEnviar = findViewById(R.id.editTransferMoney);
        dni = findViewById(R.id.editTransferAccount);

        botonTransferir = findViewById(R.id.confirmTransferButton);

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
                saldoActual = Double.parseDouble(saldo.getText().toString());
                dineroAEnviar = Double.parseDouble(dineroEnviar.getText().toString());
                //checkUsuario(dniB);

                //if ((num1 >= num2)) {
                    operacion = saldoActual - dineroAEnviar;

                    //Toast.makeText(getApplicationContext(), "Saldo ok.", Toast.LENGTH_SHORT).show();
                //} else {
                //Toast.makeText(getApplicationContext(), "Saldo insuficiente.", Toast.LENGTH_SHORT).show();
                //}
            }
        });
        botonTransferir.setOnClickListener(view -> {
            dniB = dni.getText().toString();
            String id = myAuth.getCurrentUser().getUid();
            mDatabase.child("Usuarios").child(id).child("Saldo").setValue(operacion);
            mDatabase.child("Usuarios").child(id).child("Transferencia").setValue(dineroAEnviar);
            this.makeTransfer(dniB, dineroAEnviar);
            Intent pantalla3 = new Intent(Pantalla4Cuenta.this, Pantalla3Principal.class);
            startActivity(pantalla3);
        });
        BottomNavigationView menu = findViewById(R.id.navigationMenuFour);
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void makeTransfer(String dni, Double money) {
        Query userQuery = mDatabase.child("Usuarios").orderByChild("Dni").equalTo(dni);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    String userUid = user.getKey();
                    Long saldoActual = user.child("Saldo").getValue(Long.class);
                    mDatabase.child("Usuarios").child(userUid).child("Saldo").setValue(saldoActual + money);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void checkUsuario(String dniBeneficiario) {
//        Query verifUser = FirebaseDatabase.getInstance().getReference().child("Usuarios").orderByChild("Dni").equalTo(dniBeneficiario);
//
//        verifUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    existeUser = true;
//                    Toast.makeText(getApplicationContext(),"Usuario ok.",Toast.LENGTH_SHORT).show();
//                } else {
//                    existeUser = false;
//                    Toast.makeText(getApplicationContext(), "El DNI del usuario no existe.", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

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