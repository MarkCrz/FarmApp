package com.farmapp.Dados;

public class ClassListLotesProdutos {
    String produto;
    String gramas;

    public ClassListLotesProdutos() {
        produto = "";
        gramas = "";
    }

    public ClassListLotesProdutos(String produto, String gramas) {
        this.produto = produto;
        this.gramas = gramas;
    }

    public ClassListLotesProdutos(ClassListLotesProdutos classListLotesProdutos) {
        this.produto = classListLotesProdutos.produto;
        this.gramas = classListLotesProdutos.gramas;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getGramas() {
        return gramas;
    }

    public void setGramas(String gramas) {
        this.gramas = gramas;
    }

    @Override
    public String toString() {
        return produto + "\n" + gramas + "g";
    }
}
