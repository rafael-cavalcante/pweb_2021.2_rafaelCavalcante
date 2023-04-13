package br.com.rafaelcavalcante.biritashop.model.enums;

public enum Genero {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    NAO_INFORMADO("Não Informado");

    private String valor;

    Genero(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }
}
