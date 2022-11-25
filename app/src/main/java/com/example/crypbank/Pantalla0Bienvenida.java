package com.example.crypbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

//Pantalla de bienvenida de la app
public class Pantalla0Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asignacion de pantalla completa a la pantalla de bienvenida
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.pantalla0);

        //Carga de las animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        //Declaracion variables, igualadas a su id del .xml
        TextView preTitulo = findViewById(R.id.preTitleZero);
        TextView titulo = findViewById(R.id.titleZero);
        ImageView logo = findViewById(R.id.pictureZero);

        //Asginacion animaciones a las variables
        preTitulo.setAnimation(animacion2);
        titulo.setAnimation(animacion2);
        logo.setAnimation(animacion1);

        //Transicion a la pantalla1 de inicio tras los 1,5 segundos de la animacion
        new Handler().postDelayed(() -> {
            Intent pantalla1 = new Intent(
                    Pantalla0Bienvenida.this,
                    Pantalla1Inicio.class
            );
            startActivity(pantalla1);
            finish();
        }, 1500);
    }
}
