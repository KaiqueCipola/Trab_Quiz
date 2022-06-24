package com.example.quiz;

import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class BD {

    private static List<ListQuestoes> questoesAndroid(){

        final List<ListQuestoes> listaDeQuestoes =  new ArrayList<>();

        final ListQuestoes questao1 = new ListQuestoes("essa é uma questao teste","testeOpcao1","testeOpcao2","testeOpcao3","testeOpcao4","testeOpcao1","");
        final ListQuestoes questao2 = new ListQuestoes("essa é uma questao teste","testeOpcao1","testeOpcao2","testeOpcao3","testeOpcao4","testeOpcao1","");

        listaDeQuestoes.add(questao1);
        listaDeQuestoes.add(questao2);

        return listaDeQuestoes;
    }

    public static List<ListQuestoes> getQuestoes(String topicoSelecionado){

        switch (topicoSelecionado) {
            default:
                return questoesAndroid();
        }
    }

}
