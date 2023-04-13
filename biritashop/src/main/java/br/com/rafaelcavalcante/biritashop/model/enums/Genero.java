package br.com.rafaelcavalcante.biritashop.model.enums;

public enum Genero {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    NAOINFORMADO("Não Informado");

    private String valor;

    Genero(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }
}
