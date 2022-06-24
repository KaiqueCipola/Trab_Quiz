package com.example.quiz;

public class ListQuestoes {

    private String questao,opcao1,opcao2,opcao3,opcao4,resposta;
    private String questaoSelecionada;

    public ListQuestoes(String questao, String opcao1, String opcao2, String opcao3, String opcao4, String resposta, String questaoSelecionada) {
        this.questao = questao;
        this.opcao1 = opcao1;
        this.opcao2 = opcao2;
        this.opcao3 = opcao3;
        this.opcao4 = opcao4;
        this.resposta = resposta;
        this.questaoSelecionada = questaoSelecionada;
    }

    public String getQuestao() {
        return questao;
    }

    public String getOpcao1() {
        return opcao1;
    }

    public String getOpcao2() {
        return opcao2;
    }

    public String getOpcao3() {
        return opcao3;
    }

    public String getOpcao4() {
        return opcao4;
    }

    public String getResposta() {
        return resposta;
    }

    public String getQuestaoSelecionada() {
        return questaoSelecionada;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public void setQuestaoSelecionada(String questaoSelecionada) {
        this.questaoSelecionada = questaoSelecionada;
    }
}
