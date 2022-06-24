package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TelaResultado extends AppCompatActivity {

    private AppCompatButton recomecar;
    private TextView certas, erradas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado);

        getSupportActionBar().hide();
        IniciarComponentes();

        final int getQuestoesCorretas = getIntent().getIntExtra("Correto:",0);
        final int getQuestoesIncorretas =  getIntent().getIntExtra("Incorreto:", 0);


        certas.setText(String.valueOf(getQuestoesCorretas));
        erradas.setText(String.valueOf(getQuestoesIncorretas));

        recomecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaResultado.this,TelaPrincipal.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TelaResultado.this,TelaPrincipal.class));
        finish();
    }

    private void IniciarComponentes() {
        recomecar = findViewById(R.id.recomecar);
        certas = findViewById(R.id.certas);
        erradas = findViewById(R.id.erradas);
    }
}