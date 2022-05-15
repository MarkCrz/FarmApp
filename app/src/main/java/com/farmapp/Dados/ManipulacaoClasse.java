package com.farmapp.Dados;

public class ManipulacaoClasse {

    String nome;

    ManipulacaoClasse(){
        nome = "";
    }

    public ManipulacaoClasse(String nome) {
        this.nome = nome;
    }

    public ManipulacaoClasse(ManipulacaoClasse manipulacaoClasse) {
        this.nome = manipulacaoClasse.nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
