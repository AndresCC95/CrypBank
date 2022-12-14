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
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Pantalla de compra de crypto de la app
public class Pantalla5Crypto extends AppCompatActivity {

    //Declaracion de variables
    private TextView saldo;
    private TextView nombreCrypto;
    private TextView precioCrypto;
    private EditText cantidad;
    private TextView precioTotal;

    private double balance;

    private Button botonComprar;

    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    //Declaracion de variable auxiliare
    private double operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla5);

        //Recogida de la moneda que se hace click en el RecyclerView
        Coin coin = (Coin) getIntent().getSerializableExtra("Coin");

        //Variables igualadas a su id del .xml
        saldo = findViewById(R.id.editBalanceFive);
        nombreCrypto = findViewById(R.id.editCryptoFive);
        precioCrypto = findViewById(R.id.editpriceCryptoFive);
        cantidad = findViewById(R.id.editTransferQuantityFive);
        precioTotal = findViewById(R.id.editTotalAmountFive);

        botonComprar = findViewById(R.id.confirmBuyButton);

        myAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombreCrypto.setText(coin.getNombre());
        precioCrypto.setText(String.valueOf(coin.getPrecio()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        balanceInformation();

        cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //Metodo cuando el texto de la cantidad cambia, realiza la operacion
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    double num1 = Double.parseDouble(precioCrypto.getText().toString());
                    double num2 = Double.parseDouble(cantidad.getText().toString());
                    operacion = num1 * num2;
                    precioTotal.setText(Double.toString(operacion));
                } catch(NumberFormatException numberFormatException) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Error, asegurate de que el campo cantidad no est?? vac??o.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        //Asignacion de la accion de click en el boton de confirmar compra
        botonComprar.setOnClickListener(view -> {
            try {
                balance = Double.parseDouble(saldo.getText().toString()) - Double.parseDouble(precioTotal.getText().toString());
                updateFirebase();
            } catch(NumberFormatException numberFormatException) {
                Toast.makeText(
                        getApplicationContext(),
                        "Error, asegurate de que el campo cantidad no est?? vac??o.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        //Recoger datos del menu de navegacion del .xml
        NavigationBarView menu = findViewById(R.id.navigationMenuFive);
        menu.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Metodo para la actualizacion de datos en Firebase e ir a la pantalla 3
    private void updateFirebase() {
        String id = myAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).child("Saldo").setValue(balance);
        Intent pantalla3 = new Intent(
                Pantalla5Crypto.this,
                Pantalla3Principal.class
        );
        startActivity(pantalla3);
        Toast.makeText(
                getApplicationContext(),
                "Compra completada con ??xito!",
                Toast.LENGTH_SHORT
        ).show();
    }

    //Metodo de recogida de los datos, desde Firebase, del saldo
    public void balanceInformation() {
        String id= myAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String balanceProv = snapshot.child("Saldo").getValue().toString();
                    saldo.setText(balanceProv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error de conexi??n con la BBDD.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //Metodo para la creacion del menu de navegacion y la asignacion de cada item
    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //Item 1, donde al hacer click te lleva a la pantalla 3
                case R.id.item1:
                    Intent pantalla3 = new Intent(
                            Pantalla5Crypto.this,
                            Pantalla3Principal.class
                    );
                    startActivity(pantalla3);
                    return true;
                //Item 2, donde al hacer click te lleva a la pantalla 4
                case R.id.item2:
                    Intent pantalla4 = new Intent(
                            Pantalla5Crypto.this,
                            Pantalla4Cuenta.class
                    );
                    startActivity(pantalla4);
                    return true;
                //Item 3, donde al hacer click te lleva a la pantalla 6
                case R.id.item3:
                    Intent pantalla6 = new Intent(
                            Pantalla5Crypto.this,
                            Pantalla6Perfil.class
                    );
                    startActivity(pantalla6);
                    return true;
                //Item 4, donde al hacer click te desconecta la sesion y te lleva a la pantalla 1
                case R.id.item4:
                    myAuth.signOut();
                    Toast.makeText(getApplicationContext(),
                            "Sesi??n cerrada.",
                            Toast.LENGTH_SHORT
                    ).show();
                    startActivity(new Intent(
                            Pantalla5Crypto.this,
                            Pantalla1Inicio.class)
                    );
                    finish();
                    return true;
            }
            return false;
        }
    };
}
