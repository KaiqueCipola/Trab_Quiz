package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {

    private String topicoSelecionado = "";
    private Button btn_start;
    private LinearLayout android;
    private ImageView perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();

        android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                topicoSelecionado = "android";

                android.setBackgroundResource(R.drawable.container_selecionado);

            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this,Perfil.class);
                startActivity(intent);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topicoSelecionado.isEmpty()){
                    Toast.makeText(TelaPrincipal.this,"Selecione um Tópico antes de jogar",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(TelaPrincipal.this,Perguntas.class);
                    intent.putExtra("topicoSelecionado", topicoSelecionado);
                    startActivity(intent);
                }
            }
        });

    }

    private void IniciarComponentes(){
        perfil = findViewById(R.id.perfil);
        android = findViewById(R.id.android_layout);
        btn_start = findViewById(R.id.btn_start);
    }
}