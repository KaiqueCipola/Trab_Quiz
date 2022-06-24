package com.example.quiz;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Perguntas extends AppCompatActivity {

    private ImageView btn_voltar;
    private TextView timer,topicoSelecionado;

    private AppCompatButton opcao1,opcao2,opcao3,opcao4;
    private AppCompatButton btn_prox;
    private TextView contadorQuestoes, questao;

    private Timer quizTimer;

    private int tempoTotalEmMinutos = 0;
    private int segundos = 0;

    private List<ListQuestoes> listquestoes = new ArrayList<>();

    private int posicaoQuestao = 0;

    private String opcaoSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas);

        getSupportActionBar().hide();
        IniciarComponentes();

        final String getTopicoSelecionado =  getIntent().getStringExtra("topicoSelecionado");

        topicoSelecionado.setText(getTopicoSelecionado);


        //listquestoes = BD.getQuestoes(getTopicoSelecionado);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quiz-8b4f2-default-rtdb.firebaseio.com/");

        ProgressDialog progressDialog = new ProgressDialog(Perguntas.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tempoTotalEmMinutos = Integer.parseInt(snapshot.child("tempo").getValue(String.class));

                for(DataSnapshot dataSnapshot : snapshot.child(getTopicoSelecionado).getChildren()){
                    final String getQuestao = dataSnapshot.child("questao").getValue(String.class);
                    final String getOpcao1 = dataSnapshot.child("opcao1").getValue(String.class);
                    final String getOpcao2 = dataSnapshot.child("opcao2").getValue(String.class);
                    final String getOpcao3 = dataSnapshot.child("opcao3").getValue(String.class);
                    final String getOpcao4 = dataSnapshot.child("opcao4").getValue(String.class);
                    final String getResposta = dataSnapshot.child("resposta").getValue(String.class);

                    ListQuestoes listQuestao = new ListQuestoes(getQuestao,getOpcao1,getOpcao2,getOpcao3,getOpcao4,getResposta,"");
                    listquestoes.add(listQuestao);
                }

                progressDialog.hide();

                contadorQuestoes.setText((posicaoQuestao+1)+"/"+listquestoes.size());
                questao.setText(listquestoes.get(0).getQuestao());
                opcao1.setText(listquestoes.get(0).getOpcao1());
                opcao2.setText(listquestoes.get(0).getOpcao2());
                opcao3.setText(listquestoes.get(0).getOpcao3());
                opcao4.setText(listquestoes.get(0).getOpcao4());

                startTimer(timer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        opcao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcaoSelecionada.isEmpty()){

                    opcaoSelecionada = opcao1.getText().toString();

                    opcao1.setBackgroundResource(R.drawable.questao_errada);
                    opcao1.setTextColor(Color.WHITE);

                    revelarResposta();

                    listquestoes.get(posicaoQuestao).setQuestaoSelecionada(opcaoSelecionada);
                }
            }
        });
        opcao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcaoSelecionada.isEmpty()){

                    opcaoSelecionada = opcao2.getText().toString();

                    opcao2.setBackgroundResource(R.drawable.questao_errada);
                    opcao2.setTextColor(Color.WHITE);

                    revelarResposta();

                    listquestoes.get(posicaoQuestao).setQuestaoSelecionada(opcaoSelecionada);
                }
            }
        });
        opcao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcaoSelecionada.isEmpty()){

                    opcaoSelecionada = opcao3.getText().toString();

                    opcao3.setBackgroundResource(R.drawable.questao_errada);
                    opcao3.setTextColor(Color.WHITE);

                    revelarResposta();

                    listquestoes.get(posicaoQuestao).setQuestaoSelecionada(opcaoSelecionada);
                }
            }
        });
        opcao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(opcaoSelecionada.isEmpty()){

                    opcaoSelecionada = opcao4.getText().toString();

                    opcao4.setBackgroundResource(R.drawable.questao_errada);
                    opcao4.setTextColor(Color.WHITE);

                    revelarResposta();

                    listquestoes.get(posicaoQuestao).setQuestaoSelecionada(opcaoSelecionada);
                }

            }
        });

        btn_prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcaoSelecionada.isEmpty()){
                    Toast.makeText(Perguntas.this,"Selecione uma questão para avançar",Toast.LENGTH_SHORT).show();
                }else {
                    proximaQuestao();
                }
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(Perguntas.this, TelaPrincipal.class));
                finish();
            }
        });
    }

    private void proximaQuestao(){

        posicaoQuestao++;

        if((posicaoQuestao+1) == listquestoes.size()){
            btn_prox.setText("Terminar Quiz");
        }

        if(posicaoQuestao < listquestoes.size()){
            opcaoSelecionada = "";

            opcao1.setBackgroundResource(R.drawable.container_selecionado);
            opcao1.setTextColor(Color.parseColor("#1F6BB8"));

            opcao2.setBackgroundResource(R.drawable.container_selecionado);
            opcao2.setTextColor(Color.parseColor("#1F6BB8"));

            opcao3.setBackgroundResource(R.drawable.container_selecionado);
            opcao3.setTextColor(Color.parseColor("#1F6BB8"));

            opcao4.setBackgroundResource(R.drawable.container_selecionado);
            opcao4.setTextColor(Color.parseColor("#1F6BB8"));

            contadorQuestoes.setText((posicaoQuestao+1)+"/"+listquestoes.size());
            questao.setText(listquestoes.get(posicaoQuestao).getQuestao());
            opcao1.setText(listquestoes.get(posicaoQuestao).getOpcao1());
            opcao2.setText(listquestoes.get(posicaoQuestao).getOpcao2());
            opcao3.setText(listquestoes.get(posicaoQuestao).getOpcao3());
            opcao4.setText(listquestoes.get(posicaoQuestao).getOpcao4());

        }else{

            Intent intent = new Intent(Perguntas.this,TelaResultado.class);
            intent.putExtra("Correto:", getQuestoesCorretas());
            intent.putExtra("Incorreto:",getQuestoesIncorretas());
            startActivity(intent);

            finish();
        }
    }

    private void startTimer(TextView timerTextView){
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(segundos == 0){
                    tempoTotalEmMinutos--;
                    segundos = 59;
                }else if(segundos == 0 && tempoTotalEmMinutos == 0){

                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(Perguntas.this,"Tempo Esgotado",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Perguntas.this,TelaResultado.class);
                    intent.putExtra("correto",getQuestoesCorretas());
                    intent.putExtra("incorreto",getQuestoesIncorretas());
                    startActivity(intent);

                    finish();
                }else{
                    segundos--;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String minutosFinais = String.valueOf(tempoTotalEmMinutos);
                        String segundosFinais = String.valueOf(segundos);

                        if(minutosFinais.length() == 1) minutosFinais = "0" + minutosFinais;
                        if(segundosFinais.length() == 1) segundosFinais = "0" + minutosFinais;

                        timerTextView.setText(minutosFinais+":"+segundosFinais);
                    }
                });
            }
        },1000,1000);
    }

    private int getQuestoesCorretas() {
        int questoesCorretas = 0;

        for(int i=0;i<listquestoes.size();i++){
            final String getQuestaoSelecionada = listquestoes.get(i).getQuestaoSelecionada();
            final String getResposta = listquestoes.get(i).getResposta();

            if(getQuestaoSelecionada.equals(getResposta)){
                questoesCorretas++;
            }
        }

        return questoesCorretas;
    }

    private int getQuestoesIncorretas() {
        int questoesCorretas = 0;

        for(int i=0;i<listquestoes.size();i++){
            final String getQuestaoSelecionada = listquestoes.get(i).getQuestaoSelecionada();
            final String getResposta = listquestoes.get(i).getResposta();

            if(!getQuestaoSelecionada.equals(getResposta)){
                questoesCorretas++;
            }
        }

        return questoesCorretas;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(Perguntas.this, TelaPrincipal.class));
        finish();
    }

    private void revelarResposta(){

        final String getResposta = listquestoes.get(posicaoQuestao).getResposta();

        if(opcao1.getText().toString().equals(getResposta)){
            opcao1.setBackgroundResource(R.drawable.questao_certa);
            opcao1.setTextColor(Color.WHITE);
        }else if(opcao2.getText().toString().equals(getResposta)){
            opcao2.setBackgroundResource(R.drawable.questao_certa);
            opcao2.setTextColor(Color.WHITE);
        }else if(opcao3.getText().toString().equals(getResposta)){
            opcao3.setBackgroundResource(R.drawable.questao_certa);
            opcao3.setTextColor(Color.WHITE);
        }else if(opcao4.getText().toString().equals(getResposta)){
            opcao4.setBackgroundResource(R.drawable.questao_certa);
            opcao4.setTextColor(Color.WHITE);
        }
    }

    private void IniciarComponentes(){
        opcao1 = findViewById(R.id.opcao1);
        opcao2 = findViewById(R.id.opcao2);
        opcao3 = findViewById(R.id.opcao3);
        opcao4 = findViewById(R.id.opcao4);

        btn_prox = findViewById(R.id.btn_prox);

        contadorQuestoes = findViewById(R.id.contadorQuestoes);
        questao = findViewById(R.id.questao);

        btn_voltar = findViewById(R.id.btn_voltar);
        timer = findViewById(R.id.timer);
        topicoSelecionado = findViewById(R.id.nomeTopico);
    }
}