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

public class Pantalla0Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla0);

        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView preTitulo = findViewById(R.id.preTitleZero);
        TextView titulo = findViewById(R.id.titleZero);
        ImageView logo = findViewById(R.id.pictureZero);

        preTitulo.setAnimation(animacion2);
        titulo.setAnimation(animacion2);
        logo.setAnimation(animacion1);

        new Handler().postDelayed(() -> {
            Intent pantalla1 = new Intent(Pantalla0Bienvenida.this, Pantalla1Inicio.class);
            startActivity(pantalla1);
            finish();
        }, 1500);
    }
}
