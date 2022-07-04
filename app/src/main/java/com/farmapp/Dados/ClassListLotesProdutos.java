package com.farmapp.Dados;

public class ClassListLotesProdutos {
    String loteNome;
    String gramas;

    public ClassListLotesProdutos() {
        loteNome = "";
        gramas = "";
    }

    public ClassListLotesProdutos(String loteNome, String gramas) {
        this.loteNome = loteNome;
        this.gramas = gramas;
    }

    public ClassListLotesProdutos(ClassListLotesProdutos classListLotesProdutos) {
        this.loteNome = classListLotesProdutos.loteNome;
        this.gramas = classListLotesProdutos.gramas;
    }

    public String getLoteNome() {
        return loteNome;
    }

    public void setLoteNome(String loteNome) {
        this.loteNome = loteNome;
    }

    public String getGramas() {
        return gramas;
    }

    public void setGramas(String gramas) {
        this.gramas = gramas;
    }

    @Override
    public String toString() {
        return loteNome + "\n" + gramas + "g";
    }
}
