package br.com.rafaelcavalcante.biritashop.model.enums;

public enum Embalagem {
    PLASTICO("Plastico"),
    METAL("metal"),
    VIDRO("vidro");

    private String valor;

    Embalagem(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }
}
